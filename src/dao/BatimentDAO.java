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

        listBatiment.add(new Batiment(1, "Alliance A & C"));
        listBatiment.add(new Batiment(2, "Alliance B"));
        listBatiment.add(new Batiment(3, "Alliance D & E"));
        listBatiment.add(new Batiment(4, "Alliance F & G"));
        listBatiment.add(new Batiment(5, "Alliance J"));
        listBatiment.add(new Batiment(6, "Alliance S"));
        listBatiment.add(new Batiment(7, "Arts 44"));
        listBatiment.add(new Batiment(8, "Arts&Lux"));
        listBatiment.add(new Batiment(9, "ASTRID - EVERE"));
        listBatiment.add(new Batiment(10, "AUDERGHEM"));
        listBatiment.add(new Batiment(11, "BEAULIEU"));
        listBatiment.add(new Batiment(12, "BOULEVARD DE WATERLOO"));
        listBatiment.add(new Batiment(13, "Brussels 1 Office"));
        listBatiment.add(new Batiment(14, "Brussels 2 Office"));
        listBatiment.add(new Batiment(15, "CENTRAL PLAZA"));
        listBatiment.add(new Batiment(16, "CITEB NEW"));
        listBatiment.add(new Batiment(17, "CITY CENTER"));
        listBatiment.add(new Batiment(18, "CONGO 7"));
        listBatiment.add(new Batiment(19, "CORTENBERGH"));
        listBatiment.add(new Batiment(20, "CRYSTAL"));
        listBatiment.add(new Batiment(21, "HANNUT"));
        listBatiment.add(new Batiment(22, "HASSELT"));
        listBatiment.add(new Batiment(23, "JAMBLINNE DE MEUX"));
        listBatiment.add(new Batiment(24, "LOI 102"));
        listBatiment.add(new Batiment(25, "Mercure centre"));
        listBatiment.add(new Batiment(26, "NAMUR - BOURGEOIS"));
        listBatiment.add(new Batiment(27, "NORTH LIGHT"));
        listBatiment.add(new Batiment(28, "NOSSEGEM - Data Center"));
        listBatiment.add(new Batiment(29, "OVERIJSE"));
        listBatiment.add(new Batiment(30, "PARC -UNIVERSITE L-L-N (LE)"));
        listBatiment.add(new Batiment(31, "ROOSEVELT - MONS"));
        listBatiment.add(new Batiment(32, "ROYALE 52"));
        listBatiment.add(new Batiment(33, "ROYALE 54"));
        listBatiment.add(new Batiment(34, "STEPHANIE PLACE I"));
        listBatiment.add(new Batiment(35, "Stéphanie Square"));
        listBatiment.add(new Batiment(36, "TERVUREN 2"));
        listBatiment.add(new Batiment(37, "Twin Square, Madison"));
        listBatiment.add(new Batiment(38, "Twin Square, Vendôme"));
        listBatiment.add(new Batiment(39, "WAREMME"));
        listBatiment.add(new Batiment(40, "Waterloo Office Park, immeuble M"));
        listBatiment.add(new Batiment(41, "Waterloo Office Park, immeuble N"));
        listBatiment.add(new Batiment(42, "WOLUWE GATE"));
        listBatiment.add(new Batiment(43, "TEST NE PAS AFFICHER"));
        listBatiment.add(new Batiment(44, "testBatiment"));
        listBatiment.add(new Batiment(49, "test3"));
        listBatiment.add(new Batiment(51, "test2"));
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
        return listBatiment;
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Callable<List<String>> callable = new Callable<List<String>>() {
//            @Override
//            public List<String> call() {
//                return soap.WebServiceSoap.listIdBuilding(17);
//            }
//        };
//        Future<List<String>> future = executor.submit(callable);
//        executor.shutdown();
//
//        try {
//            maList = future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        String inBatiment = "";
//        for (String str : maList) {
//            inBatiment += "'" + str + "',";
//        }
//        inBatiment = inBatiment.substring(0, inBatiment.length() - 1);
//
//        Cursor c = db.query("w3sys_batiment", new String[]{"id_batiment", "nom"}, " id_batiment in (" + inBatiment + ")", null, null, null, null);
//        return CursorToBatimentList(c);
    }


}
