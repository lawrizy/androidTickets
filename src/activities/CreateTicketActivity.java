package activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.web3sys.W3S_Tickets.R;
import model.CategorieIncidentSOAP;
import soap.WebService;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//import rest.WebService;

public class CreateTicketActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.createticket);

        final List<CategorieIncidentSOAP> listCats;
        final Activity thiss = this;
        String[] CategorieTest = {"untestt", "deuxtest"};
//        Spinner category = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CategorieTest);
//        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        category.setAdapter(stringArrayAdapter);
        final String url = "urn:AndroidControllerwsdl#getCategorie";
//        final URL urls = null;
//        try {
//            urls = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }


        final Spinner category = (Spinner) findViewById(R.id.spinner);
        Future<List<CategorieIncidentSOAP>> Listsd = Executors.newSingleThreadExecutor().submit(new Callable<List<CategorieIncidentSOAP>>() {
            @Override
            public List<CategorieIncidentSOAP> call() throws Exception {
                return WebService.getCategories();
            }
        });
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                listCat[0] = WebService.getCategories() /*WebService.getCategories()*/;
//                //    Spinner category = (Spinner) findViewById(R.id.spinner);
//            }
//        }).start();
        List<CategorieIncidentSOAP> listCat = null;
        try {
            listCat = Listsd.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ArrayAdapter<CategorieIncidentSOAP> stringArrayAdapter = new ArrayAdapter<CategorieIncidentSOAP>(thiss, android.R.layout.simple_spinner_dropdown_item, listCat);
//        for (CategorieIncidentSOAP cs:listCat)
//        {
//            stringArrayAdapter.insert(cs,cs.get_id());
//        }
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(stringArrayAdapter);
//        ArrayAdapter<CategorieIncidentSOAP> stringArrayAdapter = new ArrayAdapter<CategorieIncidentSOAP>(thiss, android.R.layout.simple_spinner_dropdown_item, listCat[0]);
//        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        category.setAdapter(stringArrayAdapter);
        setupListeners();
    }

    private void setupListeners() {
        final Spinner SpinnerCategorie = (Spinner) findViewById(R.id.spinner);
        SpinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategorieIncidentSOAP item = (CategorieIncidentSOAP) SpinnerCategorie.getSelectedItem();
                TextView tv = (TextView) findViewById(R.id.textViewIdCate);
                if (item != null)
                tv.setText(String.valueOf(item.get_id()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}
