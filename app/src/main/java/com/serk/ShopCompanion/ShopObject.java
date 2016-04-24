package com.serk.ShopCompanion;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe définit l'objet magasin
 */

public class ShopObject {


    private String id, nom, marque, adressemag, latitude, longitude,
            lundi, mardi, mercredi, jeudi, vendredi, samedi, dimanche;


    public ShopObject() {
    }

    public ShopObject(String id, String nom, String marque, String adressemag, String latitude, String longitude,
                      String lundi, String mardi, String mercredi, String jeudi, String vendredi, String samedi, String dimanche) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.adressemag = adressemag;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lundi = lundi;
        this.mardi = mardi;
        this.mercredi = mercredi;
        this.jeudi = jeudi;
        this.vendredi = vendredi;
        this.samedi = samedi;
        this.dimanche = dimanche;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdressemag() {
        return adressemag;
    }

    public void setAdressemag(String adressemag) {
        this.adressemag = adressemag;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLundi() {
        return lundi;
    }

    public void setLundi(String lundi) {
        this.lundi = lundi;
    }

    public String getMardi() {
        return mardi;
    }

    public void setMardi(String mardi) {
        this.mardi = mardi;
    }

    public String getMercredi() {
        return mercredi;
    }

    public void setMercredi(String mercredi) {
        this.mercredi = mercredi;
    }

    public String getJeudi() {
        return jeudi;
    }

    public void setJeudi(String jeudi) {
        this.jeudi = jeudi;
    }

    public String getVendredi() {
        return vendredi;
    }

    public void setVendredi(String vendredi) {
        this.vendredi = vendredi;
    }

    public String getSamedi() {
        return samedi;
    }

    public void setSamedi(String samedi) {
        this.samedi = samedi;
    }

    public String getDimanche() {
        return dimanche;
    }

    public void setDimanche(String dimanche) {
        this.dimanche = dimanche;
    }
}
