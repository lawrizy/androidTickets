package model;

/**
 * Created by User on 3/04/2014.
 */
public class CategorieIncident {
    private String FR;
    private String EN;
    private String NL;

    private int id_categorie_incident;
    private String label;

    public CategorieIncident( int id_categorie_incident, String label,String FR, String EN, String NL, int fk_parent) {

        this.id_categorie_incident = id_categorie_incident;
        this.label = label;
        this.FR = FR;
        this.EN = EN;
        this.NL = NL;
        this.fk_parent = fk_parent;
    }


    public String getFR() {
        return FR;
    }

    public void setFR(String FR) {
        this.FR = FR;
    }

    public String getEN() {
        return EN;
    }

    public void setEN(String EN) {
        this.EN = EN;
    }

    public String getNL() {
        return NL;
    }

    public void setNL(String NL) {
        this.NL = NL;
    }


    public int getFk_parent() {
        return fk_parent;
    }

    public void setFk_parent(int fk_parent) {
        this.fk_parent = fk_parent;
    }

    private int  fk_parent ;

    public CategorieIncident(int id_categorie_incident, String label, int fk_parent) {
        this.id_categorie_incident = id_categorie_incident;
        this.label = label;
        this.fk_parent = fk_parent;
    }

    public CategorieIncident(int id_categorie_incident, String label) {
        this.id_categorie_incident = id_categorie_incident;
        this.label = label;
        this.fk_parent=0;
    }

    public int getId_categorie_incident() {
        return id_categorie_incident;
    }

    public void setId_categorie_incident(int id_categorie_incident) {
        this.id_categorie_incident = id_categorie_incident;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



    @Override
    public String toString() {
        return label;
    }
}
