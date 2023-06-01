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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SavedSearchAdapter extends RecyclerView.Adapter<SavedSearchAdapter.ViewHolder> {

    private List<Search> list_search;

    public SavedSearchAdapter(ArrayList<Search> list_search) {
        this.list_search = list_search;
    }

    @NonNull
    @Override
    public SavedSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_search_item, parent, false);
        return new SavedSearchAdapter.ViewHolder(view);
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
    public void onBindViewHolder(@NonNull SavedSearchAdapter.ViewHolder holder, int position) {
        Search search = list_search.get(position);
        holder.search_title.setText(encodeString(search.getSearchstr()));
        holder.search_date.setText(convertDate(holder.search_date.getText().toString()+search.getDatesearch()));
    }

    @Override
    public int getItemCount() {
        return list_search.size();
    }

    public String convertDate(String dateOrigine) {
        String resConvertedDate = "";
        String inputFormat = "yyyy-MM-dd";
        String desiredOutputDate = null;

        SimpleDateFormat outputFormat = new SimpleDateFormat(inputFormat);
        SimpleDateFormat inputFormatObj = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = outputFormat.parse(dateOrigine);
            desiredOutputDate = inputFormatObj.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        resConvertedDate = desiredOutputDate;
        return resConvertedDate;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView search_title;
        public TextView search_date;

        public ViewHolder(View view) {
            super(view);
            search_title = view.findViewById(R.id.search_title);
            search_date = view.findViewById(R.id.search_date);
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