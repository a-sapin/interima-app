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
    private ArrayList<Offer> offers = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private OfferAdapter adapter;
    private static final int PERMISSION_REQUEST_CODE = 1993;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherche_page);

        search_bar = (EditText) findViewById(R.id.search_bar);

        search = (ImageButton) findViewById(R.id.search);

        RecyclerView recyclerView = findViewById(R.id.offersList);
        recyclerView.setLayoutManager(layoutManager);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_str = search_bar.getText().toString();
                if(search_str.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Veuillez entrer au moins un mot clé.", Toast.LENGTH_LONG).show();
                } else {
                    String search_array = "(";
                    String[] search_keywords = search_str.split(" ");
                    for(int i=0; i < search_keywords.length; i++) {
                        if(i == search_keywords.length-1) {
                            search_array+="'"+search_keywords[i]+"')";
                        } else {
                            search_array+="'"+search_keywords[i]+"', ";
                        }
                    }
                    ListingOffer<RecherchePage> dbCo = new ListingOffer<>(RecherchePage.this);
                    dbCo.setContext(getApplicationContext());
                    dbCo.setRequete("SELECT * from interima.offre where interima.offre.id in (SELECT idOffre from interima.contient where interima.contient.idMot in (SELECT id from interima.motclef where interima.motclef.mot in "+search_array+"));");
                    dbCo.execute("");
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
                Toast.makeText(getApplicationContext(), "Appuyez de nouveau sur le bouton " +
                        "de partage par SMS pour partager l'offre.", Toast.LENGTH_LONG).show();
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

    public void onQueryResult(ArrayList<Offer> offersQ) {
        this.offers = offersQ;

        RecyclerView recyclerView = findViewById(R.id.offersList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.adapter = new OfferAdapter(offers);
        recyclerView.setAdapter(adapter);
    }

}