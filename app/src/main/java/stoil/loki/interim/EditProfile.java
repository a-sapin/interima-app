package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

//import org.threeten.bp.LocalDate;
//import org.threeten.bp.format.DateTimeFormatter;
//import org.threeten.bp.format.DateTimeParseException;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        //Button connect = findViewById(R.id.button11);
        Button next = findViewById(R.id.button10);
        Spinner nat = findViewById(R.id.spinner);

        Intent intent = getIntent();

        EditText emailE = findViewById(R.id.editTextTextEmailAddress2);
        emailE.setText(intent.getStringExtra("udEmail"));

        EditText nomE = findViewById(R.id.editTextTextPersonName);
        nomE.setText(intent.getStringExtra("udNom"));

        EditText prenomE = findViewById(R.id.editTextTextPersonName2);
        prenomE.setText(intent.getStringExtra("udPrenom"));

        Spinner natE = findViewById(R.id.spinner);

        EditText telephoneE = findViewById(R.id.editTextPhone);
        telephoneE.setText(intent.getStringExtra("udTel"));

        EditText cityE = findViewById(R.id.editTextTextPersonName4);

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

            List<String> nationalities = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject  = jsonArray.getJSONObject(i);
                String nationality = countryObject .getString("nationalite");
                nationalities.add(nationality);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nationalities);
            nat.setAdapter(adapter);
            natE.setSelection(selectItemFromString(intent.getStringExtra("udNat"), nationalities));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {

                String email = emailE.getText().toString();
                String nom = nomE.getText().toString();
                String prenom = prenomE.getText().toString();
                String nat = natE.getSelectedItem().toString();
                String tel = telephoneE.getText().toString();
                String city = cityE.getText().toString();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                try {

                    if (!email.equals("") && !nom.equals("") && !prenom.equals("") && !nat.equals("") && !tel.equals("") && !city.equals("")){

                        //si tous les champs sont remplis (un chercheur d emploi doit tout renseigner)
                        Intent intent = new Intent(view.getContext(), MdP.class);

                        intent.putExtra("role", "chercheuremploi");

                        //intent.putExtra("dateNai", date);
                        intent.putExtra("udEmail", email);
                        intent.putExtra("udNom", nom);
                        intent.putExtra("udPrenom", prenom);
                        intent.putExtra("udNat", nat);
                        intent.putExtra("udTel", tel);
                        //intent.putExtra("udCity", city);

                        //view.getContext().startActivity(intent);

                        DatabaseUpdateCreate<EditProfile> dbCo = new DatabaseUpdateCreate(EditProfile.this, 0);
                        dbCo.setContext(getApplicationContext());
                        String USERid = getInfoTokenID();
                        String SQL = "UPDATE interima.chercheuremploi SET nom = '"+nom+"', prenom = '"+prenom+"', email = '"+email+"', nationalite = '"+nat+"', tel = '"+tel+"', ville = '"+city+"' WHERE idUti = "+USERid+";";
                        Log.d("EditProfile.java", "Requete :" + SQL);
                        dbCo.setRequete(SQL);
                        dbCo.execute("");

                        Toast.makeText(getApplicationContext(), "Vos informations ont bien été modifiées!", Toast.LENGTH_SHORT).show();
                        Thread.sleep(300);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                    }

                } catch (DateTimeParseException e) {
                    // Gestion de l'exception en cas de format de date incorrect
                    Toast.makeText(getApplicationContext(), "Format de date incorrect. Veuillez utiliser le format dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

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
                        if(((EditProfile)menu.getContext()).getInfoTokenID() != null) {
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
                        if(((EditProfile)menu.getContext()).getInfoTokenID() != null) {
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

    public void onQueryResult() {
        Toast.makeText(getApplicationContext(), "Vos informations ont bien été modifiées!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.profil).setChecked(true);
    }

    public int selectItemFromString(String string_item, List<String> nats) {
        for(int i = 0; i < nats.size(); i++) {
            if(nats.get(i).equals(string_item)) {
                return i;
            }
        }
        return 0;
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

    public void clearToken() {
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("User DATA", Context.MODE_PRIVATE).edit();
        editor.remove("role");
        editor.remove("id");
        editor.apply();
        return;
    }
}
