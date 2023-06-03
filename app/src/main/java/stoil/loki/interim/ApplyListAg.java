package stoil.loki.interim;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ApplyListAg extends AppCompatActivity {

    private int apply_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_apply_list_ag);

        DatabaseUserData<ApplyListAg> dbCo = new DatabaseUserData<>(ApplyListAg.this);
        dbCo.setContext(getApplicationContext());
        String SQL = "Select * from interima.chercheuremploi where idUti = '"+getInfoTokenID()+"' ;";
        dbCo.setRequete(SQL);
        dbCo.execute("");

        Intent intentp = getIntent();

        apply_id = intentp.getIntExtra("id", -1);

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

        Button accepter = (Button) findViewById(R.id.accepter);
        accepter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                ApplyListAg.this.SQLSubmitAccepter();
                Toast.makeText(ApplyListAg.this, "Vous avez accepté cette candidature.", Toast.LENGTH_SHORT).show();
            }
        });

        Button refuser = (Button) findViewById(R.id.refuser);
        refuser.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                ApplyListAg.this.SQLSubmitRefuser();
                Toast.makeText(ApplyListAg.this, "Vous avez refusé cette candidature.", Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Gérez la redirection ici
                switch (item.getItemId()) {
                    case R.id.home:
                        // Redirigez vers l'écran d'accueil

                        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intenth);
                        return true;

                    case R.id.favoris:
                        Intent intentf = new Intent(getApplicationContext(), Bookmarks.class);
                        startActivity(intentf);
                        return true;
                    case R.id.recherche:
                        Intent intents = new Intent(getApplicationContext(), RecherchePage.class);
                        startActivity(intents);
                        return true;
                    case R.id.notifs:

                        Intent intentn = new Intent(getApplicationContext(), Notifications.class);
                        startActivity(intentn);
                        return true;

                    case R.id.profil:
                        // si connecter donner la page du profil
                        // sinon on demande la co ou inscription

                        if(true) {
                            Intent intentp = new Intent(getApplicationContext(), ProfilDisplay.class);
                            startActivity(intentp);
                        } else {
                            Intent intentp = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(intentp);
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void SQLSubmitAccepter() {
        Log.d("ApplyListAg.java", "Validating candidature");
        DatabaseUpdateCreate<ApplyListAg> dbCo = new DatabaseUpdateCreate<>(ApplyListAg.this, 1);
        dbCo.setContext(getApplicationContext());
        String SQL;
        SQL = "UPDATE candidature SET statut='ACCEPTEE' WHERE candidature.id='"+apply_id+"';";
        Log.d("ApplyListAg.java", "Requete :" + SQL);
        dbCo.setRequete(SQL);
        dbCo.execute("");
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void SQLSubmitRefuser() {
        Log.d("ApplyListAg.java", "Refusing candidature");
        DatabaseUpdateCreate<ApplyListAg> dbCo = new DatabaseUpdateCreate<>(ApplyListAg.this, 1);
        dbCo.setContext(getApplicationContext());
        String SQL;
        SQL = "UPDATE candidature SET statut='REFUSEE' WHERE candidature.id='"+apply_id+"';";
        Log.d("ApplyListAg.java", "Requete :" + SQL);
        dbCo.setRequete(SQL);
        dbCo.execute("");
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