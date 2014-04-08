package model;

/**
 * Created by User on 8/04/2014.
 */
public class Batiment {
    private int id_batiment;
    private String nom;


    public int getId_batiment() {
        return id_batiment;
    }

    public void setId_batiment(int id_batiment) {
        this.id_batiment = id_batiment;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Batiment(int id_batiment, String nom) {
        this.id_batiment = id_batiment;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }

}
