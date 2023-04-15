package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class OfferAdapter extends ArrayAdapter<Offer> implements Serializable {
    private ArrayList<Offer> annonces;

    public OfferAdapter(Context context, ArrayList<Offer> annonces) {
        super(context, 0, annonces);
        this.annonces = annonces;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.offer_item, parent, false);
        }

        Offer offer = annonces.get(position);

        TextView titleTextView = convertView.findViewById(R.id.title);
        TextView descriptionTextView = convertView.findViewById(R.id.description);

        titleTextView.setText(offer.getTitle());
        descriptionTextView.setText(offer.getUrl());

        convertView.findViewById(R.id.apply_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Action à exécuter lorsque le bouton est cliqué
                Intent intent = new Intent(getContext(), FullOffer.class);
                intent.putExtra("offer", (Serializable) offer);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
