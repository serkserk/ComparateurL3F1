package com.serk.ShopCompanion;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe défini un commentaire de produit
 */
public class ProductCommentObject {
    String mail, ean, comment;

    public ProductCommentObject(String mail, String ean, String comment) {
        this.mail = mail;
        this.ean = ean;
        this.comment = comment;
    }

    public ProductCommentObject() {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
