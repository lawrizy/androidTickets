package activities;

import android.app.Activity;
import android.os.Bundle;import com.web3sys.W3S_Tickets.R;
import org.achartengine.ChartFactory;
import org.achartengine.renderer.DefaultRenderer;

/**
 * Created by User on 5/04/14.
 */
public class Dashboard extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        setupListeners();
        setupGraphs();
        drawGraphs();
    }

    /**
     * Cette méthode crée les graphiques de façon "logique" mais ne les affiche pas encore.
     */
    private void setupGraphs() {
        DefaultRenderer renderer = new DefaultRenderer();

    }

    /**
     * Cette méthode s'occupe de l'affichage des graphiques.
     */
    private void drawGraphs() {
        //todo
    }

    private void setupListeners() {
        //todo
    }
}