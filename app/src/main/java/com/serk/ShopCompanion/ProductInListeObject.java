package com.serk.ShopCompanion;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe définit l'objet produit dans une liste
 */
public class ProductInListeObject {

    private String mail, ean, marque, nom, name;

    public ProductInListeObject() {
    }

    public ProductInListeObject(String mail, String ean, String marque, String nom, String name) {
        this.mail = mail;
        this.ean = ean;
        this.marque = marque;
        this.nom = nom;
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
