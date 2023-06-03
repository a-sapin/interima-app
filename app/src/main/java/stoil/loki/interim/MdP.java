package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MdP extends AppCompatActivity {

    private int userid_for_subscription;

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
                    DatabaseUpdateCreate<MdP> dbCo = new DatabaseUpdateCreate(MdP.this, 2);
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
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intenth);
                        return true;
                    case R.id.favoris:
                        if(((MdP)menu.getContext()).getInfoTokenID() != null) {
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
                        if(((MdP)menu.getContext()).getInfoTokenID() != null) {
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

    public void dataAddQuery(int id) {
        Intent intent = getIntent();
        String role = intent.getStringExtra("role");
        Log.d("MdP.java", "debut query 2");

        if (role.equals("chercheuremploi")) {

            Log.d("MdP.java", "debut connexion pour query 2");
            DatabaseUpdateCreate<MdP> dbCo = new DatabaseUpdateCreate(MdP.this, 0);
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

            Intent home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(home);
            finish();

        } else if (role.equals("employeur")) {

            this.userid_for_subscription = id;

            String email1 = intent.getStringExtra("email1");
            String nomEntreprise = intent.getStringExtra("nomEntreprise");
            String departement = intent.getStringExtra("departement");
            String nom1 = intent.getStringExtra("nom1");
            String telephone1 = intent.getStringExtra("telephone1");
            String siret = intent.getStringExtra("siret");
            String sousService = intent.getStringExtra("sousService");
            String nom2 = intent.getStringExtra("nom2");
            String email2 = intent.getStringExtra("email2");
            String tel2 = intent.getStringExtra("tel2");
            String adresse = intent.getStringExtra("adresse");

            DatabaseUpdateCreate<MdP> dbCo = new DatabaseUpdateCreate(MdP.this, 1);
            dbCo.setContext(getApplicationContext());

            String SQL = "INSERT INTO interima.employeur (idUti, nomEntreprise, nomServDept, nomSousSD, siret, nomC1, nomC2, emailC1, emailC2, telC1, telC2, adresse) values ('"+id+"', '"+nomEntreprise+"', '"+departement+"', '"+sousService+"', '"+siret+"', '"+nom1+"', '"+nom2+"', '"+email1+"', '"+email2+"', '"+telephone1+"', '"+tel2+"', '"+adresse+"');";

            Log.d("MdP.java", "Requete :" + SQL);

            dbCo.setRequete(SQL);
            dbCo.execute("");

        } else if (role.equals("agenceinterim")) {

            this.userid_for_subscription = id;

            String email1 = intent.getStringExtra("email1");
            String nomEntreprise = intent.getStringExtra("nomEntreprise");
            String nom1 = intent.getStringExtra("nom1");
            String telephone1 = intent.getStringExtra("telephone1");
            String siret = intent.getStringExtra("siret");
            String nom2 = intent.getStringExtra("nom2");
            String email2 = intent.getStringExtra("email2");
            String tel2 = intent.getStringExtra("tel2");
            String adresse = intent.getStringExtra("adresse");

            DatabaseUpdateCreate<MdP> dbCo = new DatabaseUpdateCreate(MdP.this, 1);
            dbCo.setContext(getApplicationContext());

            String SQL = "INSERT INTO agenceinterim (idUti, nomAgence, siret, nomC1, nomC2, emailC1, emailC2, telC1, telC2, adresse) values ('"+id+"', '"+nomEntreprise+"', '"+siret+"', '"+nom1+"', '"+nom2+"', '"+email1+"', '"+email2+"', '"+telephone1+"', '"+tel2+"', '"+adresse+"');";

            Log.d("MdP.java", "Requete :" + SQL);

            dbCo.setRequete(SQL);
            dbCo.execute("");

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void dataForSubscriptionQuery() {
        Intent intent = getIntent();
        int idA = intent.getIntExtra("idA", 0);
        String paiement = intent.getStringExtra("paiement");
        Log.d("MdP.java", "debut query 3");
        LocalDate expiration = LocalDate.now();
        switch(idA) {
            case 2:
                expiration = expiration.plusMonths(1);
                break;
            case 3:
                expiration = expiration.plusMonths(3);
                break;
            case 4:
                expiration = expiration.plusMonths(6);
                break;
            case 5:
                expiration = expiration.plusYears(1);
                break;
            case 6:
                expiration = expiration.plusYears(100);
                break;
            default:
                break;
        }
        DatabaseUpdateCreate<MdP> dbCo = new DatabaseUpdateCreate(MdP.this, 0);
        dbCo.setContext(getApplicationContext());
        String SQL = "INSERT INTO interima.souscription (idAbonnement, idUti, souscription, expiration, paiement) values ('"+idA+"', '"+userid_for_subscription+"', '"+LocalDate.now()+"', '"+expiration+"', '"+paiement+"');";
        Log.d("MdP.java", "Requete :" + SQL);
        dbCo.setRequete(SQL);
        dbCo.execute("");

        Intent signin = new Intent(getApplicationContext(), SignIn.class);
        startActivity(signin);
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
