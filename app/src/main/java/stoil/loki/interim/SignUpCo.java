package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SignUpCo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_co);

        Button sign_in = findViewById(R.id.button11);
        Button next = findViewById(R.id.button10);

        Intent intentp = getIntent();
        String role = intentp.getStringExtra("role");

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignIn.class);
                view.getContext().startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GridAbonnement.class);

                EditText email1E = findViewById(R.id.editTextTextEmailAddress2);
                String email1 = email1E.getText().toString();

                EditText nomEntrepriseE = findViewById(R.id.editTextTextPersonName);
                String nomEntreprise = nomEntrepriseE.getText().toString();

                EditText adresseE = findViewById(R.id.editTextTextPersonName2);
                String adresse = adresseE.getText().toString();

                EditText NomDepartementE = findViewById(R.id.editTextTextPostalAddress);
                String NomDepartement = NomDepartementE.getText().toString();

                EditText Nom1E = findViewById(R.id.editTextTextPersonName3);
                String Nom1 = Nom1E.getText().toString();

                EditText telephone1E = findViewById(R.id.editTextPhone2);
                String telephone1 = telephone1E.getText().toString();

                EditText siretE = findViewById(R.id.editTextNumber);
                String siret = siretE.getText().toString();

                EditText sousServiceE = findViewById(R.id.editTextTextPersonName5);
                String sousService = sousServiceE.getText().toString();

                EditText nom2E = findViewById(R.id.editTextTextPersonName6);
                String nom2 = nom2E.getText().toString();

                EditText email2E = findViewById(R.id.editTextTextPersonName7);
                String email2 = email2E.getText().toString();

                EditText tel2E = findViewById(R.id.editTextTextPersonName8);
                String tel2 = tel2E.getText().toString();

                if (nomEntreprise.isEmpty() || sousService.isEmpty() || NomDepartement.isEmpty() || siret.isEmpty() || Nom1.isEmpty() || email1.isEmpty() || telephone1.isEmpty() || adresse.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Les champs obligatoires ne sont pas tous remplis", Toast.LENGTH_SHORT).show();
                } else {

                    intent.putExtra("role", role);
                    intent.putExtra("email1", email1);
                    intent.putExtra("departement", NomDepartement);
                    intent.putExtra("adresse", adresse);
                    intent.putExtra("nomEntreprise", nomEntreprise);
                    intent.putExtra("nom1", Nom1);
                    intent.putExtra("telephone1", telephone1);
                    intent.putExtra("siret", siret);
                    intent.putExtra("sousService", sousService);
                    intent.putExtra("nom2", nom2);
                    intent.putExtra("email2", email2);
                    intent.putExtra("tel2", tel2);

                    view.getContext().startActivity(intent);
                }

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
}