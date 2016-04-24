package com.serk.ShopCompanion;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe utilise les sharedpreference pour stocker les informations de l'utilisateur connecté et le statu (utilisateur connecter/déconnecter)
 */

public class UserSharedPreferences {

    SharedPreferences userLocalDatabase;

    public UserSharedPreferences(Context context) { //get context from calling activity
        userLocalDatabase = context.getSharedPreferences("userDetails", 0);
    }

    /**
     * Ajoute les informations de l'utilisateur dans les sharedpreference
     *
     * @param un utilisateur
     */
    public void storeUserData(UserObject user) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("name", user.name);
        userLocalDatabaseEditor.putString("mail", user.mail);
        userLocalDatabaseEditor.apply();
    }

    /**
     * Retourne les information de l'utulisateur connecté
     *
     * @return
     */
    public UserObject getLoggedInUser() {
        if (!userLocalDatabase.getBoolean("loggedIn", false)) {
            return null;
        }
        String name = userLocalDatabase.getString("name", "");
        String mail = userLocalDatabase.getString("mail", "");

        UserObject storedUser = new UserObject(name, mail);
        return storedUser;
    }

    /**
     * Met le status d'utilisateur connecté a vrai
     *
     * @param
     */
    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("loggedIn", loggedIn);
        userLocalDatabaseEditor.apply();
    }

    /**
     * Efface les informations de l'utilisateur pour la déconnection
     */
    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.apply();
    }

}
