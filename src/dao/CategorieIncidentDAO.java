package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import model.CategorieIncident;

import java.util.ArrayList;
import java.util.List;

public class CategorieIncidentDAO {

    private static String language;
    List<CategorieIncident> listCategorie = new ArrayList<CategorieIncident>();

    private SQLiteDatabase db;

    public CategorieIncidentDAO(Context context) {
        db = GetDB.getInstance(context).getWritableDatabase();
        language = context.getResources().getConfiguration().locale.getLanguage();
    }


    public List<CategorieIncident>getListSousCategorie(int fk_parent)
    {
        Cursor c;
        if (language.equals("en") || language.equals("fr") || language.equals("nl"))
            c = db.query("w3sys_categorie_incident", new String[]{"id_categorie_incident", language}, " fk_parent = "+fk_parent, null, null, null, null);
        else
            c = db.query("w3sys_categorie_incident", new String[]{"id_categorie_incident", "en"}, " fk_parent = "+fk_parent, null, null, null, null);
        if (c.getCount() == 0) return null;

        while (c.moveToNext()) {
            CategorieIncident categorieIncident = new CategorieIncident(c.getInt(0), c.getString(1));
            listCategorie.add(categorieIncident);
        }

        return listCategorie;
    }

    public List<CategorieIncident> getListCategorie() {
        Cursor c;
        if (language.equals("en") || language.equals("fr") || language.equals("nl"))
         c = db.query("w3sys_categorie_incident", new String[]{"id_categorie_incident", language}, " fk_parent = 0", null, null, null, null);
      else
            c = db.query("w3sys_categorie_incident", new String[]{"id_categorie_incident", "en"}, " fk_parent = 0", null, null, null, null);
        if (c.getCount() == 0) return null;

        while (c.moveToNext()) {
            CategorieIncident categorieIncident = new CategorieIncident(c.getInt(0), c.getString(1));
            listCategorie.add(categorieIncident);
        }

        return listCategorie;
    }

}
