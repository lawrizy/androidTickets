package activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.web3sys.W3S_Tickets.R;
import model.UserSessionInfo;

/**
 * Created by User on 8/04/14.
 */
public class TicketSummary extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticketsummary);

        setupLayout();
    }

    private void setupLayout() {
        // Mettre à jour le message qui signifie qu'un mail a été envoyé ("A mail has been sent to")
        TextView mailSentMessage = (TextView)findViewById(R.id.mail_sent_textview);
        String newMessage = mailSentMessage.getText().toString() + " " + UserSessionInfo.USER_EMAIL;
        mailSentMessage.setText(newMessage);
    }
}