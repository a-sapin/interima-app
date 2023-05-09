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

public class ProfileModifAg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_data_uti);

        TextView co_name = findViewById(R.id.nomView);
        TextView name_service = findViewById(R.id.nomServiceView);
        TextView name_s_service = findViewById(R.id.nomSousServiceView);
        TextView siret = findViewById(R.id.siretView);
        TextView name1 = findViewById(R.id.name1);
        TextView email1 = findViewById(R.id.email_1);
        TextView phone1 = findViewById(R.id.phone1);
        TextView name2 = findViewById(R.id.name2);
        TextView email2 = findViewById(R.id.email2);
        TextView phone2 = findViewById(R.id.phone2);
        TextView adress = findViewById(R.id.adress);
        TextView links = findViewById(R.id.links);
        TextView abo = findViewById(R.id.abo);
        TextView paiement = findViewById(R.id.paiement);

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