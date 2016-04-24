package com.serk.ShopCompanion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe affiche les produits contenus dans une liste d'achat
 */

public class ProductInListeActivity extends AppCompatActivity {

    ProductInListesRVAdapter adapter;
    ProgressDialog dialog;
    TextView addProduct;
    Toolbar toolbar;
    UserSharedPreferences userSharedPreferences;
    ArrayList<ProductInListeObject> productInListes = new ArrayList<ProductInListeObject>();
    String url = ServerURL.PRODUCT_IN_LIST_GET;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_in_liste_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Produit dans la liste");
        addProduct = (TextView) findViewById(R.id.addProduct);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(ProductInListeActivity.this);
                builder.setTitle("Ajout de produit");
                builder.setMessage("Comment ajouter ?");
                builder.setPositiveButton("Scanner", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Log.i("Alert", "YES");
                        Intent intent = new Intent(ProductInListeActivity.this, ProductInListAddWithScannerActivity.class);
                        intent.putExtra("name", getIntent().getStringExtra("name"));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Manuel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Log.i("Alert", "NO");
                        Intent intent = new Intent(ProductInListeActivity.this, ProductInListeAddActivity.class);
                        intent.putExtra("name", getIntent().getStringExtra("name"));
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_product_in_list);
        adapter = new ProductInListesRVAdapter(this, productInListes);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        //adapter.setData(productInListes);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Chargement");
        dialog.setCancelable(true);
        showDialog();
        getAndInitData();

    }

    public void getAndInitData() {  //Recupère les produits d'une liste de l'utilisateur et les affiches dans un recycler view sous forme de cardview

        userSharedPreferences = new UserSharedPreferences(this);
        UserObject user = userSharedPreferences.getLoggedInUser();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url + user.mail + "&name=" + getIntent().getStringExtra("name"),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                ProductInListeObject productInListe = new ProductInListeObject();
                                productInListe.setMail(object.getString("mail"));
                                productInListe.setEan(object.getString("ean"));
                                productInListe.setMarque(object.getString("marque"));
                                productInListe.setNom(object.getString("nom"));
                                productInListe.setName(object.getString("name"));

                                productInListes.add(productInListe);
                            }
                            hideDialog();
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                View coordinatorLayoutView = findViewById(R.id.productinlist);
                Log.d("ERROR!!!", error.toString());
                hideDialog();
                //Si l'utilisateur n'a pas de produit dans la liste, affiche un snackbar qui propose d'en ajouter
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayoutView, "Aucun produit dans la liste", Snackbar.LENGTH_LONG)
                        .setAction("Ajouter", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                builder = new AlertDialog.Builder(ProductInListeActivity.this);
                                builder.setTitle("Ajout de produit");
                                builder.setMessage("Comment ajouter ?");
                                builder.setPositiveButton("Scanner", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Log.i("Alert", "YES");
                                        Intent intent = new Intent(ProductInListeActivity.this, ProductInListAddWithScannerActivity.class);
                                        intent.putExtra("name", getIntent().getStringExtra("name"));
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("Manuel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Log.i("Alert", "NO");
                                        Intent intent = new Intent(ProductInListeActivity.this, ProductInListeAddActivity.class);
                                        intent.putExtra("name", getIntent().getStringExtra("name"));
                                        startActivity(intent);
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        });
                snackbar.show();


            }
        });
        AppController.getInstance().getRequestQueue().add(jsonArrayRequest);
    }

    public void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    public void hideDialog() {
        if (dialog.isShowing())
            dialog.hide();
    }


}
