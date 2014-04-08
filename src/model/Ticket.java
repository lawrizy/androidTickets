package model;

/**
 * Created by User on 8/04/2014.
 */
public class Ticket {
    public Ticket(String descriptif, String etage, String bureau, int fk_locataire, int fk_batiment) {
        this.descriptif = descriptif;
        this.etage = etage;
        this.bureau = bureau;
        this.fk_locataire = fk_locataire;
        this.fk_batiment = fk_batiment;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    public String getEtage() {
        return etage;
    }

    public void setEtage(String etage) {
        this.etage = etage;
    }

    public String getBureau() {
        return bureau;
    }

    public void setBureau(String bureau) {
        this.bureau = bureau;
    }

    public int getFk_locataire() {
        return fk_locataire;
    }

    public void setFk_locataire(int fk_locataire) {
        this.fk_locataire = fk_locataire;
    }

    public int getFk_batiment() {
        return fk_batiment;
    }

    public void setFk_batiment(int fk_batiment) {
        this.fk_batiment = fk_batiment;
    }

    private String descriptif;
    private String etage;
    private String bureau;
    private int fk_locataire;
    private int fk_batiment;


}
