package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class GetDB extends SQLiteOpenHelper {

    // Pattern Singleton implémenté pour éviter des doublons de connexion vers la DB.
    // Ce sera toujours la même instance de la connexion qui sera utilisée, pour cela
    // il suffit d"appeler la méthode "static getInstance()" qui renvoie l"instance
    // unique de la connexion (constructeur privé donc création impossible)

    public GetDB(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        setupList();

    }



    private static GetDB instance;

    public static GetDB getInstance(Context context) {

        if (instance == null)
            instance = new GetDB(context, "db_ticketing.db", null, 1);

        return instance;
    }


    private List<Batiment> listBatiment = new ArrayList<>();


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    private void setupList() {

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

}