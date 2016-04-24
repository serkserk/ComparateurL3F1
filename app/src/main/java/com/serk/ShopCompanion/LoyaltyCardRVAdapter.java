package com.serk.ShopCompanion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe permet d'afficher les objets carte de fidélité (LoyaltyCardObject) dans le recyclerview
 */

public class LoyaltyCardRVAdapter extends RecyclerView.Adapter<LoyaltyCardRVAdapter.CardViewHolder> {

    static String url = ServerURL.CARD_DEL;
    ArrayList<LoyaltyCardObject> cards;
    Context context;

    public LoyaltyCardRVAdapter(Context context, ArrayList<LoyaltyCardObject> cards) {
        this.context = context;
        this.cards = cards;
    }

    public void setData(ArrayList<LoyaltyCardObject> cards) {
        this.cards = cards;
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        LoyaltyCardObject card = cards.get(position);

        holder.marque.setText(card.getMarque());
        holder.code.setText(card.getCode());
        int img;
        switch (card.getMarque()) {
            case "leclerc":
                img = R.drawable.leclerc;
                break;
            case "carrefour":
                img = R.drawable.carrefour;
                break;
            case "casino":
                img = R.drawable.casino;
                break;
            case "auchan":
                img = R.drawable.auchan;
                break;
            case "franprix":
                img = R.drawable.franprix;
                break;
            case "monoprix":
                img = R.drawable.monoprix;
                break;
            case "g20":
                img = R.drawable.g20;
                break;
            default:
                img = android.R.color.transparent;
        }
        holder.logo.setImageResource(img);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loyaltycard_row,
                parent, false);
        return new CardViewHolder(view, context, cards);
    }


    /**
     * Cette classe initialise chaque cardview avec l'object correspondant
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CardView cardView;
        TextView mail, marque, code;
        ArrayList<LoyaltyCardObject> cards = new ArrayList<>();
        Context context;
        AlertDialog.Builder alertDialogBuilder;
        RequestQueue requestQueue;
        ImageView logo;

        public CardViewHolder(View view, Context context, ArrayList<LoyaltyCardObject> cards) {
            super(view);
            this.cards = cards;
            this.context = context;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            cardView = (CardView) view.findViewById(R.id.card_listes);
            marque = (TextView) view.findViewById(R.id.marque);
            code = (TextView) view.findViewById(R.id.code);
            logo = (ImageView) view.findViewById(R.id.cardImage);
            alertDialogBuilder = new AlertDialog.Builder(context);
        }

        @Override
        public void onClick(View v) { //Gère le clique sur une cardview

            int position = getAdapterPosition();
            LoyaltyCardObject card = this.cards.get(position);
            Toast.makeText(context, "Codebarre pour la carte " + card.getMarque(), Toast.LENGTH_SHORT).show();
            Intent intentcode = new Intent(this.context.getApplicationContext(), LoyaltyCardShowCodeActivity.class);
            intentcode.putExtra("code", card.getCode());
            this.context.startActivity(intentcode);
        }

        @Override
        public boolean onLongClick(View v) {

            int position = getAdapterPosition();
            final LoyaltyCardObject card = this.cards.get(position);
            requestQueue = Volley.newRequestQueue(context);


            alertDialogBuilder
                    .setTitle("Effacer")
                    .setMessage("Voulez-vous vraiment effacer cette carte ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            StringRequest request = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(context, "Carte n°" + card.getCode() + " chez " + card.getMarque() + " effacé", Toast.LENGTH_SHORT).show();
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
                                    parameters.put("mail", card.getMail());
                                    parameters.put("code", card.getCode());
                                    return parameters;
                                }
                            };
                            requestQueue.add(request);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setIcon(R.drawable.ic_error_outline_black_24dp)
                    .show();
            return false;
        }
    }


}
