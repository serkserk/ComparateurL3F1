package com.serk.ShopCompanion;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe contient tous les liens pour les différentes requêtes réalisées par l'application
 */
public class ServerURL {

    public static final String MAIN_URL = "http://comparateur.16mb.com/";   //Lien a changer si changement de host en gardant les fichiers php

    public static final String REGISTER = MAIN_URL + "UserAdd.php";
    public static final String LOGIN = MAIN_URL + "UserGet.php";
    public static final String USER_DELETE = MAIN_URL + "UserDelete.php";

    public static final String PRODUCT_GET = MAIN_URL + "ProductGet.php?ean=";
    public static final String PRODUCT_ADD = MAIN_URL + "ProductAdd.php";

    public static final String PRODUCT_SEARCH_MARQUE = MAIN_URL + "ProductMarqueSearch.php?marque=";
    public static final String PRODUCT_SEARCH_NOM = MAIN_URL + "ProductNameSearch.php?nom=";

    public static final String PRODUCT_COMMENT_GET = MAIN_URL + "ProductCommentGet.php?ean=";
    public static final String PRODUCT_COMMENT_ADD = MAIN_URL + "ProductCommentAdd.php";

    public static final String CARD_GET = MAIN_URL + "CardGet.php?mail=";
    public static final String CARD_ADD = MAIN_URL + "CardAdd.php";
    public static final String CARD_DEL = MAIN_URL + "CardDel.php";

    public static final String LIST_GET = MAIN_URL + "ListGet.php?mail=";
    public static final String LIST_ADD = MAIN_URL + "ListAdd.php";
    public static final String LIST_DEL = MAIN_URL + "ListDelete.php";

    public static final String PRODUCT_IN_LIST_GET = MAIN_URL + "ListProductGet.php?mail=";
    public static final String PRODUCT_IN_LIST_ADD = MAIN_URL + "ListProductAdd.php";
    public static final String PRODUCT_IN_LIST_DEL = MAIN_URL + "ListProductDel.php";

    public static final String SHOP_GET = MAIN_URL + "ShopGet.php";
}
