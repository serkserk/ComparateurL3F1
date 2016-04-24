package com.serk.ShopCompanion;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe définit l'objet magasin suggéré
 */

public class ShopSuggestionObject {

    private String name;

    public ShopSuggestionObject(String name) {
        this.name = name;
    }

    public ShopSuggestionObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}