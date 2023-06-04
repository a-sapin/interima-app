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

public class UpdateMdP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_mdp);

        Button update = findViewById(R.id.button12);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText mdp1E = findViewById(R.id.editTextTextPassword2);
                String mdp1 = mdp1E.getText().toString();

                EditText mdp2E = findViewById(R.id.editTextTextPassword3);
                String mdp2 = mdp2E.getText().toString();

                if (mdp1.equals(mdp2)) {
                    Log.d("MdP.java", "debut connexion");
                    DatabaseUpdateCreate<UpdateMdP> dbCo = new DatabaseUpdateCreate(UpdateMdP.this, 2);
                    dbCo.setContext(getApplicationContext());
                    String SQL = "UPDATE interima.utilisateur SET mdp='"+mdp1+"' WHERE id="+getInfoTokenID()+";";
                    Log.d("UpdateMdP.java", "Requete :" + SQL);
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
                        if(((UpdateMdP)menu.getContext()).getInfoTokenID() != null) {
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
                        if(((UpdateMdP)menu.getContext()).getInfoTokenID() != null) {
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
        Toast.makeText(this, "Mot de passe mis à jour", Toast.LENGTH_SHORT).show();
        onBackPressed();
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
