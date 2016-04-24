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
 * Cette classe permet d'afficher les listes d'achat dans le recyclerview
 */


public class ListesRVAdapter extends RecyclerView.Adapter<ListesRVAdapter.ListesViewHolder> {

    static String url = ServerURL.LIST_DEL;
    ArrayList<ListeObject> listesListe;
    Context context;

    public ListesRVAdapter(Context context, ArrayList<ListeObject> listes) {
        this.context = context;
        this.listesListe = listes;
    }

    public void setData(ArrayList<ListeObject> liste) {
        listesListe = liste;
    }

    @Override
    public int getItemCount() {
        return listesListe.size();
    }

    @Override
    public void onBindViewHolder(ListesViewHolder holder, int position) {
        ListeObject liste = listesListe.get(position);

        holder.name.setText(liste.getName());
        holder.description.setText(liste.getDescription());
        holder.total.setText(liste.getMail());
    }

    @Override
    public ListesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listes_card,
                parent, false);
        return new ListesViewHolder(view, context, listesListe);
    }

    /**
     * Cette classe initialise chaque cardview avec l'object correspondant
     */
    public static class ListesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CardView cardView;
        TextView name, description, total;
        ArrayList<ListeObject> listes = new ArrayList<>();
        Context context;
        AlertDialog.Builder alertDialogBuilder;
        RequestQueue requestQueue;

        public ListesViewHolder(View view, Context context, ArrayList<ListeObject> listes) {
            super(view);
            this.listes = listes;
            this.context = context;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            cardView = (CardView) view.findViewById(R.id.card_listes);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            total = (TextView) view.findViewById(R.id.total);
            alertDialogBuilder = new AlertDialog.Builder(context);
        }

        @Override
        public void onClick(View v) { //Gère le clique sur une cardview

            int position = getAdapterPosition();
            ListeObject liste = this.listes.get(position);
            Intent intent = new Intent(this.context, ProductInListeActivity.class);
            intent.putExtra("name", liste.getName());
            this.context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {

            int position = getAdapterPosition();
            final ListeObject liste = this.listes.get(position);
            requestQueue = Volley.newRequestQueue(context);


            alertDialogBuilder
                    .setTitle("Effacer")
                    .setMessage("Voulez-vous vraiment effacer cette liste ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            StringRequest request = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(context, "Liste " + liste.getName() + " effacé", Toast.LENGTH_SHORT).show();
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
                                    parameters.put("mail", liste.getMail());
                                    parameters.put("name", liste.getName());
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
