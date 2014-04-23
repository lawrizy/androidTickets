package activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import com.web3sys.W3S_Tickets.R;
import dao.BatimentDAO;
import dao.CategorieIncidentDAO;
import model.Batiment;
import model.CategorieIncident;
import model.UserSessionInfo;

import java.util.List;

public class CreateTicketActivity extends Activity {

    private Toast t;
    static int sousCategorieID;
    static int batimentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.createticket);

        List<CategorieIncident> listCats;
        Spinner category = (Spinner) findViewById(R.id.categorySpinner);
        dao.CategorieIncidentDAO categorieIncidentDAO = new CategorieIncidentDAO(this);
        listCats = categorieIncidentDAO.getListCategorie();


        ArrayAdapter<CategorieIncident> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listCats);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(stringArrayAdapter);

        List<Batiment> batimentList;
        Spinner batiment = (Spinner) findViewById(R.id.buildingSpinner);
        dao.BatimentDAO batimentDAO = new BatimentDAO(this);
        batimentList = batimentDAO.getListBatiment(UserSessionInfo.USER_ID);
        ArrayAdapter<Batiment> batimentArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, batimentList);
        batimentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batiment.setAdapter(batimentArrayAdapter);

        setupListeners();
    }

    private void setupListeners() {
        final Spinner spinnerBatiment=(Spinner)findViewById(R.id.buildingSpinner);
        final Spinner spinnerCategorie = (Spinner) findViewById(R.id.categorySpinner);
        final Spinner spinnerSubcategory = (Spinner) findViewById(R.id.subCategorySpinner);

        Button createTicketButton = (Button) findViewById(R.id.sendCreateTicket);
        createTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCreateTicket();
            }
        });

        Button resetButton = (Button)findViewById(R.id.resetForm);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllFields();
            }
        });

        spinnerCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategorieIncident item = (CategorieIncident) spinnerCategorie.getSelectedItem();
                dao.CategorieIncidentDAO categorieIncidentDAO = new CategorieIncidentDAO(getApplicationContext());
                List<CategorieIncident> listSousCats;
                listSousCats = categorieIncidentDAO.getListSousCategorie(item.getId_categorie_incident());
                ArrayAdapter<CategorieIncident> stringArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listSousCats);
                stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubcategory.setAdapter(stringArrayAdapter);
                setupListeners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void sendCreateTicket() {
        final Spinner spinnerBatiment=(Spinner)findViewById(R.id.buildingSpinner);
        final Spinner spinnerCategorie = (Spinner) findViewById(R.id.categorySpinner);
        final Spinner spinnerSubcategory = (Spinner) findViewById(R.id.subCategorySpinner);

        //  boolean validated = validateFields();
        // Log.i("AndroidTickets", "Field validation result: " + validated);
        final Context thisContext = this.getApplicationContext();

        new Thread(new Runnable() {
            @Override
            public void run() {
                EditText floorTextField = (EditText) findViewById(R.id.floorInput);
                EditText officeTextField = (EditText) findViewById(R.id.officeInput);
                EditText descriptionMultilineTextField = (EditText) findViewById(R.id.descriptionInput);
                Spinner spinnerBat = (Spinner) findViewById(R.id.buildingSpinner);

                batimentID = ((Batiment) spinnerBat.getSelectedItem()).getId_batiment();
                Spinner spinnerSousCat = (Spinner) findViewById(R.id.subCategorySpinner);
                sousCategorieID = ((CategorieIncident) spinnerSousCat.getSelectedItem()).getId_categorie_incident();

                final String result = soap.WebServiceSoap.createTicket(UserSessionInfo.USER_ID, sousCategorieID, batimentID, floorTextField.getText().toString(), officeTextField.getText().toString(), descriptionMultilineTextField.getText().toString());

                if(result.equals("OK"))
                {
                    Intent i = new Intent(thisContext, TicketSummary.class);
                    Bundle extras = new Bundle();
                    extras.putString("category", spinnerCategorie.getSelectedItem().toString());
                    extras.putString("subCategory", spinnerSubcategory.getSelectedItem().toString());
                    extras.putString("building", spinnerBatiment.getSelectedItem().toString());
                    extras.putString("description", descriptionMultilineTextField.getText().toString());
                    extras.putString("ticketNumber", result);
                    i.putExtras(extras);
                    startActivity(i);
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            t = Toast.makeText(thisContext, getString(R.string.CreationTicketErreurCatcherMessage) + result, Toast.LENGTH_LONG);
                            t.setGravity(Gravity.TOP, Gravity.CENTER_HORIZONTAL, 200);
                            t.show();
                        }
                    });
                }
            }
        }).start();
    }

    private void resetAllFields() {
        Log.i("AndroidTickets", "Resetting all ticket creation fields.");

        Spinner catSpinner = (Spinner) findViewById(R.id.categorySpinner);
        Spinner subCatSpinner = (Spinner) findViewById(R.id.subCategorySpinner);
        Spinner buildingSpinner = (Spinner) findViewById(R.id.buildingSpinner);
        EditText floorTextField = (EditText) findViewById(R.id.floorInput);
        EditText officeTextField = (EditText) findViewById(R.id.officeInput);
        EditText descriptionMultilineTextField = (EditText) findViewById(R.id.descriptionInput);

        catSpinner.setSelection(0);
        subCatSpinner.setSelection(0);
        buildingSpinner.setSelection(0);
        floorTextField.setText("");
        officeTextField.setText("");
        descriptionMultilineTextField.setText("");
    }
}
