package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileModifUti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_data_uti);

        TextView fname = findViewById(R.id.nomView);
        TextView name = findViewById(R.id.nomServiceView);
        TextView bd = findViewById(R.id.nomSousServiceView);
        TextView nat = findViewById(R.id.siretView);
        TextView email = findViewById(R.id.name1);
        TextView city = findViewById(R.id.email_1);
        TextView phone = findViewById(R.id.phone1);

        Button modif_profil = findViewById(R.id.modifProfil);
        Button modif_mdp = findViewById(R.id.modifMdp);

        modif_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WhoAreYou.class);
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
}