package com.serk.ShopCompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Azap Serkan, ie04114
 * Cette activité permet d'ajouter une nouvelle carte de fidélité grâce au scanner
 */


public class LoyaltyCardAddWithScannerActivity extends Activity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "SCANNER";

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();         // Start camera

    }


    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.i(TAG, rawResult.getText()); // Prints scan results
        Log.i(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format

        String value = rawResult.toString();
        Intent eanIntent = new Intent(this, LoyaltyCardAddActivity.class);
        eanIntent.putExtra("ean", value);
        startActivity(eanIntent);
        finish();

    }


}
