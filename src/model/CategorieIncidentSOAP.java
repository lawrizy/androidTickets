package model;

/**
 * Created by User on 3/04/2014.
 */
public class CategorieIncidentSOAP {

    private int _id;
    private String _label;
    private CategorieIncidentSOAP parent = null;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_label() {
        return _label;
    }

    public void set_label(String _label) {
        this._label = _label;
    }
    public CategorieIncidentSOAP getParent() {
        return parent;
    }

    public void setParent(CategorieIncidentSOAP parent) {
        this.parent = parent;
    }



    public CategorieIncidentSOAP(int id, String label) {
        this._id = id;
        this._label = label;

    }
    public CategorieIncidentSOAP() {

     }

    @Override
    public String toString() {
        return _label;
    }
}
