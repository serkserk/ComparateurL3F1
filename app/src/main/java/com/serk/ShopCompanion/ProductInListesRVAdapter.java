package com.serk.ShopCompanion;

import android.content.Context;
import android.content.DialogInterface;
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
 * Cette classe permet d'afficher les produits de liste d'achat dans le recyclerview
 */


public class ProductInListesRVAdapter extends RecyclerView.Adapter<ProductInListesRVAdapter.ProductInListesViewHolder> {

    static String url = ServerURL.PRODUCT_IN_LIST_DEL;
    ArrayList<ProductInListeObject> productInListes;
    Context context;

    public ProductInListesRVAdapter(Context context, ArrayList<ProductInListeObject> listes) {
        this.context = context;
        this.productInListes = listes;
    }

    public void setData(ArrayList<ProductInListeObject> liste) {
        productInListes = liste;
    }

    @Override
    public int getItemCount() {
        return productInListes.size();
    }

    @Override
    public void onBindViewHolder(ProductInListesViewHolder holder, int position) {
        ProductInListeObject productInListe = productInListes.get(position);

        holder.ean.setText(productInListe.getEan());
        holder.marque.setText(productInListe.getMarque());
        holder.nom.setText(productInListe.getNom());
    }

    @Override
    public ProductInListesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productinlist_card,
                parent, false);
        return new ProductInListesViewHolder(view, context, productInListes);
    }

    /**
     * Cet classe initialise chaque cardview avec l'object correspondant
     */
    public static class ProductInListesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CardView cardView;
        TextView ean, nom, marque, mail;
        ArrayList<ProductInListeObject> listes = new ArrayList<>();
        Context context;
        AlertDialog.Builder alertDialogBuilder;
        RequestQueue requestQueue;

        public ProductInListesViewHolder(View view, Context context, ArrayList<ProductInListeObject> listes) {
            super(view);
            this.listes = listes;
            this.context = context;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            cardView = (CardView) view.findViewById(R.id.card_product_in_list);
            ean = (TextView) view.findViewById(R.id.ean);
            marque = (TextView) view.findViewById(R.id.marque);
            nom = (TextView) view.findViewById(R.id.nom);
            mail = (TextView) view.findViewById(R.id.mail);
            alertDialogBuilder = new AlertDialog.Builder(context);
        }

        @Override
        public void onClick(View v) {   //Gère le clique sur une cardview

            int position = getAdapterPosition();
            ProductInListeObject liste = this.listes.get(position);
            /*Intent intent = new Intent(this.context, ProductInListeActivity.class);
            intent.putExtra("mail", liste.getMail());
            intent.putExtra("name", liste.getName());
            this.context.startActivity(intent);*/
            Toast.makeText(context, liste.getMarque() + " " + liste.getNom(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {

            int position = getAdapterPosition();
            final ProductInListeObject productInListe = this.listes.get(position);
            requestQueue = Volley.newRequestQueue(context);


            alertDialogBuilder
                    .setTitle("Effacer")
                    .setMessage("Voulez-vous vraiment effacer ce produit de la liste ?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            StringRequest request = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(context, "Produit " + productInListe.getNom() + " effacé dans " + productInListe.getName() + " effacé", Toast.LENGTH_SHORT).show();
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
                                    parameters.put("mail", productInListe.getMail());
                                    parameters.put("name", productInListe.getName());
                                    parameters.put("ean", productInListe.getEan());
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
