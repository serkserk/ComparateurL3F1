package com.serk.ShopCompanion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
 * Cette activité affiche les commentaires d'un produit
 */

public class ProductCommentResultActivity extends AppCompatActivity {

    ProductCommentRVAdapter adapter;
    ProgressDialog dialog;
    TextView addComment;
    Toolbar toolbar;
    UserSharedPreferences userSharedPreferences;
    ArrayList<ProductCommentObject> comments = new ArrayList<ProductCommentObject>();
    String url = ServerURL.PRODUCT_COMMENT_GET;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.productcomment_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Commentaire du produit");
        addComment = (TextView) findViewById(R.id.addComment);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductCommentResultActivity.this, ProductCommentResultAddActivity.class);
                intent.putExtra("ean", getIntent().getStringExtra("ean"));
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_comment);
        adapter = new ProductCommentRVAdapter(this, comments);
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
                url + getIntent().getStringExtra("ean"),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("TAG", "oui");
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                ProductCommentObject comment = new ProductCommentObject();
                                comment.setMail(object.getString("mail"));
                                comment.setEan(object.getString("ean"));
                                comment.setComment(object.getString("comment"));

                                comments.add(comment);
                            }
                            hideDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                View coordinatorLayoutView = findViewById(R.id.commentinlist);
                Log.d("ERROR!!!", error.toString());
                hideDialog();
                //Si l'utilisateur n'a pas de produit dans la liste, affiche un snackbar qui propose d'en ajouter
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayoutView, "Aucun commentaire pour ce produit", Snackbar.LENGTH_LONG)
                        .setAction("Ajouter", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ProductCommentResultActivity.this, ProductCommentResultAddActivity.class);
                                intent.putExtra("ean", getIntent().getStringExtra("ean"));
                                startActivity(intent);
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
