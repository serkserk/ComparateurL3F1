package com.serk.ShopCompanion;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe défini un utilisateur
 */

public class UserObject {

    String name, mail;

    public UserObject(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
