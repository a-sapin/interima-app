package stoil.loki.interim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("L'équipe PSOME (Puccio Sapin Ongaro Mobile Engineering) est une équipe " +
                "constituée d'Océane ONGARO, de Lorenzo PUCCIO et d'Arthur SAPIN dans le cadre de " +
                "l'unité d'enseignement HAI811I - Développement et programmation pour supports " +
                "mobiles. Nous vous proposons l'application Interima.\n\n" +
                "L'application Interima est une application d'intérim qui propose à un " +
                "utilisateur chercheur d'emploi de s'inscrire, de partager et de candidater à des annonces " +
                "publiées par des employeurs ou des agences d'intérim. L'application repose sur " +
                "une base de données MySQL et permet à un utilisateur de consulter ses " +
                "candidatures ainsi que leur état: approuvée ou rejetée par l'entité qui " +
                "publie l'annonce. Interima propose à ses utilisateurs de rechercher une annonce " +
                "avec une liste de mots clés, puis de filtrer ses résultats par domaine.");

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

}