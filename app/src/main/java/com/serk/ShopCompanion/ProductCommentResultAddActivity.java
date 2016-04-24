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
import android.widget.Toast;

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
 * Cette activité permet d'ajouter un commentaire pour un produit
 */

public class ProductCommentResultAddActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    Toolbar toolbar;
    EditText ean, mail, comment;
    FloatingActionButton Add;
    AlertDialog.Builder builder;
    UserSharedPreferences userSharedPreferences;
    private String url = ServerURL.PRODUCT_COMMENT_ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_comment_result_add_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ajouter un commentaire");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        userSharedPreferences = new UserSharedPreferences(this);
        final UserObject user = userSharedPreferences.getLoggedInUser();

        ean = (EditText) findViewById(R.id.ean);
        ean.setText(getIntent().getStringExtra("ean"));
        ean.setEnabled(false);
        mail = (EditText) findViewById(R.id.mail);
        mail.setText(user.getMail());
        mail.setEnabled(false);
        comment = (EditText) findViewById(R.id.comment);
        Add = (FloatingActionButton) findViewById(R.id.addComment);


        builder = new AlertDialog.Builder(this);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (comment.getText().toString().equals("")) { //Verifie si les champs sont remplis
                    builder = new AlertDialog.Builder(ProductCommentResultAddActivity.this);
                    builder.setTitle("Attention");
                    builder.setMessage("Ajouter un commentaire");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {  // // Ajoute la liste d'achat dans la base de données

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
                                        if (code.equals("PostComment_false")) {
                                            showDialog(code);
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
                            Toast.makeText(ProductCommentResultAddActivity.this, "Veulliez réessayer !", Toast.LENGTH_LONG).show();
                        }
                    }) {  // Paramètres de la requete
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("ean", ean.getText().toString());
                            parameters.put("mail", user.getMail());
                            parameters.put("comment", comment.getText().toString());
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

        if (code.equals("PostComment_false")) {
            builder.setTitle("Erreur");
            builder.setMessage("Vous avez déja commenté ce produit");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    comment.setText("");
                }
            });

        } else if (code.equals("postlistproduct_true")) {
            builder.setTitle("Ajout");
            builder.setMessage("Commentaire ajouté");
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