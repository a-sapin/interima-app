package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
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

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("item_id", itemProfil.get(getAdapterPosition()).getId());
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
