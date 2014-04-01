package com.web3sys.W3S_Tickets;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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

        final Context thisContext = this.getApplicationContext();

        OnClickListener listnr = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(thisContext, CreateTicketActivity.class);
                startActivity(i);
            }
        };
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(listnr);

        Button exitButton = (Button) findViewById(R.id.button2);
        exitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
            }
        });
    }


    public void testExit()
    {

    }
}
