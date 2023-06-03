package stoil.loki.interim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ApplyList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_apply_list);

        DatabaseUserData<ApplyList> dbCo = new DatabaseUserData<>(ApplyList.this);
        dbCo.setContext(getApplicationContext());
        String SQL = "Select * from interima.chercheuremploi where idUti = '"+getInfoTokenID()+"' ;";
        dbCo.setRequete(SQL);
        dbCo.execute("");

        Intent intentp = getIntent();

        TextView titre = findViewById(R.id.textView48);
        titre.setText(intentp.getStringExtra("titre"));

        Button lienCV = findViewById(R.id.button16);
        lienCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = intentp.getStringExtra("lienCV");
                Intent icv = new Intent(Intent.ACTION_VIEW);
                icv.setData(Uri.parse(url));
                view.getContext().startActivity(icv);
            }
        });

        Button lienLM = findViewById(R.id.button17);
        lienLM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = intentp.getStringExtra("lienLM");
                Intent icv = new Intent(Intent.ACTION_VIEW);
                icv.setData(Uri.parse(url));
                view.getContext().startActivity(icv);
            }
        });

        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intenth);
                        return true;
                    case R.id.favoris:
                        if(((ApplyList)menu.getContext()).getInfoTokenID() != null) {
                            Intent intentf = new Intent(getApplicationContext(), Bookmarks.class);
                            startActivity(intentf);
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Connectez-vous pour " +
                                    "accéder à cette fonctionnalité.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.recherche:
                        Intent intents = new Intent(getApplicationContext(), RecherchePage.class);
                        startActivity(intents);
                        return true;
                    case R.id.notifs:
                        if(((ApplyList)menu.getContext()).getInfoTokenID() != null) {
                            Intent intentn = new Intent(getApplicationContext(), Notifications.class);
                            startActivity(intentn);
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Connectez-vous pour " +
                                    "accéder à cette fonctionnalité.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.profil:
                        Intent intentp = new Intent(getApplicationContext(), ProfilDisplay.class);
                        startActivity(intentp);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

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

    public void onQueryResult(ArrayList<String> userData, ArrayList<String> candidatureData) {

        TextView nom = findViewById(R.id.nomView);
        nom.setText(encodeString(userData.get(0)));

        TextView prenom = findViewById(R.id.nomServiceView);
        prenom.setText(encodeString(userData.get(1)));

        TextView nat = findViewById(R.id.siretView);
        nat.setText(encodeString(userData.get(2)));

        ListView utiliseDans = findViewById(R.id.recyclerView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, candidatureData);
        utiliseDans.setAdapter(adapter);

    }

    public ArrayList<String> getInfoToken() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("User DATA", Context.MODE_PRIVATE);
        ArrayList<String> value = new ArrayList<>();
        value.add(sharedPreferences.getString("role", null));
        value.add(sharedPreferences.getString("id", null));

        return value;
    }

    public String getInfoTokenID() {
        ArrayList<String> info = getInfoToken();
        return info.get(1);
    }

    public String getInfoTokenRole() {
        ArrayList<String> info = getInfoToken();
        return info.get(0);
    }
}