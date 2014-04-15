package activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.web3sys.W3S_Tickets.R;
import dao.BatimentDAO;
import model.Batiment;
import model.UserSessionInfo;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by User on 5/04/14.
 */
public class Dashboard extends Activity {
    private GraphicalView mChartView;
    private XYMultipleSeriesDataset dataset;
    private XYMultipleSeriesRenderer renderer;

    // pour test
    private final int SERIES_NR = 2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        setupListeners();
        setupSpinners();
        setupTestGraph();
    }

    private void setupTestGraph() {
        XYMultipleSeriesRenderer renderer = getTestBarRenderer();
        testChartSettings(renderer);
        mChartView = ChartFactory.getBarChartView(this, getTestBarDataSet(), renderer, BarChart.Type.DEFAULT);
        LinearLayout graphLayout = (LinearLayout)findViewById(R.id.graphLayout);
        graphLayout.addView(mChartView);
        mChartView.repaint();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        redrawGraph();
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
        fillDataSet();

        if(mChartView == null)
        {
            LinearLayout graphLayout = (LinearLayout)findViewById(R.id.graphLayout);
            mChartView = ChartFactory.getBarChartView(this, dataset, renderer, BarChart.Type.DEFAULT);
            graphLayout.addView(mChartView);
        }
        mChartView.repaint();
    }

    private XYMultipleSeriesDataset getTestBarDataSet() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 4;
        Random r = new Random();
        ArrayList<String> legendTitles = new ArrayList<String>();
        legendTitles.add("Sales");
        legendTitles.add("Expenses");
        for (int i = 0; i < SERIES_NR; i++) {
            CategorySeries series = new CategorySeries(legendTitles.get(i));
            for (int k = 0; k < nr; k++) {
                series.add(100 + r.nextInt() % 100);
            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }

    public XYMultipleSeriesRenderer getTestBarRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[] { 30, 40, 15, 0 });
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.BLUE);
        renderer.addSeriesRenderer(r);
        r = new SimpleSeriesRenderer();
        r.setColor(Color.RED);
        renderer.addSeriesRenderer(r);
        return renderer;
    }

    private void testChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setChartTitle("Truiton's Performance by AChartEngine BarChart");
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(10.5);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(210);
        renderer.addXTextLabel(1, "2010");
        renderer.addXTextLabel(2, "2011");
        renderer.addXTextLabel(3, "2012");
        renderer.addXTextLabel(4, "2013");
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setBarSpacing(0.5);
        renderer.setXTitle("Years");
        renderer.setYTitle("Performance");
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GRAY);
        renderer.setXLabels(0); // sets the number of integer labels to appear
    }

    private void fillDataSet() {
        //todo remplir le dataset et son renderer en fonction du type de graphique sélectionné
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