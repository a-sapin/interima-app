package stoil.loki.interim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class RecherchePage extends AppCompatActivity {

    private EditText search_bar;
    private ImageButton search;
    private static final int PERMISSION_REQUEST_CODE = 1993;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherche_page);

        search_bar = (EditText) findViewById(R.id.search_bar);

        search = (ImageButton) findViewById(R.id.search);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.offersList);
        recyclerView.setLayoutManager(layoutManager);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Principe: cliquer trigger l'instanciation d'un OfferAdapter
                // dont on filtre les résultats qui ne correspondent pas à
                // un mot clé entré dans search_bar

                ArrayList<Offer> offers = new ArrayList<Offer>();
                for (int i = 0; i < 10; i++) {
                    offers.add(new Offer(1, "Developpeur Fullstack", "capgemini.com"));
                }

                recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
                OfferAdapter adapter = new OfferAdapter(offers);
                recyclerView.setAdapter(adapter);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                String smsbody = "Partagé via Interima: TITRE, DUREE, " +
                        "REMUNERATION, LIEN SI SOURCE DISPONIBLE";
                sendIntent.putExtra("sms_body", smsbody);
                startActivity(sendIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Activez l'accès à vos messages pour " +
                        "partager cette annonce par SMS.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.recherche).setChecked(true);
    }

}