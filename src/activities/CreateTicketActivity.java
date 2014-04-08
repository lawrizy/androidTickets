package activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.web3sys.W3S_Tickets.R;
import model.CategorieIncident;
import soap.WebServiceSoap;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CreateTicketActivity extends Activity{

    private Toast t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.createticket);

        t = new Toast(this.getApplicationContext());
        t.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);

        final List<CategorieIncident> listCats;
        final Activity thiss = this;
        final Spinner category = (Spinner) findViewById(R.id.categorySpinner);
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
        final Spinner SpinnerCategorie = (Spinner) findViewById(R.id.categorySpinner);

        Button createTicketButton = (Button)findViewById(R.id.sendCreateTicket);
        createTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCreateTicket();
            }
        });

        SpinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategorieIncident item = (CategorieIncident) SpinnerCategorie.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sendCreateTicket()
    {
        Log.i("AndroidTickets", "Attempting to create a new ticket...");
        Log.i("AndroidTickets", "Validating fields...");
        boolean validated = validateFields();
        Log.i("AndroidTickets", "Field validation result: " + validated);
    }

    private boolean validateFields() {
        Context context = getApplicationContext();
        Spinner catSpinner = (Spinner)findViewById(R.id.categorySpinner);
        Spinner subCatSpinner = (Spinner)findViewById(R.id.subCategorySpinner);
        Spinner buildingSpinner = (Spinner)findViewById(R.id.buildingSpinner);
        StringBuilder bld = new StringBuilder();

        boolean retVal = true;

        if(catSpinner.getSelectedItem() == null)
        {
            bld.append(context.getString(R.string.errorCategoryRequired) + "\n");
            retVal = false;
        }
        if(subCatSpinner.getSelectedItem() == null)
        {
            bld.append(context.getString(R.string.errorSubCategoryRequired) + "\n");
            retVal = false;
        }
        if(buildingSpinner.getSelectedItem() == null)
        {
            bld.append(context.getString(R.string.errorBuildingRequired) + "\n");
            retVal = false;
        }

        String finalString = bld.substring(0, bld.toString().length()-1);

        t.makeText(this.getApplicationContext(), finalString, Toast.LENGTH_LONG).show();

        return retVal;
    }

    private void resetAllFields()
    {
        Log.i("AndroidTickets", "Resetting all ticket creation fields.");

        Spinner catSpinner = (Spinner)findViewById(R.id.categorySpinner);
        Spinner subCatSpinner = (Spinner)findViewById(R.id.subCategorySpinner);
        Spinner buildingSpinner = (Spinner)findViewById(R.id.buildingSpinner);
        EditText floorTextField = (EditText)findViewById(R.id.floorNumberInput);
        EditText officeTextField = (EditText)findViewById(R.id.officeNumberInput);
        EditText descriptionMultilineTextField = (EditText)findViewById(R.id.descriptionInput);

        catSpinner.setSelection(0);
        subCatSpinner.setSelection(0);
        buildingSpinner.setSelection(0);
        floorTextField.setText("");
        officeTextField.setText("");
        descriptionMultilineTextField.setText("");
    }
}
