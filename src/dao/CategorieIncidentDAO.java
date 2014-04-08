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
     //   db = GetDB.getInstance(context).getWritableDatabase();
        listCategorie.add(new CategorieIncident(1, "Sanitaire",0));
        listCategorie.add(new CategorieIncident(2, "Electricité",0));
        listCategorie.add(new CategorieIncident(3, "Ascenseurs",0));
        listCategorie.add(new CategorieIncident(4, "HVAC",0));
        listCategorie.add(new CategorieIncident(14, "Sécurité",0));
        listCategorie.add(new CategorieIncident(15, "Divers",0));
        listCategorie.add(new CategorieIncident(5, "Panne d'électricité",2));
        listCategorie.add(new CategorieIncident(6, "Ampoule / néon défectueux",2));
        listCategorie.add(new CategorieIncident(7, "Fuite eau",1));
        listCategorie.add(new CategorieIncident(8, "WC bouché",1));
        listCategorie.add(new CategorieIncident(9, "Ascenseur en panne",3));
        listCategorie.add(new CategorieIncident(10, "Arrêt",3));
        listCategorie.add(new CategorieIncident(11, "Radiateur en panne",4));
        listCategorie.add(new CategorieIncident(12, "Local trop chaud",4));
        listCategorie.add(new CategorieIncident(13, "Local trop froid",4));
        listCategorie.add(new CategorieIncident(16, "Thermostat défectueux",4));
        listCategorie.add(new CategorieIncident(17, "Climatisation en panne",4));
        listCategorie.add(new CategorieIncident(18, "Fuite d'eau au niveau du radiateur",4));
        listCategorie.add(new CategorieIncident(19, "Autre",4));
        listCategorie.add(new CategorieIncident(20, "Evier bouché",1));
        listCategorie.add(new CategorieIncident(21, "Chasse d'eau défectueuse",1));
        listCategorie.add(new CategorieIncident(22, "Robinetterie défectueuse",1));
        listCategorie.add(new CategorieIncident(23, "Mauvaise odeur au niveau des canalisations",1));
        listCategorie.add(new CategorieIncident(24, "Autre",1));
        listCategorie.add(new CategorieIncident(25, "Prise défectueuse",2));
        listCategorie.add(new CategorieIncident(26, "Autre",2));
        listCategorie.add(new CategorieIncident(27, "Badge défectueux",14));
        listCategorie.add(new CategorieIncident(28, "Lecteur de badge défectueux",14));
        listCategorie.add(new CategorieIncident(29, "Problème d'accès au batiment",14));
        listCategorie.add(new CategorieIncident(30, "Problème d'accès au parking",14));
        listCategorie.add(new CategorieIncident(31, "Caméra défectueuse",14));
        listCategorie.add(new CategorieIncident(32, "Détecteur d'incendie défectueux",14));
        listCategorie.add(new CategorieIncident(33, "Parlophone / visiophone défectueux",14));
        listCategorie.add(new CategorieIncident(34, "Porte bloquée",14));
        listCategorie.add(new CategorieIncident(35, "Autre",14));
        listCategorie.add(new CategorieIncident(36, "Nettoyage",15));
        listCategorie.add(new CategorieIncident(37, "Déblayage",15));
        listCategorie.add(new CategorieIncident(38, "Papier WC manquant",15));
        listCategorie.add(new CategorieIncident(39, "Produit sanitaire manquant",15));
        listCategorie.add(new CategorieIncident(40, "Autre",15));


    }
    public List<CategorieIncident> getListCategorieS() {
        return listCategorie;
    }

    public List<CategorieIncident> getListCategorie() {
        List<CategorieIncident> listCat = new ArrayList<CategorieIncident>();
        Cursor c = db.query("w3sys_categorie_incident", new String[]{"id_categorie_incident", "label", "fk_parent"}, " fk_parent = null", null, null, null, null);

        c.toString();


        return listCat;
    }

}
