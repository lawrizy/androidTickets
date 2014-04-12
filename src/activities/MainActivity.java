package activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRouter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.web3sys.W3S_Tickets.R;
import model.UserSessionInfo;
import soap.WebServiceSoap;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        setupListeners();
    }

    /**
     * Cette méthode se charge d'initialiser les listeners sur les composants de la vue.
     */
    private void setupListeners() {
        // On récupère le contexte dans lequel on se trouve actuellement.
        final Context thisContext = this.getApplicationContext();

        Button btn = (Button) findViewById(R.id.button);
        final EditText editEmail = (EditText) findViewById(R.id.loginEmail);
        final EditText editPassword = (EditText) findViewById(R.id.loginPass);

      //  final WebServiceRest webServiceRest = new WebServiceRest();
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("AndroidTickets", "User " + editEmail.getText().toString() + " trying to login...");
                        int response = WebServiceSoap.getUser(editEmail.getText().toString(), editPassword.getText().toString());
                        UserSessionInfo.USER_FUNCTION = WebServiceSoap.getUserFunction(response);
                        if (response > 0)//en dessous de zero errors et au dessus c'est l'ID de l'utilisateur
                        {
                            UserSessionInfo.USER_ID = response;
                            UserSessionInfo.USER_EMAIL = editEmail.getText().toString();
                            Log.i("AndroidTickets" , "User " + editEmail.getText().toString() + " logged in with ID " + response + ".");
                            Log.i("AndroidTickets", "User function is : " + UserSessionInfo.USER_FUNCTION.functionName);

                            Intent i = null;
                            switch(UserSessionInfo.USER_FUNCTION)
                            {
                                case Admin:
                                case Root:
                                    i = new Intent(thisContext, Dashboard.class);
                                    break;
                                case Locataire:
                                    i = new Intent(thisContext, CreateTicketActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putInt("userid", (int)response);
                                    i.putExtras(extras);
                                    break;
                            }

                            startActivity(i);
                        }
                        else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("AndroidTickets", "Login failed!");
//                                    TextView errorArea = (TextView)findViewById(R.id.errorMessageArea);
//                                    errorArea.setText(R.string.errorInvalidCredentialsMessage);
                                    Toast errorPopup = new Toast(thisContext);
                                    errorPopup.setGravity(Gravity.CENTER|Gravity.BOTTOM, 0, 0);
                                    errorPopup.makeText(thisContext, R.string.errorInvalidCredentialsMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });


        Button exitButton = (Button) findViewById(R.id.button2);
        exitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AndroidTickets", "Touched the Exit button.");
                moveTaskToBack(true);
            }
        });
    }

}
