package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MdP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finishing_sign_up);

        Button sign_up = findViewById(R.id.button12);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText mdp1E = findViewById(R.id.editTextTextPassword2);
                String mdp1 = mdp1E.getText().toString();

                EditText mdp2E = findViewById(R.id.editTextTextPassword3);
                String mdp2 = mdp2E.getText().toString();

                // verification que les deux sont identiques
                if (mdp1.equals(mdp2)) {

                    // faire la demande de code de verification

                    Log.d("MdP.java", "debut connexion");
                    DatabaseUpdateCreate<MdP> dbCo = new DatabaseUpdateCreate(MdP.this, true);
                    dbCo.setContext(getApplicationContext());

                    Intent intent = getIntent();
                    String role = intent.getStringExtra("role");

                    String SQL = "";

                    if (role.equals("chercheuremploi")) {
                        SQL = "INSERT INTO interima.utilisateur (mdp, role) values ('"+mdp1+"', 'ChercheurEmploi');";
                    } else if (role.equals("employeur")) {
                        SQL = "INSERT INTO interima.utilisateur (mdp, role) values ('"+mdp1+"', 'Employeur');";
                    } else if (role.equals("agenceinterim")) {
                        SQL = "INSERT INTO interima.utilisateur (mdp, role) values ('"+mdp1+"', 'AgenceInterim');";
                    } else if (role.equals("gestionnaire")) {
                        SQL = "INSERT INTO interima.utilisateur (mdp, role) values ('"+mdp1+"', 'Gestionnaire');";
                    }

                    Log.d("MdP.java", "Requete :" + SQL);

                    dbCo.setRequete(SQL);
                    dbCo.execute("");


                } else {
                    Toast.makeText(getApplicationContext(), "Attention ! Les mots de passe ne sont pas identiques !", Toast.LENGTH_SHORT).show();
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

    public void dataAddQuery(int id) {
        Intent intent = getIntent();
        String role = intent.getStringExtra("role");
        Log.d("MdP.java", "debut query 2");

        if (role.equals("chercheuremploi")) {

            Log.d("MdP.java", "debut connexion pour query 2");
            DatabaseUpdateCreate<MdP> dbCo = new DatabaseUpdateCreate(MdP.this, false);
            dbCo.setContext(getApplicationContext());

            String nom = intent.getStringExtra("nom");
            String prenom = intent.getStringExtra("prenom");
            String email = intent.getStringExtra("email");
            String nat = intent.getStringExtra("nat");
            String tel = intent.getStringExtra("tel");
            String city = intent.getStringExtra("city");
            String dateNai = intent.getStringExtra("dateNai");

            String SQL = "INSERT INTO interima.chercheuremploi (idUti, nom, prenom, email, nationalite, tel, ville) values ('"+id+"', '"+nom+"', '"+prenom+"', '"+email+"', '"+nat+"', '"+tel+"', '"+city+"');";

            Log.d("MdP.java", "Requete :" + SQL);

            dbCo.setRequete(SQL);
            dbCo.execute("");
        }

        Intent home = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(home);
        finish();
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
