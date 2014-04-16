package activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.web3sys.W3S_Tickets.R;
import dao.BatimentDAO;
import enums.Langue;
import model.Batiment;
import model.CategorieIncident;
import model.GraphType;
import model.GraphTypeSpinnerAdapter;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by User on 5/04/14.
 */
public class Dashboard extends Activity {
    enum DrawMode
    {
        TICKETS_FOR_CATEGORIES,
        TICKETS_FOR_STATUS,
    }

    private GraphicalView mChartView;
    private XYMultipleSeriesDataset dataset;
    private XYMultipleSeriesRenderer renderer;

    private DrawMode mode = DrawMode.TICKETS_FOR_CATEGORIES;
    private int batimentFilter = 0; // All buildings

    private List<CategorieIncident> listCats;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        setupListeners();
        setupSpinners();
        redrawGraph();
        //setupTestGraph();
    }

    private void setupListeners() {
        //todo on item selected spinners
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

    private void setupSpinners() {
        //Graphic type spinner
        Spinner graphTypeSpinner = (Spinner)findViewById(R.id.graphTypeSpinner);
        //ArrayAdapter<String> entries = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, populateGraphicTypeArray());
        GraphTypeSpinnerAdapter entries = new GraphTypeSpinnerAdapter(Dashboard.this, android.R.layout.simple_spinner_item, populateGraphicTypeArray());
        entries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        graphTypeSpinner.setAdapter(entries);

        // Building spinner
        List<Batiment> batimentList;
        Spinner batiment = (Spinner) findViewById(R.id.buildingFilterSpinner);
        dao.BatimentDAO batimentDAO = new BatimentDAO(this);
        batimentList = batimentDAO.getAllBuildings();
        ArrayAdapter<Batiment> batimentArrayAdapter = new ArrayAdapter<>(this.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, batimentList);
        batimentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batiment.setAdapter(batimentArrayAdapter);
    }

    private GraphType[] populateGraphicTypeArray()
    {
        int typeAmount = 2;
        GraphType[] result = new GraphType[typeAmount];
        result[0] = new GraphType(getString(R.string.dashboard_graphType_ticketsForCategory));
        result[1] = new GraphType(getString(R.string.dashboard_graphType_ticketsForStatus));
        //result[2] = new GraphType(getString(R.string.dashboard_graphType_TicketsByCompanies));
        return result;
    }

    private void redrawGraph() {
        fillDataSet();
        buildRenderer();
        makeConfig();
        mChartView = ChartFactory.getBarChartView(this, dataset, renderer, BarChart.Type.DEFAULT);
        LinearLayout graphLayout = (LinearLayout)findViewById(R.id.graphLayout);
        graphLayout.addView(mChartView);
        mChartView.repaint();
    }

    private void fillDataSet() {
        //todo remplir le dataset et son renderer en fonction du type de graphique sélectionné
        if(mode == DrawMode.TICKETS_FOR_CATEGORIES)
        {
            final Spinner buildingSpinner = (Spinner) findViewById(R.id.buildingFilterSpinner);
            String langue = getResources().getConfiguration().locale.getLanguage();
            final Langue langues = (langue.equals("en") ? Langue.EN : (langue.equals("fr") ? Langue.FR : Langue.NL));
           // WebServiceSoap.getBarsDatas( ((Batiment)buildingSpinner.getSelectedItem()).getId_batiment(), langues);

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<List<CategorieIncident>> callable = new Callable<List<CategorieIncident>>() {
                @Override
                public List<CategorieIncident> call() throws Exception {
                    return soap.WebServiceSoap.getBarsDatas(((Batiment) buildingSpinner.getSelectedItem()).getId_batiment(), langues);
                }
            };
            Future<List<CategorieIncident>> future = executor.submit(callable);
            executor.shutdown();

            try {
                listCats = (ArrayList<CategorieIncident>) future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            dataset = new XYMultipleSeriesDataset();

            final int SERIES_NR = 1;
            final int nr = listCats.size();

            for (int i = 0; i < SERIES_NR; ++i) {
                CategorySeries series = new CategorySeries("");
                for(int j = 0 ; j < nr ; ++j)
                    series.add(listCats.get(i).getNbTicket());
                dataset.addSeries(series.toXYSeries());
            }
        }
        else if(mode == DrawMode.TICKETS_FOR_STATUS)
        {

        }
    }

    private void buildRenderer() {
        renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[] { 30, 40, 15, 0 });

        SimpleSeriesRenderer r;
//        for(int i = 0 ; i < listCats.size(); ++i) {
            r = new SimpleSeriesRenderer();
            renderer.addSeriesRenderer(r);
//        }
    }

    private void makeConfig() {
        renderer.setChartTitle("Tickets for category");
        renderer.setXAxisMin(-1);
        renderer.setXAxisMax(7);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(500);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setBarSpacing(1);
        renderer.setBarWidth(20);
        renderer.setXTitle("Category");
        renderer.setYTitle("Nb Tickets");

        for(int i = 0 ; i < listCats.size() ; ++i)
            renderer.addXTextLabel(i, listCats.get(i).getLabel());

        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GRAY);
        renderer.setXLabels(0); // sets the number of integer labels to appear
    }

    /******************************** TEST CHART *********************************/
    private void setupTestGraph() {
        XYMultipleSeriesRenderer renderer = getTestBarRenderer();
        testChartSettings(renderer);
        mChartView = ChartFactory.getBarChartView(this, getTestBarDataSet(), renderer, BarChart.Type.DEFAULT);
        LinearLayout graphLayout = (LinearLayout)findViewById(R.id.graphLayout);
        graphLayout.addView(mChartView);
        mChartView.repaint();
    }

    private XYMultipleSeriesDataset getTestBarDataSet() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 4;
        final int SERIES_NR = 2;
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
}