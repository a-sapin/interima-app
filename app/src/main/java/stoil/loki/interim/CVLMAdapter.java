package stoil.loki.interim;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CVLMAdapter extends RecyclerView.Adapter<CVLMAdapter.ViewHolder> {

    private List<CandidatureData> list_apply;

    public CVLMAdapter(ArrayList<CandidatureData> list_apply) {
        this.list_apply = list_apply;
    }

    @NonNull
    @Override
    public CVLMAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cvlm_item, parent, false);
        return new CVLMAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CVLMAdapter.ViewHolder holder, int position) {
        CandidatureData apply = list_apply.get(position);
        holder.titleTextView.setText(apply.getOffertitle());

        holder.cv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.example.com";
                Intent icv = new Intent(Intent.ACTION_VIEW);
                icv.setData(Uri.parse(url));
                view.getContext().startActivity(icv);
            }
        });

        holder.lm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.example.com";
                Intent ilm = new Intent(Intent.ACTION_VIEW);
                ilm.setData(Uri.parse(url));
                view.getContext().startActivity(ilm);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_apply.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView status;
        public Button cv_button;
        public Button lm_button;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.title);
            status = view.findViewById(R.id.status);
            cv_button = view.findViewById(R.id.cv_button);
            lm_button = view.findViewById(R.id.lm_button);
        }
    }

}
