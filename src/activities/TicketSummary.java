package activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.web3sys.W3S_Tickets.R;
import model.UserSessionInfo;

/**
 * Created by User on 8/04/14.
 */
public class TicketSummary extends Activity {
    private String categorie;
    private String subCategorie;
    private String building;
    private String ticketNumber;
    private String description;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticketsummary);

        //todo récupérer cat + subcat + building depuis le bundle envoyé par createTicket
        categorie = getIntent().getExtras().getString("category");
        subCategorie = getIntent().getExtras().getString("subCategory");
        building = getIntent().getExtras().getString("building");
        ticketNumber = getIntent().getExtras().getString("ticketNumber");
        description = getIntent().getExtras().getString("description");

        setupListeners();
        setupLayout();
    }

    private void setupLayout() {
        // Mettre à jour le message qui signifie qu'un mail a été envoyé ("A mail has been sent to")
        TextView mailSentMessage = (TextView)findViewById(R.id.mail_sent_textview);
        String newMessage = mailSentMessage.getText().toString() + " " + UserSessionInfo.USER_EMAIL;
        mailSentMessage.setText(newMessage);

        // Mise à jour du texte des différents champs
        TextView categoryText = (TextView)findViewById(R.id.summary_category);
        TextView subCategoryText = (TextView)findViewById(R.id.summary_subCategory);
        TextView buildingText = (TextView)findViewById(R.id.summary_buildingName);
        TextView ticketNumberText = (TextView)findViewById(R.id.summary_numTicket);
        TextView descriptionText = (TextView) findViewById(R.id.summary_description);

        categoryText.setText(categorie);
        subCategoryText.setText(subCategorie);
        buildingText.setText(building);
        ticketNumberText.setText(ticketNumber);
        descriptionText.setText(description);
    }

    private void setupListeners() {
        final Context thisContext = this.getApplicationContext();

        Button backToTicketCreationButton = (Button)findViewById(R.id.buttonBackToCreation);
        Button exitButton = (Button)findViewById(R.id.summary_exitButton);

        backToTicketCreationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(thisContext, CreateTicketActivity.class);
                startActivity(i);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
            }
        });
    }
}