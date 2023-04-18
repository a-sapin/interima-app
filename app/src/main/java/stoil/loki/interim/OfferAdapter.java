package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    private List<Offer> annonces;

    public OfferAdapter(ArrayList<Offer> annonces) {
        this.annonces = annonces;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer offer = annonces.get(position);
        holder.titleTextView.setText(offer.getTitle());
        holder.descriptionTextView.setText(offer.getUrl());
        holder.applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FullOffer.class);
                intent.putExtra("offer", offer);
                view.getContext().startActivity(intent);
            }
        });

        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ajout/retirer bookmark de la bdd + changement de l image du bouton

                Drawable imageDrawable = holder.bookmark.getDrawable();

                Resources resources = holder.itemView.getContext().getResources();
                Drawable marque = ResourcesCompat.getDrawable(resources, R.drawable.marque_page, null);
                Drawable marque_vide = ResourcesCompat.getDrawable(resources, R.drawable.marque_page_vide, null);

                if(imageDrawable.getConstantState().equals(marque.getConstantState())) {
                    holder.bookmark.setImageDrawable(marque_vide);
                    // retirer le bookmark de la bdd

                } else {
                    holder.bookmark.setImageDrawable(marque);
                    // ajouter le bookmark dans la bdd

                }

            }
        });

        holder.translation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // demander la langue pour la traduction etc
            }
        });

        holder.dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // autres options
            }
        });

    }

    @Override
    public int getItemCount() {
        return annonces.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public Button applyButton;
        public ImageButton bookmark;
        public ImageButton translation;
        public ImageButton dot;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.title);
            descriptionTextView = view.findViewById(R.id.description);
            applyButton = view.findViewById(R.id.apply_button);
            bookmark = view.findViewById(R.id.bookmark);
            translation = view.findViewById(R.id.language_button);
            dot = view.findViewById(R.id.dot);
        }
    }
}
