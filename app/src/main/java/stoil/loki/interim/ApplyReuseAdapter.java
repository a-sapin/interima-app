package stoil.loki.interim;

import static androidx.core.content.ContextCompat.startActivity;

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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ApplyReuseAdapter extends RecyclerView.Adapter<ApplyReuseAdapter.ViewHolder> {
    private List<CandidatureData> list_apply;
    private static final int PERMISSION_REQUEST_CODE = 1993;

    public ApplyReuseAdapter(ArrayList<CandidatureData> list_apply) {
        this.list_apply = list_apply;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apply_reuse_item, parent, false);
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
    public void onBindViewHolder(@NonNull ApplyReuseAdapter.ViewHolder holder, int position) {
        CandidatureData apply = list_apply.get(position);
        holder.titleTextView.setText(encodeString(apply.getOffertitle()));

        holder.select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Apply.class);
                intent.putExtra("lienCV", encodeString(list_apply.get(position).getLienCV()));
                intent.putExtra("lienLM", encodeString(list_apply.get(position).getLienLM()));
                intent.putExtra("id", list_apply.get(position).getId());
                // recuperer les donnees de la candidature et preremplir une candidature
                view.getContext().startActivity(intent);
            }
        });

        holder.see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // voir la candidature
                Intent intent = new Intent(view.getContext(), ApplyReuse.class);
                intent.putExtra("lienCV", encodeString(list_apply.get(position).getLienCV()));
                intent.putExtra("lienLM", encodeString(list_apply.get(position).getLienLM()));
                intent.putExtra("titre", encodeString(list_apply.get(position).getOffertitle()));
                intent.putExtra("id", list_apply.get(position).getId());
                view.getContext().startActivity(intent);
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
                                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                    sendIntent.setData(Uri.parse("sms:"));
                                    String smsbody = "Partagé via Interima: TITRE, DATE, " +
                                            "LIEN CV, LIEN LM";
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
        return list_apply.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public Button select_button;
        public ImageButton see;
        public ImageButton dot;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.title);
            select_button = view.findViewById(R.id.select_button);
            see = view.findViewById(R.id.see);
            dot = view.findViewById(R.id.dot);
        }
    }
}
