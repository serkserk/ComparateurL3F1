package com.serk.ShopCompanion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
 * Cette activité permet d'ajouter une nouvelle carte de fidélité
 */

public class LoyaltyCardAddActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    Toolbar toolbar;
    EditText Code;
    AutoCompleteTextView Marque;
    FloatingActionButton Add;
    AlertDialog.Builder builder;
    UserSharedPreferences userSharedPreferences;
    ArrayList<String> shopSuggestionList = new ArrayList<String>();
    String url2 = ServerURL.SHOP_GET;
    String[] marque = {"leclerc", "carrefour", "casino", "g20", "monoprix", "franprix", "auchan"};
    private String url = ServerURL.CARD_ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loyaltycard_add_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ajouter une carte de fidélité");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Code = (EditText) findViewById(R.id.code);
        Add = (FloatingActionButton) findViewById(R.id.bAddCard);
        userSharedPreferences = new UserSharedPreferences(this);
        final UserObject user = userSharedPreferences.getLoggedInUser();

        if (!(getIntent().getStringExtra("ean") == null)) {
            Log.i("SCAN EAN", getIntent().getStringExtra("ean"));
            Code.setText(getIntent().getStringExtra("ean"));
            Code.setEnabled(false);
        }

        builder = new AlertDialog.Builder(this);

        Marque = (AutoCompleteTextView) findViewById(R.id.marque);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, marque);
        Marque.setAdapter(adapter);
        Marque.setThreshold(1);
        Marque.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Marque.showDropDown();
                }
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Marque.getText().toString().equals("") || Code.getText().toString().equals("")) { //Verifie si les champs sont remplis
                    builder = new AlertDialog.Builder(LoyaltyCardAddActivity.this);
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
                } else { // Ajoute la carte de fidelité dans la base de données
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
                                        if (code.equals("PostCard_false")) {
                                            showDialog(code);
                                        }
                                        if (code.equals("postcard_true")) {
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
                    }) { // Paramètres de la requete
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("mail", user.mail);
                            parameters.put("marque", Marque.getText().toString());
                            parameters.put("code", Code.getText().toString());
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
        if (code.equals("PostCard_false")) {
            builder.setTitle("Erreur");
            builder.setMessage("Carte non ajouté");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Marque.setText("");
                    Code.setText("");
                }
            });
        } else if (code.equals("postcard_true")) {
            builder.setTitle("Ajout");
            builder.setMessage("Carte enregistré");
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
