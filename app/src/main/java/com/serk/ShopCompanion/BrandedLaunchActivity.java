package com.serk.ShopCompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Azap Serkan, ie04114
 * Cette activité affiche l'écran de lancement et détermine s’il faut afficher le HomeActivity ou le LoginActivity
 */

public class BrandedLaunchActivity extends AppCompatActivity {

    UserSharedPreferences userSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSharedPreferences = new UserSharedPreferences(this);

        authenticate();

    }

    /**
     * Verifie si il y a un utilisateur, sinon affiche l'écran de connexion
     */
    private void authenticate() {
        if (userSharedPreferences.getLoggedInUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
