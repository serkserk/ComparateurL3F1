package com.serk.ShopCompanion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Azap Serkan, ie04114
 * Cette activité permet d'ajouter un produit dans une liste d'achat
 */

public class ProductInListeAddActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    Toolbar toolbar;
    EditText ean, marque, nom, name;
    FloatingActionButton Add;
    AlertDialog.Builder builder;
    UserSharedPreferences userSharedPreferences;
    private String url = ServerURL.PRODUCT_IN_LIST_ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productinliste_add_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ajouter un produit");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        ean = (EditText) findViewById(R.id.ean);
        marque = (EditText) findViewById(R.id.marque);
        nom = (EditText) findViewById(R.id.nom);
        name = (EditText) findViewById(R.id.name);

        name.setText(getIntent().getStringExtra("name"));
        name.setEnabled(false);

        if (!(getIntent().getStringExtra("ean") == null)) {
            Log.i("SCAN EAN", getIntent().getStringExtra("ean"));
            ean.setText(getIntent().getStringExtra("ean"));
            ean.setEnabled(false);
        }

        Add = (FloatingActionButton) findViewById(R.id.bAddProduct);

        userSharedPreferences = new UserSharedPreferences(this);
        final UserObject user = userSharedPreferences.getLoggedInUser();

        builder = new AlertDialog.Builder(this);


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ean.getText().toString().equals("") || marque.getText().toString().equals("")
                        || nom.getText().toString().equals("")) {   //Verifie si les champs sont remplis
                    builder = new AlertDialog.Builder(ProductInListeAddActivity.this);
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
                } else {     // Ajoute le produit dans la liste d'achat dans la base de données

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
                                        if (code.equals("postlistproduct_false")) {
                                            showDialog( code);
                                        }
                                        if (code.equals("postlistproduct_true")) {
                                            showDialog(code);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("MYAPP", "onErrorResponse()");
                        }
                    }) {    // Paramètres de la requete
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("mail", user.mail);
                            parameters.put("ean", ean.getText().toString());
                            parameters.put("marque", marque.getText().toString());
                            parameters.put("nom", nom.getText().toString());
                            parameters.put("name", getIntent().getStringExtra("name"));
                            return parameters;
                        }
                    };
                    requestQueue.add(request);

                }

            }
        });

    }

    /**
     * Fonction pour créer la fenetre de dialogue
     *
     * @param code    du serveur
     */
    public void showDialog(String code) {
        if (code.equals("postlistproduct_false")) {
            builder.setTitle("Erreur");
            builder.setMessage("Liste non créer");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ean.setText("");
                    marque.setText("");
                    nom.setText("");
                }
            });

        } else if (code.equals("postlistproduct_true")) {
            builder.setTitle("Ajout");
            builder.setMessage("Liste créer");
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
