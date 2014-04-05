package activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.web3sys.W3S_Tickets.R;

public class CreateTicketActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.createticket);

        setupListeners();
    }

    private void setupListeners() {
        /* Create ticket button */
        Button createTicketButton = (Button)findViewById(R.id.sendCreateTicket);
        createTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCreateTicket();
            }
        });

        /* Reset button */
        Button resetButton = (Button)findViewById(R.id.resetTicketView);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAllFields();
            }
        });
    }

    private void sendCreateTicket()
    {
        Log.i("AndroidTickets", "Attempting to create a new ticket... (Not implemented)");
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
