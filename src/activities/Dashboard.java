package activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.web3sys.W3S_Tickets.R;
import dao.BatimentDAO;
import model.Batiment;
import model.UserSessionInfo;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 5/04/14.
 */
public class Dashboard extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        redrawGraph();

        setupListeners();
        setupSpinners();
    }

    private void setupListeners() {
        //todo
        Button exit = (Button)findViewById(R.id.dashboard_exitButton);
        Spinner graphTypeSpinner = (Spinner)findViewById(R.id.graphTypeSpinner);
        Spinner batiment = (Spinner) findViewById(R.id.buildingFilterSpinner);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
            }
        });
    }

    private void redrawGraph() {
        //todo
        XYMultipleSeriesDataset ds = new XYMultipleSeriesDataset();
        XYMultipleSeriesRenderer dsr = new XYMultipleSeriesRenderer();

        ds.addSeries(0, new XYSeries("Test", 100));
        dsr.addSeriesRenderer(0, new SimpleSeriesRenderer());
        GraphicalView mChartView = ChartFactory.getLineChartView(this.getApplicationContext(), ds, dsr);
        mChartView.repaint();
    }

    private void setupSpinners() {
        //Graphic type spinner
        Spinner graphTypeSpinner = (Spinner)findViewById(R.id.graphTypeSpinner);
        ArrayAdapter<String> entries = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getGraphicTypeList());
        entries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        graphTypeSpinner.setAdapter(entries);

        // Building spinner
        List<Batiment> batimentList;
        Spinner batiment = (Spinner) findViewById(R.id.buildingFilterSpinner);
        dao.BatimentDAO batimentDAO = new BatimentDAO(this);
        batimentList = batimentDAO.getListBatiment(UserSessionInfo.USER_ID);
        ArrayAdapter<Batiment> batimentArrayAdapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, batimentList);
        batimentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batiment.setAdapter(batimentArrayAdapter);
    }

    private ArrayList<String> getGraphicTypeList()
    {
        ArrayList<String> result = new ArrayList<>();
        result.add(getString(R.string.dashboard_graphType_ticketsForCategory));
        result.add(getString(R.string.dashboard_graphType_ticketsForStatus));
        result.add(getString(R.string.dashboard_graphType_TicketsByCompanies));
        return result;
    }
}