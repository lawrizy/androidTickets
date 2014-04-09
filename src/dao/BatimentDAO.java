package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import model.Batiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/04/2014.
 */
public class BatimentDAO {
    List<Batiment> batimentList=new ArrayList<>();
    private SQLiteDatabase db;

    public BatimentDAO(Context context) {
        db = GetDB.getInstance(context).getWritableDatabase();
    }

    public List<Batiment> CursorToBatimentList(Cursor c) {
        if (c.getCount() == 0) return null;

        while (c.moveToNext()) {
            Batiment batiment = new Batiment(c.getInt(0), c.getString(1));
            batimentList.add(batiment);
        }

        return batimentList;

    }

    public List<Batiment>getListBatiment(/*int id_user*/)
    {
        Cursor c = db.query("w3sys_batiment", new String[]{"id_batiment", "nom"},null,null,null,null,null);
        return CursorToBatimentList(c);
    }


}
