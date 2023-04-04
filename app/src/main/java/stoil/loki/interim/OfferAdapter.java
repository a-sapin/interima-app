package stoil.loki.interim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OfferAdapter extends ArrayAdapter<Offer> {
    private ArrayList<Offer> annonces;

    public OfferAdapter(Context context, ArrayList<Offer> annonces) {
        super(context, 0, annonces);
        this.annonces = annonces;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.annonce_item, parent, false);
        }

        Offer annonce = annonces.get(position);

        TextView titleTextView = convertView.findViewById(R.id.title);
        TextView descriptionTextView = convertView.findViewById(R.id.description);

        titleTextView.setText(annonce.getTitle());
        descriptionTextView.setText(annonce.getUrl());

        return convertView;
    }
}
