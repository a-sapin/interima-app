package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProfileModifUti extends AppCompatActivity {

    private TextView fname = findViewById(R.id.nomView);
    private TextView name = findViewById(R.id.nomServiceView);
    private TextView bd = findViewById(R.id.nomSousServiceView);
    private TextView nat = findViewById(R.id.siretView);
    private TextView email = findViewById(R.id.name1);
    private TextView city = findViewById(R.id.email_1);
    private TextView phone = findViewById(R.id.phone1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_data_uti);

        Button modif_profil = findViewById(R.id.modifProfil);
        Button modif_mdp = findViewById(R.id.modifMdp);

        modif_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditProfile.class);
                view.getContext().startActivity(intent);
            }
        });

        modif_mdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MdP.class);
                view.getContext().startActivity(intent);
            }
        });

        // mettre les donnees en fonction de ce qui a été récup de la bdd

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
                        if(((ProfileModifUti)menu.getContext()).getInfoTokenID() != null) {
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
                        if(((ProfileModifUti)menu.getContext()).getInfoTokenID() != null) {
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