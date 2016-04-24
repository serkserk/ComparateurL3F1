package com.serk.ShopCompanion;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe défini un produit
 */

public class ScannedProductObject {
    private String ean, marque, nom, desc, shop, price;


    public ScannedProductObject() {
    }

    public ScannedProductObject(String ean, String marque, String nom, String desc, String shop, String price) {
        this.ean = ean;
        this.marque = marque;
        this.nom = nom;
        this.desc = desc;
        this.shop = shop;
        this.price = price;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
