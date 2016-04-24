package com.serk.ShopCompanion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Azap Serkan, ie04114
 * Cette activité permet la connexion à l'application s’il n'y a pas d'utilisateur connecter
 */


public class LoginActivity extends AppCompatActivity {

    EditText Mail, Password;
    Button Login;
    TextView toRegister;
    AlertDialog.Builder builder;

    UserObject user;
    UserSharedPreferences userSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        user = new UserObject("", "");
        userSharedPreferences = new UserSharedPreferences(this);

        Mail = (EditText) findViewById(R.id.lMail);
        Password = (EditText) findViewById(R.id.lPassword);
        Login = (Button) findViewById(R.id.bLogin);
        toRegister = (TextView) findViewById(R.id.toRegister);

        toRegister.setOnClickListener(new View.OnClickListener() { //Lance l'activité de creation de compte
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = Mail.getText().toString();

                if (Mail.getText().toString().equals("") || Password.getText().toString().equals("")) { //Vérifie si les champs sont remplis
                    builder = new AlertDialog.Builder(LoginActivity.this);
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
                } else if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches())) {
                    Mail.setError("Mail non valide");
                } else {
                /*
                    OkHttpClient client = new OkHttpClient(); //teste avec Okhttp au lieu des methodes natif plus complèxe

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("mail", Mail.getText().toString())
                            .addFormDataPart("password", Password.getText().toString() )
                            .build();

                    final Request request = new Request.Builder()
                            .url("http://comparateur.16mb.com/UserGet.php/")
                            .post(requestBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(getBaseContext(), "onFailure", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            try {
                                //progressDialog.dismiss();
                                String json = response.body().string();
                                JSONObject jsonObject = new JSONObject(json);
                                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                                JSONObject jo = jsonArray.getJSONObject(0);
                                String code = jo.getString("code");
                                String message = jo.getString("message");

                                if (code.equals("login_true")) {


                                    userSharedPreferences.clearUserData();

                                    user.setMail(Mail.getText().toString());
                                    user.setName(message);

                                    Log.i(TAG, "user.setName(message)" + user.name);
                                    Log.i(TAG, "user.setName(mail)" + user.mail);

                                    userSharedPreferences.storeUserData(user);
                                    userSharedPreferences.setUserLoggedIn(true);

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else if (code.equals("login_false")) {
                                     showDialog("Login fail", message, code);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    */
                    BackgroundTask bt = new BackgroundTask(LoginActivity.this);
                    bt.execute("login", Mail.getText().toString(), Password.getText().toString()); //Exécute la requète en arrière plan
                }
            }
        });
    }

    /**
     * Empeche d'aller vers l'activité principale
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * Fonction pour créer la fenetre de dialogue
     *
     * @param title   de la fenetre
     * @param message du serveur
     * @param code    du serveur
     */
    public void showDialog(String title, String message, String code) {
        builder.setTitle(title);
        if (code.equals("reg_true") || code.equals("reg_false")) {
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });

        } else if (code.equals("login_false")) {
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText mail, password;
                    mail = (EditText) findViewById(R.id.lMail);
                    password = (EditText) findViewById(R.id.lPassword);
                    mail.setText("");
                    password.setText("");
                    dialog.dismiss();
                }
            });

        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}

