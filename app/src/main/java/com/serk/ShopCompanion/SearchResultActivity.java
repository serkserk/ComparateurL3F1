package com.serk.ShopCompanion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

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
 * Cette activité affiche les résultats de la recherche grâce a une liste de produit depuis la base de données
 */

public class SearchResultActivity extends AppCompatActivity {

    ScannedProductRVAdapter adapter;
    ProgressDialog dialog;
    Toolbar toolbar;
    AlertDialog.Builder builder;
    private ArrayList<ScannedProductObject> products = new ArrayList<ScannedProductObject>();
    private String get = ServerURL.PRODUCT_GET;
    private String searchNom = ServerURL.PRODUCT_SEARCH_NOM;
    private String searchMarque = ServerURL.PRODUCT_SEARCH_MARQUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Résultat recherche");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_product);
        adapter = new ScannedProductRVAdapter(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        adapter.setData(products);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Chargement");
        dialog.setCancelable(true);

        showDialog();
        getAndInitData();

    }

    public void getAndInitData() {  //Recupère les produits correspondant au scan et les affiches dans un recycler view sous forme de cardview

        if (!(getIntent().getStringExtra("nom") == null)) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, searchNom + getIntent().getStringExtra("nom"),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                Log.d("MYAPP", response.toString());
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject object = response.getJSONObject(i);
                                    ScannedProductObject product = new ScannedProductObject();
                                    product.setEan(object.getString("ean"));
                                    product.setMarque(object.getString("marque"));
                                    product.setNom(object.getString("nom"));
                                    product.setDesc(object.getString("description"));
                                    product.setShop(object.getString("shop"));
                                    product.setPrice(object.getString("price"));

                                    products.add(product);
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
                    Log.d("ERROR!!!", error.toString());
                    hideDialog();

                    builder = new AlertDialog.Builder(SearchResultActivity.this);
                    builder.setTitle("Produit inconnu");
                    builder.setMessage("Ajouter le produit ?");
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Log.i("Alert", "YES");
                            Intent intent = new Intent(SearchResultActivity.this, ScannedProductScannerResultAddProductActivity.class);
                            intent.putExtra("ean", getIntent().getStringExtra("ean"));
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Log.i("Alert", "NO");
                            startActivity(new Intent(SearchResultActivity.this, HomeActivity.class));
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });
            AppController.getInstance().getRequestQueue().add(jsonArrayRequest);


        }


        if (!(getIntent().getStringExtra("marque") == null)) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, searchMarque + getIntent().getStringExtra("marque"),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                Log.d("MYAPP", response.toString());
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject object = response.getJSONObject(i);
                                    ScannedProductObject product = new ScannedProductObject();
                                    product.setEan(object.getString("ean"));
                                    product.setMarque(object.getString("marque"));
                                    product.setNom(object.getString("nom"));
                                    product.setDesc(object.getString("description"));
                                    product.setShop(object.getString("shop"));
                                    product.setPrice(object.getString("price"));

                                    products.add(product);
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
                    Log.d("ERROR!!!", error.toString());
                    hideDialog();

                    builder = new AlertDialog.Builder(SearchResultActivity.this);
                    builder.setTitle("Produit inconnu");
                    builder.setMessage("Ajouter le produit ?");
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Log.i("Alert", "YES");
                            Intent intent = new Intent(SearchResultActivity.this, ScannedProductScannerResultAddProductActivity.class);
                            intent.putExtra("ean", getIntent().getStringExtra("ean"));
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Log.i("Alert", "NO");
                            startActivity(new Intent(SearchResultActivity.this, HomeActivity.class));
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });
            AppController.getInstance().getRequestQueue().add(jsonArrayRequest);


        }

    }

    public void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    public void hideDialog() {
        if (dialog.isShowing())
            dialog.hide();
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