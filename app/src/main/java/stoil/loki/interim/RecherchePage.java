package stoil.loki.interim;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class RecherchePage extends AppCompatActivity {

    private EditText search_bar;
    private ImageButton search;
    private ArrayList<Offer> offers = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private OfferAdapter adapter;
    private static final int PERMISSION_REQUEST_CODE = 1993;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherche_page);
        System.out.println(LocalDate.now());

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
                    dbCo.setRequete("SELECT * from interima.offre where interima.offre.titre LIKE '%"+search_bar.getText().toString()+"%' UNION SELECT * from interima.offre where interima.offre.id in (SELECT idOffre from interima.contient where interima.contient.idMot in (SELECT id from interima.motclef where interima.motclef.mot in "+search_array+"));");
                    dbCo.execute("");
                    SQLSubmit();
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void SQLSubmit() {
        Log.d("RecherchePage.java", "Adding search to saved searches");
        DatabaseUpdateCreate<RecherchePage> dbCo = new DatabaseUpdateCreate<>(RecherchePage.this, true);
        dbCo.setContext(getApplicationContext());

        String SQL, search_bar_str;
        search_bar_str = search_bar.getText().toString();
        SQL = "INSERT INTO recherche (idUti, metier, lieu, debut, fin, dateRecherche) values ('"+getInfoTokenID()+"', '"+search_bar_str+"', 'NOLIEU', '2023-05-28', '2023-05-28', '"+ LocalDate.now() +"');";

        Log.d("RecherchePage.java", "Requete :" + SQL);

        dbCo.setRequete(SQL);
        dbCo.execute("");

    }

    public void onQueryResult(ArrayList<Offer> offersQ) {
        this.offers = offersQ;

        RecyclerView recyclerView = findViewById(R.id.offersList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.adapter = new OfferAdapter(offers);
        recyclerView.setAdapter(adapter);

        if(offersQ.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Pas de résultat.", Toast.LENGTH_LONG).show();
        }
    }

    public String convertDate(String dateOrigine)
    {
        String resConvertedDate = "";
        String outputFormat = "yyyy-MM-dd";
        String desiredOutputDate = null;

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormatObj = new SimpleDateFormat(outputFormat);

        try {
            Date date = inputFormat.parse(dateOrigine);
            desiredOutputDate = outputFormatObj.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("====== CONVERTDATE IN CREATEOFFER.JAVA =======");
        System.out.println("Input Date: " + dateOrigine);
        System.out.println("Desired Output Date: " + desiredOutputDate);
        resConvertedDate = desiredOutputDate;
        return resConvertedDate;
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