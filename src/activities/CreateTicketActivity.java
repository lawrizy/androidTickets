package activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.web3sys.W3S_Tickets.R;
import enums.ERROR_SET_MODE;

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
        Log.i("AndroidTickets", "Attempting to create a new ticket...");
        Log.i("AndroidTickets", "Validating fields...");
        boolean validated = validateFields();
        Log.i("AndroidTickets", "Field validation result: " + validated);
    }

    private void setErrorMessage(String message, ERROR_SET_MODE mode)
    {
        TextView errorArea = (TextView)findViewById(R.id.errorMessageTextView);
        switch(mode)
        {
            case CLEAR_AND_CREATE:
                clearErrorMessage();
                errorArea.setText(message);
                break;
            case APPEND:
                String oldMessage = "";
                if(errorArea.getText() != null)
                    oldMessage = errorArea.getText().toString();
                String newMessage = oldMessage;
                if(errorMessageExists())
                    if(oldMessage.charAt(oldMessage.length()-1) != '\n')
                        newMessage += "\n";
                newMessage += message;
                clearErrorMessage();
                errorArea.setText(newMessage);
                break;
        }
    }

    private void clearErrorMessage()
    {
        TextView errorArea = (TextView)findViewById(R.id.errorMessageTextView);
        errorArea.setText("");
    }

    private boolean errorMessageExists()
    {
        TextView errorArea = (TextView)findViewById(R.id.errorMessageTextView);
        return (errorArea.getText() != null & errorArea.getText().length() > 0 & errorArea.getText().toString() != "") ? true : false;
    }

    private boolean validateFields() {
        Context context = getApplicationContext();
        Spinner catSpinner = (Spinner)findViewById(R.id.categorySpinner);
        Spinner subCatSpinner = (Spinner)findViewById(R.id.subCategorySpinner);
        Spinner buildingSpinner = (Spinner)findViewById(R.id.buildingSpinner);

        boolean retVal = true;

        if(errorMessageExists()) clearErrorMessage();

        if(catSpinner.getSelectedItem() == null)
        {
            setErrorMessage(context.getString(R.string.errorCategoryRequired) + "\n", ERROR_SET_MODE.APPEND);
            retVal = false;
        }
        if(subCatSpinner.getSelectedItem() == null)
        {
            setErrorMessage(context.getString(R.string.errorSubCategoryRequired) + "\n", ERROR_SET_MODE.APPEND);
            retVal = false;
        }
        if(buildingSpinner.getSelectedItem() == null)
        {
            setErrorMessage(context.getString(R.string.errorBuildingRequired) + "\n", ERROR_SET_MODE.APPEND);
            retVal = false;
        }

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
