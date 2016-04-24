package com.serk.ShopCompanion;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe permet d'afficher les produits dans le recyclerview
 */

public class ScannedProductRVAdapter extends RecyclerView.Adapter<ScannedProductRVAdapter.MyViewHolder> {

    ArrayList<ScannedProductObject> productList;
    Context context;

    public ScannedProductRVAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<ScannedProductObject> product) {
        productList = product;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ScannedProductObject product = productList.get(position);

        holder.ean.setText(product.getEan());
        holder.marque.setText(product.getMarque());
        holder.nom.setText(product.getNom());
        holder.desc.setText(product.getDesc());
        holder.price.setText(product.getPrice());
        holder.shop.setText(product.getShop());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scanned_product_list_row,
                parent, false);
        return new MyViewHolder(view, context, productList);
    }

    /**
     * Cet classe initialise chaque cardview avec l'object correspondant
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView ean, marque, nom, desc, shop, price;
        Context context;
        ArrayList<ScannedProductObject> productLists = new ArrayList<>();

        public MyViewHolder(View view, Context context, ArrayList<ScannedProductObject> productLists) {
            super(view);
            view.setOnClickListener(this);
            this.productLists = productLists;
            this.context = context;
            cardView = (CardView) view.findViewById(R.id.card_product);
            ean = (TextView) view.findViewById(R.id.ean);
            marque = (TextView) view.findViewById(R.id.marque);
            nom = (TextView) view.findViewById(R.id.nom);
            desc = (TextView) view.findViewById(R.id.desc);
            shop = (TextView) view.findViewById(R.id.shop);
            price = (TextView) view.findViewById(R.id.price);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ScannedProductObject product = this.productLists.get(position);
            Intent intentcode = new Intent(this.context.getApplicationContext(), ProductCommentResultActivity.class);
            intentcode.putExtra("ean", product.getEan());
            this.context.startActivity(intentcode);
        }
    }
}
