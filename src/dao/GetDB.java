package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class GetDB extends SQLiteOpenHelper {

    // Pattern Singleton implémenté pour éviter des doublons de connexion vers la DB.
    // Ce sera toujours la même instance de la connexion qui sera utilisée, pour cela
    // il suffit d'appeler la méthode "static getInstance()" qui renvoie l'instance
    // unique de la connexion (constructeur privé donc création impossible)

    public GetDB(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static GetDB instance;

    public static GetDB getInstance(Context context) {

        if (instance == null)
            instance = new GetDB(context, "db_ticketing.db", null, 1);

        return instance;

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}