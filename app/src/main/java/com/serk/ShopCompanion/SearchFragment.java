package com.serk.ShopCompanion;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Azap Serkan, ie04114
 * Ce fragment accessible depuis le navigation drawer affiche le formulaire de recherche de produit
 */

public class SearchFragment extends Fragment {

    EditText nom, marque;
    Button nomsearch, marquesearch;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);

        nom = (EditText) rootView.findViewById(R.id.nom);
        marque = (EditText) rootView.findViewById(R.id.marque);
        nomsearch = (Button) rootView.findViewById(R.id.nomsearch);
        marquesearch = (Button) rootView.findViewById(R.id.marquesearch);

        nomsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                intent.putExtra("nom", nom.getText().toString());
                startActivity(intent);
            }
        });

        marquesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                intent.putExtra("marque", marque.getText().toString());
                startActivity(intent);
            }
        });

        return rootView;
    }


}
