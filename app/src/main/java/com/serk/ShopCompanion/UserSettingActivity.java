package com.serk.ShopCompanion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Azap Serkan, ie04114
 * Cette activité permet la suppression du compte connecté et supprime toutes les informations de l'utilisateur de la base de données
 */

public class UserSettingActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    Toolbar toolbar;
    EditText Nom, Mail;
    Button Del;
    AlertDialog.Builder builder;
    UserSharedPreferences userSharedPreferences;
    private String url = ServerURL.USER_DELETE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mon compte");

        userSharedPreferences = new UserSharedPreferences(this);
        final UserObject user = userSharedPreferences.getLoggedInUser();
        requestQueue = Volley.newRequestQueue(this);

        Nom = (EditText) findViewById(R.id.nom);
        Nom.setText(user.name);
        Nom.setEnabled(false);
        Mail = (EditText) findViewById(R.id.mail);
        Mail.setText(user.mail);
        Mail.setEnabled(false);
        Del = (Button) findViewById(R.id.bDelUser);

        builder = new AlertDialog.Builder(this);

        Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Nom.getText().toString().equals("") || Mail.getText().toString().equals("")) { //Verifie si les champs sont remplis
                    builder = new AlertDialog.Builder(UserSettingActivity.this);
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
                                    Toast.makeText(UserSettingActivity.this, "Au revoir " + ("\ud83d\ude14"), Toast.LENGTH_LONG).show();
                                    userSharedPreferences.clearUserData();
                                    userSharedPreferences.setUserLoggedIn(false);
                                    startActivity(new Intent(UserSettingActivity.this, LoginActivity.class));
                                    finish();
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
                            parameters.put("mail", Mail.getText().toString());
                            return parameters;
                        }
                    };
                    requestQueue.add(request);

                }

            }
        });
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
