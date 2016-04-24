package com.serk.ShopCompanion;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe permet d'ajouter un produit dans la base de données apres avoir effectué un scan sans résultat
 */

public class ScannedProductScannerResultAddProductActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    Toolbar toolbar;
    EditText ean, marque, nom, description, price;
    AutoCompleteTextView shop;
    FloatingActionButton add;
    AlertDialog.Builder builder;
    ArrayList<String> shopSuggestionList = new ArrayList<String>();
    String url2 = ServerURL.SHOP_GET;
    String url = ServerURL.PRODUCT_ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanned_product_add_from_scanner_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ajouter un produit");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        ean = (EditText) findViewById(R.id.ean);
        ean.setText(getIntent().getStringExtra("ean"));
        ean.setEnabled(false);
        marque = (EditText) findViewById(R.id.marque);
        nom = (EditText) findViewById(R.id.nom);
        description = (EditText) findViewById(R.id.description);
        price = (EditText) findViewById(R.id.price);
        shop = (AutoCompleteTextView) findViewById(R.id.shop);
        getShopsNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, shopSuggestionList);
        shop.setAdapter(adapter);

        add = (FloatingActionButton) findViewById(R.id.bAddProduct);

        builder = new AlertDialog.Builder(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (marque.getText().toString().equals("") || nom.getText().toString().equals("")
                        || description.getText().toString().equals("") || price.getText().toString().equals("")
                        || shop.getText().toString().equals("")) {  //Vérifie si les champs sont remplis
                    builder = new AlertDialog.Builder(ScannedProductScannerResultAddProductActivity.this);
                    builder.setTitle("Attention");
                    builder.setMessage("Remplir tous les champs");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {    // Ajoute le produit dans la base de données

                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                                        JSONObject jo = jsonArray.getJSONObject(0);
                                        String code = jo.getString("code");
                                        String message = jo.getString("message");
                                        if (code.equals("postproduct_false")) {
                                            showDialog(code);
                                        }
                                        if (code.equals("postproduct_true")) {
                                            showDialog( code);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {    // Paramètres de la requete
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("ean", getIntent().getStringExtra("ean"));
                            parameters.put("marque", marque.getText().toString());
                            parameters.put("nom", nom.getText().toString());
                            parameters.put("description", description.getText().toString());
                            parameters.put("shop", shop.getText().toString());
                            parameters.put("price", price.getText().toString());
                            return parameters;
                        }
                    };
                    requestQueue.add(request);

                }

            }
        });

    }

    public void getShopsNames() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url2,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                ShopSuggestionObject shopSuggestionObject = new ShopSuggestionObject();
                                shopSuggestionObject.setName(object.getString("nom"));
                                shopSuggestionList.add(object.getString("nom"));
                            }
                            for (int i = 0; i < shopSuggestionList.size(); i++) {
                                Log.i("GETSHOPNAME   ", shopSuggestionList.get(i));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR!!!", error.toString());
            }
        });
        AppController.getInstance().getRequestQueue().add(jsonArrayRequest);
    }

    /**
     * Fonction pour créer la fenetre de dialogue
     *
     * @param code    du serveur
     */
    public void showDialog(String code) {

        if (code.equals("postproduct_false")) {
            builder.setTitle("Erreur");
            builder.setMessage("Produit non ajouté");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ean.setText("");
                    marque.setText("");
                    nom.setText("");
                }
            });
        } else if (code.equals("postproduct_true")) {
            builder.setTitle("Ajout");
            builder.setMessage("Produit ajouté !");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
