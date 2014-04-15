package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import model.Batiment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by User on 8/04/2014.
 */
public class BatimentDAO {
    private static final int DB_VERSION = 1;
    private static final String TABLE_BATIMENT = "w3sys_batiment";
    private static final String COL_BATIMENT_ID = "id_batiment";
    private static final String COL_BATIMENT_NOM = "nom";
    List<Batiment> listBatiment = new ArrayList<>();

    private SQLiteDatabase db;



    public BatimentDAO(Context context) {
        db = GetDB.getInstance(context).getWritableDatabase();
    }

    public List<Batiment> CursorToBatimentList(Cursor c) {
        if (c.getCount() == 0) return null;

        while (c.moveToNext()) {
            Batiment batiment = new Batiment(c.getInt(0), c.getString(1));
            listBatiment.add(batiment);
        }

        return listBatiment;

    }

    public List<Batiment> getListBatiment(int id_user) {
        List<String> maList = new ArrayList<>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<List<String>> callable = new Callable<List<String>>() {
            @Override
            public List<String> call() {
                return soap.WebServiceSoap.listIdBuilding(17);
            }
        };
        Future<List<String>> future = executor.submit(callable);

        executor.shutdown();

        try {
            maList = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String inBatiment = "";
        for (String str : maList) {
            inBatiment += "'" + str + "',";
        }
        inBatiment = inBatiment.substring(0, inBatiment.length() - 1);

        Cursor c = db.query("w3sys_batiment", new String[]{"id_batiment", "nom"}, " id_batiment in (" + inBatiment + ")", null, null, null, null);
        return CursorToBatimentList(c);
    }


}
