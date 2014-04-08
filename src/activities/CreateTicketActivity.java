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
import model.CategorieIncident;
import soap.WebServiceSoap;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//import rest.WebServiceRest;

public class CreateTicketActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.createticket);

        final List<CategorieIncident> listCats;
        final Activity thiss = this;
        final Spinner category = (Spinner) findViewById(R.id.spinner);
        Future<List<CategorieIncident>> Listsd = Executors.newSingleThreadExecutor().submit(new Callable<List<CategorieIncident>>() {
            @Override
            public List<CategorieIncident> call() throws Exception {
                return WebServiceSoap.getCategories();
            }
        });


        List<CategorieIncident> listCat = null;
        try {
            listCat = Listsd.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ArrayAdapter<CategorieIncident> stringArrayAdapter = new ArrayAdapter<CategorieIncident>(thiss, android.R.layout.simple_spinner_dropdown_item, listCat);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(stringArrayAdapter);
        setupListeners();
    }

    private void setupListeners() {
        final Spinner SpinnerCategorie = (Spinner) findViewById(R.id.spinner);
        SpinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategorieIncident item = (CategorieIncident) SpinnerCategorie.getSelectedItem();
                TextView tv = (TextView) findViewById(R.id.textViewIdCate);
                if (item != null)
                    tv.setText(String.valueOf(item.getId_categorie_incident()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
