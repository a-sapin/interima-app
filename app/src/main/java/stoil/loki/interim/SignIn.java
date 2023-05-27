package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SignIn extends AppCompatActivity {

    private static EditText email, pw;
    private static String role;
    private DatabaseConnexion<SignIn, SignIn> dbCo;
    private String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_uti);

        Button sign_in = findViewById(R.id.button9);
        Button sign_up = findViewById(R.id.button15);

        email = findViewById(R.id.editTextTextEmailAddress);
        pw = findViewById(R.id.editTextTextPassword);

        Spinner spinner = findViewById(R.id.spinner4);

        List<String> itemList = new ArrayList<>();
        itemList.add("Chercheur d'emploi");
        itemList.add("Employeur");
        itemList.add("Agence d'intérim");
        itemList.add("Gestionnaire");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role = parent.getItemAtPosition(position).toString();
                Log.d("SignIn.java", "affichage role :" + role);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Code à exécuter lorsqu'aucun élément n'est sélectionné
            }
        });


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SignIn.java", "bouton co");
                connexion();

//                SessionManager sessionManager = new SessionManager(getApplicationContext(), email.getText().toString(), role, SignIn.this );
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WhoAreYou.class);
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
                            //On est déjà sur la page de connexion
                            //Intent intentp = new Intent(getApplicationContext(), SignIn.class);
                            //startActivity(intentp);
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

        // faire en sorte que ca marche sur toutes les versions d android
        if ( android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.profil).setChecked(true);
    }

    private void connexion() {
        Log.d("SignIn.java", "debut connexion");
        dbCo = new DatabaseConnexion<>(this, SignIn.this);
        dbCo.setContext(getApplicationContext());

        String SQL;

        if (role.equals("Gestionnaire")){
            SQL = "SELECT mdp FROM interima.utilisateur WHERE id IN (SELECT idUti FROM interima.gestionnaire WHERE email = '"+email.getText().toString()+"');";

        } else if (role.equals("Chercheur d'emploi")) {
            SQL = "SELECT mdp FROM interima.utilisateur WHERE id IN (SELECT idUti FROM interima.chercheuremploi WHERE email = '"+email.getText().toString()+"');";

        } else if (role.equals("Employeur")) {
            SQL = "SELECT mdp FROM interima.utilisateur WHERE id IN (SELECT idUti FROM interima.employeur WHERE emailC1 = '"+email.getText().toString()+"');";

        } else if (role.equals("Agence d'intérim")) {
            SQL = "SELECT mdp FROM interima.utilisateur WHERE id IN (SELECT idUti FROM interima.agenceinterim WHERE emailC1 = '"+email.getText().toString()+"');";

        } else {
            SQL = "";
        }

        Log.d("SignIn.java", "Requete :" + SQL);

        dbCo.setRequete(SQL);
        dbCo.execute("");

    }

    public void onQueryResult(String result) {
        // Traitez le résultat de la requête ici
        Log.d("SignIn", "Résultat de la requête : " + result + " pw = " + pw.getText().toString() + " res = " + pw.getText().toString().trim().equals(result.trim()));
        res = result;
        if (result.contains("\n")) {
            String[] passwords = result.split("\\r?\\n");

            for (String password : passwords) {
                Log.d("SignIn", "pw = " + password);
                if (pw.getText().toString().trim().equals(password.trim())) {
                    // si l utilisateur existe bien et que son mdp est le bon on peut changer d activité sinon on reste sur la page
                    Toast.makeText(getApplicationContext(), "Bienvenue", Toast.LENGTH_SHORT).show();
                    // recuperation de l id de l utilisateur et de son role
                    // on passe la creation du token au SessionManager
                    Log.d("SignIn", "Debut sessionManager ");
                    SessionManager sessionManager = new SessionManager(getApplicationContext(), email.getText().toString(), role, SignIn.this);

                    Log.d("SignIn", "fin sessionManager ");
                    // token fait + connexion ok donc on peut revenir sur l activité principale
                    // quand la requete aura une reponse elle se terminera automatiquement

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }

        Toast.makeText(getApplicationContext(), "Mdp ou email incorrect", Toast.LENGTH_SHORT).show();
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
