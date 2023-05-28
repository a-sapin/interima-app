package stoil.loki.interim;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ApplyListAdapter extends RecyclerView.Adapter<ApplyListAdapter.ViewHolder> {

    private List<CandidatureData> list_apply;
    private static final int PERMISSION_REQUEST_CODE = 1993;

    public ApplyListAdapter(ArrayList<CandidatureData> list_apply) {
        this.list_apply = list_apply;
    }

    @NonNull
    @Override
    public ApplyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apply_list_item, parent, false);
        return new ApplyListAdapter.ViewHolder(view);
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
    public void onBindViewHolder(@NonNull ApplyListAdapter.ViewHolder holder, int position) {
        CandidatureData apply = list_apply.get(position);
        holder.titleTextView.setText(encodeString(apply.getOffertitle()));
        holder.status.setText(encodeString(apply.getStatut()));

        holder.see_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.getInfoTokenID(view) != "Chercheur d'emploi" && holder.getInfoTokenID(view) != null) {
                    Intent intent = new Intent(view.getContext(), ApplyListAg.class);
                    view.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(view.getContext(), ApplyList.class);
                    view.getContext().startActivity(intent);
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
        public TextView status;
        public Button see_button;
        public ImageButton dot;

        public ViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.title);
            status = view.findViewById(R.id.status);
            see_button = view.findViewById(R.id.see_button);
            dot = view.findViewById(R.id.dot);
        }

        public ArrayList<String> getInfoToken(View view) {
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("User DATA", Context.MODE_PRIVATE);
            ArrayList<String> value = new ArrayList<>();
            value.add(sharedPreferences.getString("role", null));
            value.add(sharedPreferences.getString("id", null));

            return value;
        }

        public String getInfoTokenID(View view) {
            ArrayList<String> info = getInfoToken(view);
            return info.get(1);
        }

        public String getInfoTokenRole(View view) {
            ArrayList<String> info = getInfoToken(view);
            return info.get(0);
        }
    }
}