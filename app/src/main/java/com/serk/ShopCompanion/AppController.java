package com.serk.ShopCompanion;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Azap Serkan, ie04114
 * Object singleton qui limite l'acces a la base de donnée en limitant a 1 RequestQueue
 * Cette classe définit un singleton qui permet de s'assurer qu'une seule instance de Volley
 * sera instanciée pendant toute la durée de votre application.
 */

public class AppController extends Application {
    private static AppController Instance;
    private RequestQueue RequestQueue;

    public static AppController getInstance() {
        return Instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
    }

    public RequestQueue getRequestQueue() {
        if (RequestQueue == null)
            RequestQueue = Volley.newRequestQueue(getApplicationContext());
        return RequestQueue;
    }
}
