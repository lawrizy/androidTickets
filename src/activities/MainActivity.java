package activities;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.web3sys.W3S_Tickets.R;

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

        // Listener pour le bouton de LOGIN
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AndroidTickets", "Touched the Login button.");
                Intent i = new Intent(thisContext, CreateTicketActivity.class);
                startActivity(i);
            }
        });

        // Listener pour le bouton EXIT
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
