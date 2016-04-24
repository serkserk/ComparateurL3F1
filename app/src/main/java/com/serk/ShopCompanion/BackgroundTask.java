package com.serk.ShopCompanion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe s'occupe de la connexion au serveur en arrière-plan
 * pour la création de compte et la connexion à l’application.
 * Elle utilise les librairies natives d’Android
 */

public class BackgroundTask extends AsyncTask<String, Void, String> {

    String register = ServerURL.REGISTER;
    String login = ServerURL.LOGIN;
    Context ctx;
    Activity activity;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;

    UserObject user = new UserObject("", "");
    UserSharedPreferences userSharedPreferences;

    public BackgroundTask(Context ctx) {
        this.ctx = ctx;
        userSharedPreferences = new UserSharedPreferences(ctx);
        activity = (Activity) ctx;
    }

    /**
     * Tache a realiser avant l'operation
     * initialisation de la fenetre de chargement
     */
    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(activity);
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Connexion");
        progressDialog.setMessage("Veuillez patienter");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * Realise l'operation de connexion/creation de compte
     *
     * @param type d'operation : creation ou connexion et les données de l'utilisateur
     * @return reponse du serveur
     */
    @Override
    protected String doInBackground(String... params) {

        String method = params[0];
        if (method.equals("register")) {  //Si creation de compte
            try {
                URL url = new URL(register);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String mail = params[1];
                String name = params[2];
                String password = params[3];
                String data = URLEncoder.encode("mail", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8") + "&" +
                        URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                Thread.sleep(5000);
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (method.equals("login")) { // Si connexion
            try {
                URL url = new URL(login);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String mail, password;
                mail = params[1];

                user.setMail(mail);

                password = params[2];
                String data = URLEncoder.encode("mail", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");

                }
                httpURLConnection.disconnect();
                Thread.sleep(5000);
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    /**
     * Tache a realiser apres avoir recu la reponse du serveur
     * Boite de dialogue correspondant au succes ou echec de l'opération
     *
     * @param reponse du serveur
     */
    @Override
    protected void onPostExecute(String json) {
        try {
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            JSONObject jo = jsonArray.getJSONObject(0);
            String code = jo.getString("code");
            String message = jo.getString("message");
            if (code.equals("reg_true")) {
                showDialog(code);
            } else if (code.equals("reg_false")) {
                showDialog(code);
            } else if (code.equals("login_true")) {

                user.setName(message);
                userSharedPreferences.storeUserData(user);
                userSharedPreferences.setUserLoggedIn(true);

                Intent intent = new Intent(activity, HomeActivity.class);
                activity.startActivity(intent);

            } else if (code.equals("login_false")) {
                showDialog(code);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Fonction pour créer la fenetre de dialogue
     *
     * @param code    du serveur
     */
    public void showDialog(String code) {
        if (code.equals("reg_true") ) {
            builder.setTitle("Création de compte");
            builder.setMessage("Compte créer");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    activity.finish();
                }
            });
        } else if (code.equals("login_false")) {
            builder.setTitle("Connexion");
            builder.setMessage("Compte non existant");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText mail, password;
                    mail = (EditText) activity.findViewById(R.id.lMail);
                    password = (EditText) activity.findViewById(R.id.lPassword);
                    mail.setText("");
                    password.setText("");
                    dialog.dismiss();
                }
            });
        }else if (code.equals("reg_false")){
            builder.setTitle("Création de compte");
            builder.setMessage("Mail déja utilisé");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    activity.finish();
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
