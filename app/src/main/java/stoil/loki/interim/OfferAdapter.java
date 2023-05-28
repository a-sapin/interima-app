package stoil.loki.interim;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
    private List<Offer> annonces;
    private static final int PERMISSION_REQUEST_CODE = 1993;

    public OfferAdapter(ArrayList<Offer> annonces) {
        this.annonces = annonces;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        return new ViewHolder(view);
    }

    private String encodeString(String text) {
        try {
            byte[] utf8Bytes = text.getBytes("ISO-8859-1");
            return new String(utf8Bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return text;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer offer = annonces.get(position);
        holder.titleTextView.setText(encodeString(offer.getTitle()));
        holder.descriptionTextView.setText(encodeString(offer.getUrl()));

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

        holder.dot.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), holder.dot);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setForceShowIcon(true);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()) {
                            case R.id.share_sms:
                                if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                    String TITRE = offer.getTitle();
                                    String DUREE = "du "+offer.getDateDebut()+" au "+offer.getDateFin();
                                    String REMUNERATION = Float.toString(offer.getSalaire());
                                    String SOURCE = offer.getUrl();
                                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                    sendIntent.setData(Uri.parse("sms:"));
                                    String smsbody = "Partagé via Interima: "+encodeString(TITRE+", "+DUREE+", " +
                                            REMUNERATION)+"€/hr.";
                                    if (!SOURCE.isEmpty()){
                                        smsbody+="("+encodeString(SOURCE)+")";
                                    }
                                    sendIntent.putExtra("sms_body", smsbody);
                                    view.getContext().startActivity(sendIntent);
                                } else {
                                    ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{android.Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
                                }
                                break;
                            default:
                                Toast.makeText(view.getContext(), "Partage via " + menuItem.getTitle() + " à venir", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
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
            dot = view.findViewById(R.id.dot);
        }
    }
}
