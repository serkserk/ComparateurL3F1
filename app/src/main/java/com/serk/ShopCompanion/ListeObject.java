package com.serk.ShopCompanion;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe défini une liste d'achat
 */

public class ListeObject {

    private String mail, name, description;


    public ListeObject() {
    }

    public ListeObject(String mail, String name, String description) {
        this.mail = mail;
        this.name = name;
        this.description = description;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
