package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WhoAreYou extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.who_are_you);

        Button seeker = findViewById(R.id.button5);
        Button corp = findViewById(R.id.button6);
        Button agency = findViewById(R.id.button7);

        seeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUp.class);
                view.getContext().startActivity(intent);
            }
        });

        corp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUpCo.class);
                intent.putExtra("role","employeur");
                view.getContext().startActivity(intent);
            }
        });

        agency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUpCo.class);
                intent.putExtra("role","agenceinterim");
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