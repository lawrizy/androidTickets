package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import model.CategorieIncident;

import java.util.ArrayList;
import java.util.List;

public class CategorieIncidentDAO {

    List<CategorieIncident> listCategorie = new ArrayList<CategorieIncident>();

    private SQLiteDatabase db;

    public CategorieIncidentDAO(Context context) {
        db = GetDB.getInstance(context).getWritableDatabase();
    }

//    public List<CategorieIncident> CursorToCategorieIncidentList(Cursor c) {
//        if (c.getCount() == 0) return null;
//
//        while (c.moveToNext()) {
//                CategorieIncident categorieIncident = new CategorieIncident(c.getInt(0), c.getString(1));
//                listCategorie.add(categorieIncident);
//            }
//
//        return listCategorie;
//
//    }


    public List<CategorieIncident>getListSousCategorie(int fk_parent)
    {
        Cursor c = db.query("w3sys_categorie_incident", new String[]{"id_categorie_incident", "label"}, " fk_parent = "+fk_parent, null, null, null, null);
        if (c.getCount() == 0) return null;

        while (c.moveToNext()) {
            CategorieIncident categorieIncident = new CategorieIncident(c.getInt(0), c.getString(1));
            listCategorie.add(categorieIncident);
        }

        return listCategorie;
    }

    public List<CategorieIncident> getListCategorie() {
        Cursor c = db.query("w3sys_categorie_incident", new String[]{"id_categorie_incident", "label"}, " fk_parent = 0", null, null, null, null);
        if (c.getCount() == 0) return null;

        while (c.moveToNext()) {
            CategorieIncident categorieIncident = new CategorieIncident(c.getInt(0), c.getString(1));
            listCategorie.add(categorieIncident);
        }

        return listCategorie;
    }

}
