package com.serk.ShopCompanion;

import android.app.ProgressDialog;
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
 * Ce fragment accesible depuis le navigation drawer affiche les listes d'achat de l'utilisateur
 */

public class ListesListFragment extends android.support.v4.app.Fragment {

    ListesRVAdapter adapter;
    ProgressDialog dialog;
    TextView addListes;
    UserSharedPreferences userSharedPreferences;
    ArrayList<ListeObject> listes = new ArrayList<ListeObject>();
    String url = ServerURL.LIST_GET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.listes_list_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_listes);
        recyclerView.setHasFixedSize(true);
        adapter = new ListesRVAdapter(getActivity(), listes);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        addListes = (TextView) rootView.findViewById(R.id.addListes);
        //adapter.setData(listes);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Chargement");
        dialog.setCancelable(true);
        showDialog();

        addListes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListesCreateActivity.class));
            }
        });

        getAndInitData();

        return rootView;
    }

    public void getAndInitData() {  // Recupère les listes d'achat de l'utilisateur et les affiches dans un recycler view sous forme de cardview

        userSharedPreferences = new UserSharedPreferences(getActivity());
        final UserObject user = userSharedPreferences.getLoggedInUser();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + user.mail,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                ListeObject liste = new ListeObject();
                                liste.setMail(object.getString("mail"));
                                liste.setName(object.getString("name"));
                                liste.setDescription(object.getString("description"));

                                listes.add(liste);
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
                LinearLayout ll = (LinearLayout) view.findViewById(R.id.listeslist);
                Log.d("ERROR!!!", error.toString());
                hideDialog();
                Snackbar snackbar = Snackbar //Si l'utilisateur n'a pas de liste d'achat, affiche un snackbar qui propose d'en ajouter une
                        .make(ll, "Aucune liste", Snackbar.LENGTH_LONG)
                        .setAction("Ajouter", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getActivity(), ListesCreateActivity.class));
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
