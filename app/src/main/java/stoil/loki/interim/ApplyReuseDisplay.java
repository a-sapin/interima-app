package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
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

public class ApplyReuseDisplay extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1993;
    private ArrayList<CandidatureData> candidatures = new ArrayList<>();

    private ApplyReuseAdapter adapter;

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_reuse);

        ListingCandidatureData<ApplyReuseDisplay> dbCo = new ListingCandidatureData<>(ApplyReuseDisplay.this);
        dbCo.setContext(getApplicationContext());
        dbCo.setRequete("Select * from interima.candidature where idUti = '"+ getInfoTokenID() +"';");
        dbCo.execute("");

//        ArrayList<CandidatureData> candidatures = new ArrayList<>();
//
//        for(int i = 0; i < 10; i++) {
//            candidatures.add(new CandidatureData("Candidature Developpeur FullStack"));
//        }
//
//        RecyclerView list_candidatures = findViewById(R.id.apply_list);
//        list_candidatures.setLayoutManager(layoutManager);
//        list_candidatures.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//
//        ApplyReuseAdapter adapter = new ApplyReuseAdapter(candidatures);
//        list_candidatures.setAdapter(adapter);

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

    public void onQueryResult(ArrayList<CandidatureData> candidaturesQ) {

        TextView rien = findViewById(R.id.empty_textview);

        if (candidaturesQ.isEmpty()){
            rien.setVisibility(View.VISIBLE);

        } else {
            this.candidatures = candidaturesQ;

            rien.setVisibility(View.GONE);
            RecyclerView recyclerView = findViewById(R.id.apply_list);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            this.adapter = new ApplyReuseAdapter(candidatures);
            recyclerView.setAdapter(adapter);
        }

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

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                String smsbody = "Partagé via Interima: TITRE, DATE, " +
                        "LIEN CV, LIEN LM";
                sendIntent.putExtra("sms_body", smsbody);
                startActivity(sendIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Activez l'accès à vos messages pour " +
                        "partager cette annonce par SMS.", Toast.LENGTH_LONG).show();
            }
        }
    }

}