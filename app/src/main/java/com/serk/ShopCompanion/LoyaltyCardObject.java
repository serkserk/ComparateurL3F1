package com.serk.ShopCompanion;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe défini une carte de fidélité
 */

public class LoyaltyCardObject {

    int image;
    private String mail, marque, code;


    public LoyaltyCardObject() {
    }

    public LoyaltyCardObject(String mail, String marque, String code, int image) {
        this.mail = mail;
        this.marque = marque;
        this.code = code;
        this.image = image;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
