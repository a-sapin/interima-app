package stoil.loki.interim;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ApplyReuseAdapter extends RecyclerView.Adapter<ApplyReuseAdapter.ViewHolder> {
    private List<CandidatureData> list_apply;

    public ApplyReuseAdapter(ArrayList<CandidatureData> list_apply) {
        this.list_apply = list_apply;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apply_reuse_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CandidatureData apply = list_apply.get(position);
        holder.titleTextView.setText(apply.getTitle());

        holder.select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FullOffer.class);
                // selectionner l annonce et envoyé la candidature
                view.getContext().startActivity(intent);
            }
        });

        holder.see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // voir la candidature
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
        return list_apply.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView source;
        public Button select_button;
        public ImageButton see;
        public ImageButton dot;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.title);
            source = view.findViewById(R.id.description);
            select_button = view.findViewById(R.id.select_button);
            see = view.findViewById(R.id.see);
            dot = view.findViewById(R.id.dot);
        }
    }
}
