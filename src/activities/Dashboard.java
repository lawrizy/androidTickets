package activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
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
import java.util.concurrent.*;

/**
 * Created by User on 5/04/14.
 */
public class Dashboard extends Activity {
    public enum DrawMode {
        TICKETS_FOR_CATEGORIES("Tickets for categories"),
        TICKETS_FOR_STATUS("Tickets for status");

        private String name;

        DrawMode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private GraphicalView mChartView;
    private XYMultipleSeriesDataset dataset;
    private XYMultipleSeriesRenderer renderer;

    private DrawMode mode = DrawMode.TICKETS_FOR_CATEGORIES;

    private List<CategorieIncident> listCats;
    private List<CategorieIncident> listStatus;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        setupListeners();
        setupSpinners();
        redrawGraph();
        //setupTestGraph();
    }

    private void setupListeners() {
        Button exit = (Button) findViewById(R.id.dashboard_exitButton);
        Spinner graphTypeSpinner = (Spinner) findViewById(R.id.graphTypeSpinner);
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
        final Spinner graphTypeSpinner = (Spinner) findViewById(R.id.graphTypeSpinner);
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

        batiment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                redrawGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        graphTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = ((GraphType) (graphTypeSpinner.getSelectedItem())).getMode();
                Log.i("AndroidTickets", "New graph mode : " + mode.getName());
                redrawGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private GraphType[] populateGraphicTypeArray() {
        int typeAmount = 2;
        GraphType[] result = new GraphType[typeAmount];
        result[0] = new GraphType(DrawMode.TICKETS_FOR_CATEGORIES);
        result[1] = new GraphType(DrawMode.TICKETS_FOR_STATUS);
        //result[2] = new GraphType(getString(R.string.dashboard_graphType_TicketsByCompanies));
        return result;
    }

    private void redrawGraph() {
        Log.i("AndroidTickets", "Redrawing graphics");
        clearGraphic();
        fillDataSet();
        buildRenderer();
        makeConfig();
        mChartView = ChartFactory.getBarChartView(this, dataset, renderer, BarChart.Type.DEFAULT);
        LinearLayout graphLayout = (LinearLayout) findViewById(R.id.graphLayout);
        graphLayout.addView(mChartView);
        mChartView.repaint();
    }

    private void clearGraphic() {
        LinearLayout graphLayout = (LinearLayout) findViewById(R.id.graphLayout);
        graphLayout.removeAllViews();
    }

    private void fillDataSet() {
        if (mode == DrawMode.TICKETS_FOR_CATEGORIES) {
            // preparation langue
            final Spinner buildingSpinner = (Spinner) findViewById(R.id.buildingFilterSpinner);
            String langue = getResources().getConfiguration().locale.getLanguage();
            final Langue langues = (langue.equals("en") ? Langue.EN : (langue.equals("fr") ? Langue.FR : Langue.NL));

            // Fetch des datas via SOAP
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

            // Création du dataset en fct des datas reçues
            dataset = new XYMultipleSeriesDataset();

            final int SERIES_NR = 1;
            final int nr = listCats.size();

            for (int i = 0; i < SERIES_NR; ++i) {
                CategorySeries series = new CategorySeries("Nb tickets");
                for (int j = 0; j < nr; ++j)
                    series.add(listCats.get(j).getNbTicket());
                dataset.addSeries(series.toXYSeries());
            }
        } else if (mode == DrawMode.TICKETS_FOR_STATUS) { // TODO
            // preparation langue
            final Spinner buildingSpinner = (Spinner) findViewById(R.id.buildingFilterSpinner);
            String langue = getResources().getConfiguration().locale.getLanguage();
            final Langue langues = (langue.equals("en") ? Langue.EN : (langue.equals("fr") ? Langue.FR : Langue.NL));

            // Fetch des datas via SOAP
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<List<CategorieIncident>> callable = new Callable<List<CategorieIncident>>() {
                @Override
                public List<CategorieIncident> call() throws Exception {
                    return soap.WebServiceSoap.getPieDatas(((Batiment) buildingSpinner.getSelectedItem()).getId_batiment(), langues);
                }
            };
            Future<List<CategorieIncident>> future = executor.submit(callable);
            executor.shutdown();

            try {
                listStatus = (ArrayList<CategorieIncident>) future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            // Création du dataset en fct des datas reçues
            dataset = new XYMultipleSeriesDataset();

            final int SERIES_NR = 3;
            final int nr = 1;

            for (int i = 0; i < SERIES_NR; ++i) {
                CategorySeries series = new CategorySeries(listStatus.get(i).getLabel());
                for (int j = 0; j < nr; ++j)
                    series.add(listCats.get(j).getNbTicket());
                dataset.addSeries(series.toXYSeries());
            }
        }
    }

    private void buildRenderer() {
        renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[]{30, 40, 15, 0});

        SimpleSeriesRenderer r;
        if (mode == DrawMode.TICKETS_FOR_CATEGORIES) {
            r = new SimpleSeriesRenderer();
            renderer.addSeriesRenderer(r);
        } else if (mode == DrawMode.TICKETS_FOR_STATUS) {
            for (int i = 0; i < listCats.size(); ++i) {
                r = new SimpleSeriesRenderer();
                renderer.addSeriesRenderer(r);
            }
        }
    }

    private void makeConfig() {
        renderer.setChartTitle("Tickets for status");
        renderer.setXAxisMin(-1);
        renderer.setXAxisMax(7);
        renderer.setYAxisMin(0);
        int maxY = 0;
        for (CategorieIncident ci : listCats) {
            if (ci.getNbTicket() > maxY)
                maxY = ci.getNbTicket();
        }
        renderer.setYAxisMax(maxY + 5);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setBarSpacing(1);
        renderer.setBarWidth(20);
        renderer.setShowLabels(true);
        renderer.setShowLegend(false);

        for (int i = 0; i < listCats.size(); ++i)
            renderer.addXTextLabel(i + 1, listCats.get(i).getLabel() + " (" + listCats.get(i).getNbTicket() + ")");

        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GRAY);
        renderer.setXLabels(0); // sets the number of integer labels to appear
    }
}