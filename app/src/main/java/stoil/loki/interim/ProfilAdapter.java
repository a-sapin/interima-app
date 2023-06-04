package stoil.loki.interim;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfilAdapter extends RecyclerView.Adapter<ProfilAdapter.ViewHolder> {
    private static List<ItemProfil> itemProfil;

    public ProfilAdapter(ArrayList<ItemProfil> itemProfil) {
        this.itemProfil = itemProfil;
    }

    @NonNull
    @Override
    public ProfilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profil, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilAdapter.ViewHolder holder, int position) {
        ItemProfil itemProfilTp = itemProfil.get(position);
        holder.option.setText(itemProfilTp.getTitle());
        holder.icon.setImageDrawable(itemProfilTp.getIcon());
        ItemProfil itemProfil = this.itemProfil.get(position);
        holder.bind(itemProfil);

    }

    @Override
    public int getItemCount() {
        return itemProfil.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView option;

        public ImageView icon;

        public ViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.imageView);
            option = view.findViewById(R.id.textView47);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ouvrir une nouvelle activité ou fragment pour afficher plus d'informations
                    Context context = v.getContext();
                    Intent intent = new Intent(context, itemProfil.get(getAdapterPosition()).getIntentClass());
                    intent.putExtra("item_id", itemProfil.get(getAdapterPosition()).getId());
                    UserData currentud = itemProfil.get(getAdapterPosition()).getUd();
                    if(currentud != null) {
                        if(currentud.getNom() != null) {
                            intent.putExtra("udNom", currentud.getNom());
                        }
                        if(currentud.getPrenom() != null) {
                            intent.putExtra("udPrenom", currentud.getPrenom());
                        }
                        if(currentud.getEmail() != null) {
                            intent.putExtra("udEmail", currentud.getEmail());
                        }
                        if(currentud.getNat() != null) {
                            intent.putExtra("udNat", currentud.getNat());
                        }
                        if(currentud.getTel() != null) {
                            intent.putExtra("udTel", currentud.getTel());
                        }
                        if(currentud.getNomServDept() != null) {
                            intent.putExtra("udNomServDept", currentud.getNomServDept());
                        }
                        if(currentud.getNomSousSD() != null) {
                            intent.putExtra("udNomSousSD", currentud.getNomSousSD());
                        }
                        if(currentud.getSiret() != null) {
                            intent.putExtra("udSiret", currentud.getSiret());
                        }
                        if(currentud.getNom2() != null) {
                            intent.putExtra("udNom2", currentud.getNom2());
                        }
                        if(currentud.getEmail2() != null) {
                            intent.putExtra("udEmail2", currentud.getEmail2());
                        }
                        if(currentud.getTel2() != null) {
                            intent.putExtra("udTel2", currentud.getTel2());
                        }
                        if(currentud.getAdresse() != null) {
                            intent.putExtra("udAdresse", currentud.getAdresse());
                        }
                        if(currentud.getNomEntreprise() != null) {
                            intent.putExtra("udNomEntreprise", currentud.getNomEntreprise());
                        }
                    }
                    context.startActivity(intent);
                }
            });

        }

        public void bind(ItemProfil itemProfil) {
            option.setText(itemProfil.getTitle());
            icon.setImageDrawable(itemProfil.getIcon());
        }

    }

}
