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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

//import org.threeten.bp.LocalDate;
//import org.threeten.bp.format.DateTimeFormatter;
//import org.threeten.bp.format.DateTimeParseException;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        //Button connect = findViewById(R.id.button11);
        Button next = findViewById(R.id.button10);
        Spinner nat = findViewById(R.id.spinner);

        Intent intent = getIntent();

        String role = intent.getStringExtra("role");

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

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {

                // recuperation de toutes les informations entrées

                EditText emailE = findViewById(R.id.editTextTextEmailAddress2);
                String email = emailE.getText().toString();

                EditText nomE = findViewById(R.id.editTextTextPersonName);
                String nom = nomE.getText().toString();

                EditText prenomE = findViewById(R.id.editTextTextPersonName2);
                String prenom = prenomE.getText().toString();

                Spinner natE = findViewById(R.id.spinner);
                String nat = natE.getSelectedItem().toString();

                EditText dateNai = findViewById(R.id.editTextDate);
                String dateString = dateNai.getText().toString();

                EditText telephoneE = findViewById(R.id.editTextPhone);
                String tel = telephoneE.getText().toString();

                EditText cityE = findViewById(R.id.editTextTextPersonName4);
                String city = cityE.getText().toString();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                try {
                    LocalDate date = LocalDate.parse(dateString, formatter);

                    if (date != null && !email.equals("") && !nom.equals("") && !prenom.equals("") && !nat.equals("") && !tel.equals("") && !city.equals("")){

                        //si tous les champs sont remplis (un chercheur d emploi doit tout renseigner)
                        Intent intent = new Intent(view.getContext(), MdP.class);

                        intent.putExtra("role", "chercheuremploi");

                        intent.putExtra("dateNai", date);
                        intent.putExtra("email", email);
                        intent.putExtra("nom", nom);
                        intent.putExtra("prenom", prenom);
                        intent.putExtra("nat", nat);
                        intent.putExtra("tel", tel);
                        intent.putExtra("city", city);

                        view.getContext().startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
                    }

                } catch (DateTimeParseException e) {
                    // Gestion de l'exception en cas de format de date incorrect
                    Toast.makeText(getApplicationContext(), "Format de date incorrect. Veuillez utiliser le format dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
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
                        if(((SignUp)menu.getContext()).getInfoTokenID() != null) {
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
                        if(((SignUp)menu.getContext()).getInfoTokenID() != null) {
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

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.profil).setChecked(true);
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
