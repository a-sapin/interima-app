package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Bookmarks extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1993; // For access to SMS sharing
    private ArrayList<Offer> offers = new ArrayList<Offer>();
    private ArrayList<Integer> bookmarked_ids = new ArrayList<>();
    OfferAdapter adapter;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmarks);

        this.adapter = new OfferAdapter(offers, bookmarked_ids);

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

                        //Intent intentf = new Intent(getApplicationContext(), Bookmarks.class);
                        //startActivity(intentf);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Appuyez de nouveau sur le bouton " +
                            "de partage par SMS pour partager l'offre.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Activez l'accès à vos messages pour " +
                            "partager cette annonce par SMS.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void onQueryResult(ArrayList<Offer> offersQ) {
        if(offersQ.isEmpty()) {
            TextView rien = (TextView) findViewById(R.id.empty_textview);
            rien.setVisibility(View.VISIBLE);
        } else {
            this.offers = offersQ;
            RecyclerView recyclerView = findViewById(R.id.offersList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            if(getInfoToken() != null) {
                if(getInfoTokenRole().equals("Chercheur d'emploi")) {
                    ListingBookmarkedIds<Bookmarks> dbCo = new ListingBookmarkedIds<>(Bookmarks.this);
                    dbCo.setContext(getApplicationContext());
                    dbCo.setRequete("SELECT id FROM interima.offre WHERE offre.id IN (SELECT idOffre FROM interima.favori WHERE idUti="+getInfoTokenID()+");");
                    dbCo.execute("");
                } else {
                    this.adapter = new OfferAdapter(offers, new ArrayList<>());
                    recyclerView.setAdapter(adapter);
                }
            } else {
                this.adapter = new OfferAdapter(offers, new ArrayList<>());
                recyclerView.setAdapter(adapter);
            }
        }
    }

    public void onQueryResult2(ArrayList<Integer> bookmarked_idsQ) {
        this.bookmarked_ids = bookmarked_idsQ;
        this.adapter = new OfferAdapter(offers, bookmarked_ids);
        RecyclerView recyclerView = findViewById(R.id.offersList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.favoris).setChecked(true);
        this.offers = new ArrayList<>();
        this.offers.add(new Offer(1, "Developpeur Fullstack", "capgemini.com"));
        ListingOffer<Bookmarks> dbCo = new ListingOffer<>(Bookmarks.this);
        dbCo.setContext(getApplicationContext());
        dbCo.setRequete("SELECT * FROM interima.offre WHERE offre.id IN (SELECT idOffre FROM interima.favori WHERE idUti="+getInfoTokenID()+");");
        dbCo.execute("");
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
