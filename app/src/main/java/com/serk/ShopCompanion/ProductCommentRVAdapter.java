package com.serk.ShopCompanion;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Azap Serkan, ie04114
 * Cette classe permet d'afficher les objets commentaires (ProductCommentObject) dans le recyclerview
 */

public class ProductCommentRVAdapter extends RecyclerView.Adapter<ProductCommentRVAdapter.CardViewHolder> {

    ArrayList<ProductCommentObject> comments;
    Context context;

    public ProductCommentRVAdapter(Context context, ArrayList<ProductCommentObject> comments) {
        this.context = context;
        this.comments = comments;
    }

    public void setData(ArrayList<ProductCommentObject> comments) {
        this.comments = comments;
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        ProductCommentObject comment = comments.get(position);

        holder.comment.setText(comment.getComment());
        holder.mail.setText(comment.getMail());
        holder.ean.setText(comment.getEan());

    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productcomment_row,
                parent, false);
        return new CardViewHolder(view, context, comments);
    }


    /**
     * Cette classe initialise chaque cardview avec l'object correspondant
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView comment, mail, ean;
        ArrayList<ProductCommentObject> cards = new ArrayList<>();
        Context context;
        AlertDialog.Builder alertDialogBuilder;

        public CardViewHolder(View view, Context context, ArrayList<ProductCommentObject> cards) {
            super(view);
            this.cards = cards;
            this.context = context;
            cardView = (CardView) view.findViewById(R.id.card_listes);
            comment = (TextView) view.findViewById(R.id.comment);
            mail = (TextView) view.findViewById(R.id.mail);
            ean = (TextView) view.findViewById(R.id.ean);
            alertDialogBuilder = new AlertDialog.Builder(context);
        }

    }


}
