package com.serk.ShopCompanion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
 * Ce fragment accessible depuis le navigation drawer affiche les cartes de fidélité de l'utilisateur
 */

public class LoyaltyCardListFragment extends android.support.v4.app.Fragment {

    LoyaltyCardRVAdapter adapter;
    ProgressDialog dialog;
    TextView addCards;
    UserSharedPreferences userSharedPreferences;
    ArrayList<LoyaltyCardObject> cards = new ArrayList<LoyaltyCardObject>();
    String url = ServerURL.CARD_GET;
    AlertDialog.Builder builder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.loyaltycard_list_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_cards);
        recyclerView.setHasFixedSize(true);
        adapter = new LoyaltyCardRVAdapter(getActivity(), cards);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        addCards = (TextView) rootView.findViewById(R.id.addCards);

        adapter.setData(cards);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Chargement");
        dialog.setCancelable(true);
        showDialog();

        addCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ajout de carte");
                builder.setMessage("Comment ajouter ?");
                builder.setPositiveButton("Scanner", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Log.i("Alert", "YES");
                        Intent intent = new Intent(getActivity(), LoyaltyCardAddWithScannerActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Manuel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Log.i("Alert", "NO");
                        startActivity(new Intent(getActivity(), LoyaltyCardAddActivity.class));
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        getAndInitData();

        return rootView;
    }

    public void getAndInitData() { //Recupère les cartes de l'utilisateur et les affiches dans un recycler view sous forme de cardview

        userSharedPreferences = new UserSharedPreferences(getActivity());
        final UserObject user = userSharedPreferences.getLoggedInUser();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + user.mail,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                LoyaltyCardObject card = new LoyaltyCardObject();
                                card.setMail(object.getString("mail"));
                                card.setMarque(object.getString("marque"));
                                card.setCode(object.getString("code"));

                                cards.add(card);
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
                View view = getView();
                LinearLayout ll = (LinearLayout) view.findViewById(R.id.cardlist);
                Log.d("ERROR!!!", error.toString());
                hideDialog();
                Snackbar snackbar = Snackbar  //Si l'utilisateur n'a pas de carte de fidelite, affiche un snackbar qui propose d'ajouter une carte
                        .make(ll, "Aucune carte", Snackbar.LENGTH_LONG)
                        .setAction("Ajouter", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Ajout de carte");
                                builder.setMessage("Comment ajouter ?");
                                builder.setPositiveButton("Scanner", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Log.i("Alert", "YES");
                                        Intent intent = new Intent(getActivity(), LoyaltyCardAddWithScannerActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("Manuel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Log.i("Alert", "NO");
                                        startActivity(new Intent(getActivity(), LoyaltyCardAddActivity.class));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dialog.hide();
        dialog.dismiss();
    }
}
