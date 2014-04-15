package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import model.Batiment;
import model.CategorieIncident;

import java.util.ArrayList;
import java.util.List;

public class GetDB extends SQLiteOpenHelper {
    private  List<CategorieIncident> listCategorie;
    private List<Batiment> listBatiment;
    private SQLiteDatabase db;
    private static final String DB_NAME = "Ridounet.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_BATIMENT = "w3sys_batiment";
    private static final String COL_BATIMENT_ID = "id_batiment";
    private static final String COL_BATIMENT_NOM = "nom";
    private static final String CREATE_TABLE_BATIMENT = "CREATE TABLE " + TABLE_BATIMENT + "(" + COL_BATIMENT_ID + " INTEGER," + COL_BATIMENT_NOM + " TEXT);";
    private static String TABLE_CATEGORIE_INCIDENT = "w3sys_categorie_incident";
    private static String COL_CATEGORIE_INCIDENT_ID = "id_categorie_incident";
    private static String COL_CATEGORIE_INCIDENT_LABEL = "label";
    private static String COL_CATEGORIE_INCIDENT_FK_PARENT = "fk_parent";
    private static String CREATE_TABLE_CATEGORIE_INCIDENT = "CREATE TABLE " + TABLE_CATEGORIE_INCIDENT + "(" + COL_CATEGORIE_INCIDENT_ID + " INTEGER," + COL_CATEGORIE_INCIDENT_LABEL + " TEXT," + COL_CATEGORIE_INCIDENT_FK_PARENT + " INTEGER);";


    // Pattern Singleton implémenté pour éviter des doublons de connexion vers la DB.
    // Ce sera toujours la même instance de la connexion qui sera utilisée, pour cela
    // il suffit d'appeler la méthode "static getInstance()" qui renvoie l"instance
    // unique de la connexion (constructeur privé donc création impossible)

    private GetDB(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = getWritableDatabase();

    }


    private static GetDB instance;

    public static GetDB getInstance(Context context) {

        if (instance == null)
            instance = new GetDB(context, DB_NAME, null, DB_VERSION);

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BATIMENT);
        db.execSQL(CREATE_TABLE_CATEGORIE_INCIDENT);
        listBatiment = new ArrayList<Batiment>();
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

        // Insert into DB
        ContentValues values = new ContentValues();
        for (Batiment batiment : listBatiment) {
            values.put(COL_BATIMENT_ID, batiment.getId_batiment());
            values.put(COL_BATIMENT_NOM, batiment.getNom());
            db.insert(TABLE_BATIMENT, null, values);
        }
        listCategorie=new ArrayList<>();
        listCategorie.add(new CategorieIncident(1, "Sanitaire", 0));
        listCategorie.add(new CategorieIncident(2, "Electricité", 0));
        listCategorie.add(new CategorieIncident(3, "Ascenseurs", 0));
        listCategorie.add(new CategorieIncident(4, "HVAC", 0));
        listCategorie.add(new CategorieIncident(14, "Sécurité", 0));
        listCategorie.add(new CategorieIncident(15, "Divers", 0));
        listCategorie.add(new CategorieIncident(5, "Panne d'électricité", 2));
        listCategorie.add(new CategorieIncident(6, "Ampoule / néon défectueux", 2));
        listCategorie.add(new CategorieIncident(7, "Fuite eau", 1));
        listCategorie.add(new CategorieIncident(8, "WC bouché", 1));
        listCategorie.add(new CategorieIncident(9, "Ascenseur en panne", 3));
        listCategorie.add(new CategorieIncident(10, "Arrêt", 3));
        listCategorie.add(new CategorieIncident(11, "Radiateur en panne", 4));
        listCategorie.add(new CategorieIncident(12, "Local trop chaud", 4));
        listCategorie.add(new CategorieIncident(13, "Local trop froid", 4));
        listCategorie.add(new CategorieIncident(16, "Thermostat défectueux", 4));
        listCategorie.add(new CategorieIncident(17, "Climatisation en panne", 4));
        listCategorie.add(new CategorieIncident(18, "Fuite d'eau au niveau du radiateur", 4));
        listCategorie.add(new CategorieIncident(19, "Autre", 4));
        listCategorie.add(new CategorieIncident(20, "Evier bouché", 1));
        listCategorie.add(new CategorieIncident(21, "Chasse d'eau défectueuse", 1));
        listCategorie.add(new CategorieIncident(22, "Robinetterie défectueuse", 1));
        listCategorie.add(new CategorieIncident(23, "Mauvaise odeur au niveau des canalisations", 1));
        listCategorie.add(new CategorieIncident(24, "Autre", 1));
        listCategorie.add(new CategorieIncident(25, "Prise défectueuse", 2));
        listCategorie.add(new CategorieIncident(26, "Autre", 2));
        listCategorie.add(new CategorieIncident(27, "Badge défectueux", 14));
        listCategorie.add(new CategorieIncident(28, "Lecteur de badge défectueux", 14));
        listCategorie.add(new CategorieIncident(29, "Problème d'accès au batiment", 14));
        listCategorie.add(new CategorieIncident(30, "Problème d'accès au parking", 14));
        listCategorie.add(new CategorieIncident(31, "Caméra défectueuse", 14));
        listCategorie.add(new CategorieIncident(32, "Détecteur d'incendie défectueux", 14));
        listCategorie.add(new CategorieIncident(33, "Parlophone / visiophone défectueux", 14));
        listCategorie.add(new CategorieIncident(34, "Porte bloquée", 14));
        listCategorie.add(new CategorieIncident(35, "Autre", 14));
        listCategorie.add(new CategorieIncident(36, "Nettoyage", 15));
        listCategorie.add(new CategorieIncident(37, "Déblayage", 15));
        listCategorie.add(new CategorieIncident(38, "Papier WC manquant", 15));
        listCategorie.add(new CategorieIncident(39, "Produit sanitaire manquant", 15));
        listCategorie.add(new CategorieIncident(40, "Autre", 15));

        //insert db

        ContentValues Categoryvalues = new ContentValues();
        for (CategorieIncident categorieIncident: listCategorie) {
            Categoryvalues.put(COL_CATEGORIE_INCIDENT_ID, categorieIncident.getId_categorie_incident());
            Categoryvalues.put(COL_CATEGORIE_INCIDENT_LABEL, categorieIncident.getLabel());
            Categoryvalues.put(COL_CATEGORIE_INCIDENT_FK_PARENT,categorieIncident.getFk_parent());
            db.insert(TABLE_CATEGORIE_INCIDENT, null, Categoryvalues);
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE "+TABLE_BATIMENT+";");
        db.execSQL("DROP TABLE " + TABLE_CATEGORIE_INCIDENT + ";");
        onCreate(db);
    }

}