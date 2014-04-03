package activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.web3sys.W3S_Tickets.R;
import model.CategorieIncident;
import soap.Response;

import java.util.List;

public class CreateTicketActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.createticket);

        final List<CategorieIncident> listCats;
        final Activity thiss = this;
//        String[] CategorieTest = {"untestt", "deuxtest"};
//        Spinner category = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CategorieTest);
//        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        category.setAdapter(stringArrayAdapter);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<CategorieIncident> listCat = Response.getCategories();

                    Spinner category = (Spinner) findViewById(R.id.spinner);
                    ArrayAdapter<CategorieIncident> stringArrayAdapter = new ArrayAdapter<CategorieIncident>(thiss, android.R.layout.simple_spinner_dropdown_item, listCat);
                    stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(stringArrayAdapter);
                }
            }).start();

    }

}
