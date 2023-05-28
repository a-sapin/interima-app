package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Apply extends AppCompatActivity {

    private Offer offer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply);

        Button apply = findViewById(R.id.modifProfil);

        Intent intentp = getIntent();
        String CV = intentp.getStringExtra("lienCV");
        String LM = intentp.getStringExtra("lienLM");
        int id = intentp.getIntExtra("id", 0);

        String nom = intentp.getStringExtra("nom");
        String prenom = intentp.getStringExtra("prenom");
        String nat = intentp.getStringExtra("nat");

        if (CV != null || LM != null) {
            if (nom == null) {
                DatabaseUserData<Apply> dbCo = new DatabaseUserData<>(Apply.this);
                dbCo.setContext(getApplicationContext());
                String SQL = "Select * from interima.chercheuremploi where idUti = '"+getInfoTokenID()+"' ;";
                dbCo.setRequete(SQL);
                dbCo.execute("");
            } else {

                EditText nomE = findViewById(R.id.nomView);
                nomE.setText(nom);

                EditText prenomE = findViewById(R.id.nomServiceView);
                prenomE.setText(prenom);

                Spinner natE = findViewById(R.id.siretView);
                List<String> nationalities;

                try {
                    InputStream inputStream = getAssets().open("nationalites.json");
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();

                    String json = new String(buffer, StandardCharsets.UTF_8);

                    // Parsing du JSON
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("pays");

                    nationalities = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject countryObject  = jsonArray.getJSONObject(i);
                        String nationality = countryObject .getString("nationalite");
                        nationalities.add(nationality);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nationalities);
                    natE.setAdapter(adapter);

                    int defaultPosition = nationalities.indexOf(nat);
                    natE.setSelection(defaultPosition);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                EditText lienCV = findViewById(R.id.button16);
                lienCV.setText(CV);

                EditText lienLM = findViewById(R.id.button17);
                lienLM.setText(LM);
            }
        }

        // verifier que tous les champs sont remplis avant de transmettre le apply
        // mettre un Toast avec le fait que tous les champs ne sont pas remplis par exemple
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                Toast.makeText(getApplicationContext(), "Candidature envoyée", Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(intent);
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

    private String encodeString(String text) {
        try {
            byte[] utf8Bytes = text.getBytes("ISO-8859-1");
            return new String(utf8Bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return text;
        }
    }

    public void onQueryResult(ArrayList<String> userData) {

        EditText nom = findViewById(R.id.nomView);
        nom.setText(encodeString(userData.get(0)));

        EditText prenom = findViewById(R.id.nomServiceView);
        prenom.setText(encodeString(userData.get(1)));

        Spinner nat = findViewById(R.id.siretView);
        List<String> nationalities;

        try {
            InputStream inputStream = getAssets().open("nationalites.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parsing du JSON
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("pays");

            nationalities = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject  = jsonArray.getJSONObject(i);
                String nationality = countryObject .getString("nationalite");
                nationalities.add(nationality);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nationalities);
            nat.setAdapter(adapter);

            int defaultPosition = nationalities.indexOf(encodeString(userData.get(2)));
            nat.setSelection(defaultPosition);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Intent intentp = getIntent();
        String CV = intentp.getStringExtra("lienCV");
        String LM = intentp.getStringExtra("lienLM");

        EditText lienCV = findViewById(R.id.button16);
        lienCV.setText(CV);

        EditText lienLM = findViewById(R.id.button17);
        lienLM.setText(LM);

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