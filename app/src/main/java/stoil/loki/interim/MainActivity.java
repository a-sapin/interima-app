package stoil.loki.interim;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements Serializable, LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private static final int PERMISSION_REQUEST_CODE = 1993; // For access to SMS sharing
    private LocationManager locationManager;
    private ArrayList<Offer> offers = new ArrayList<>();
    private ArrayList<Integer> bookmarked_ids = new ArrayList<>();
    OfferAdapter adapter;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    //Location Variables//
    double curLong = 0.0;
    double curLat = 0.0;
    boolean geoPermGranted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.setProperty("file.encoding", "UTF-8");

        /*ListingOffer<MainActivity> dbCo = new ListingOffer<>(MainActivity.this);
        dbCo.setContext(getApplicationContext());
        dbCo.setRequete("Select * from interima.offre;");
        dbCo.execute("");*/

        File file = new File(getFilesDir().toString() + "/first_time_launch.txt");
        System.out.println(getFilesDir());
        if (!file.exists()) {
            // This is the first launch
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.getBaseContext().openFileOutput("first_time_launch.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write("notified");
                outputStreamWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Intent intent = new Intent(this, Geolocalisation.class);
            startActivity(intent);
            finish();
        } else { //This isn't the first launch
            /*for (int i = 0; i < 10; i++) {
                offers.add(new Offer(1, "Developpeur Fullstack", "capgemini.com"));
            }

            RecyclerView recyclerView = findViewById(R.id.offersList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            this.adapter = new OfferAdapter(offers);
            recyclerView.setAdapter(adapter);*/

            this.adapter = new OfferAdapter(offers, bookmarked_ids);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                geoPermGranted = false;
            }
            if (geoPermGranted)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, this);

            BottomNavigationView menu = findViewById(R.id.navigation);
            menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            //Nous sommes déjà à l'accueil.
                            return true;
                        case R.id.recherche:
                            Intent intents = new Intent(getApplicationContext(), RecherchePage.class);
                            startActivity(intents);
                            return true;
                        case R.id.favoris:
                            Intent intentb = new Intent(MainActivity.this, Bookmarks.class);
                            startActivity(intentb);
                            return true;
                        case R.id.notifs:
                            Intent intentn = new Intent(MainActivity.this, Notifications.class);
                            startActivity(intentn);
                            return true;
                        case R.id.profil:
                            Intent intentp = new Intent(getApplicationContext(), ProfilDisplay.class);
                            startActivity(intentp);
                            return true;
                        default:
                            return false;
                    }
                }
            });
        }
    }

    public void onQueryResult(ArrayList<Offer> offersQ) {
        this.offers = offersQ;
        RecyclerView recyclerView = findViewById(R.id.offersList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        if(getInfoToken() != null) {
            if(getInfoTokenRole().equals("Chercheur d'emploi")) {
                ListingBookmarkedIds<MainActivity> dbCo = new ListingBookmarkedIds<>(MainActivity.this);
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

    public void onQueryResult2(ArrayList<Integer> bookmarked_idsQ) {
        this.bookmarked_ids = bookmarked_idsQ;
        this.adapter = new OfferAdapter(offers, bookmarked_ids);
        RecyclerView recyclerView = findViewById(R.id.offersList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Location permission granted, do your work here
                } else {
                    // Location permission denied
                }
                break;
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

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.home).setChecked(true);
        this.offers = new ArrayList<>();
        this.offers.add(new Offer(1, "Developpeur Fullstack", "capgemini.com"));
        ListingOffer<MainActivity> dbCo = new ListingOffer<>(MainActivity.this);
        dbCo.setContext(getApplicationContext());
        dbCo.setRequete("Select * from interima.offre;");
        dbCo.execute("");
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        if((this.curLat == latitude) && (this.curLong == longitude)) {

        } else {
            this.curLat = latitude;
            this.curLong = longitude;
            System.out.println("Location changed to: "+latitude+", "+longitude);
            sortOffers();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Do nothing
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}


    public void sortOffers() {
        for (Offer off : offers) {
            off.calculateDiff((float) curLong, (float) curLat);
        }
        Collections.sort(offers, new Comparator<Offer>() {
            @Override
            public int compare(Offer o1, Offer o2) {
                return Double.compare(o1.getDistFromUser(), o2.getDistFromUser());
            }});
        Toast.makeText(getApplicationContext(), "Actualisation de la liste des offres", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
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