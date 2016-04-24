package com.serk.ShopCompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Azap Serkan, ie04114
 * Cette activité est l’hôte de la vue du code barre
 */

public class LoyaltyCardShowCodeActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loyaltycard_code_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Codebar");

        Intent intentcode = getIntent();
        String code = intentcode.getStringExtra("code");

        LoyaltyCardBarcodeView view = new LoyaltyCardBarcodeView(this, code);
        setContentView(view);
    }

    /**
     * Gestion du clique sur la croix a gauche de la toolbar
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
