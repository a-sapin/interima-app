package stoil.loki.interim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ApplyListDisplay extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1993;
    private ArrayList<CandidatureData> candidatures = new ArrayList<>();
    ApplyListAdapter adapter;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_list_display);
        System.setProperty("file.encoding", "UTF-8");

        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intenth);
                        return true;
                    case R.id.favoris:
                        if(((ApplyListDisplay)menu.getContext()).getInfoTokenID() != null) {
                            Intent intentf = new Intent(getApplicationContext(), Bookmarks.class);
                            startActivity(intentf);
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Connectez-vous pour " +
                                    "accéder à cette fonctionnalité.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.recherche:
                        Intent intents = new Intent(getApplicationContext(), RecherchePage.class);
                        startActivity(intents);
                        return true;
                    case R.id.notifs:
                        if(((ApplyListDisplay)menu.getContext()).getInfoTokenID() != null) {
                            Intent intentn = new Intent(getApplicationContext(), Notifications.class);
                            startActivity(intentn);
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Connectez-vous pour " +
                                    "accéder à cette fonctionnalité.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.profil:
                        Intent intentp = new Intent(getApplicationContext(), ProfilDisplay.class);
                        startActivity(intentp);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Appuyez de nouveau sur le bouton " +
                        "de partage par SMS pour partager la candidature.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Activez l'accès à vos messages pour " +
                        "partager cette candidature par SMS.", Toast.LENGTH_LONG).show();
            }
        }
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
            this.adapter = new ApplyListAdapter(candidatures);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.profil).setChecked(true);
        ListingCandidatureData<ApplyListDisplay> dbCo = new ListingCandidatureData<>(ApplyListDisplay.this);
        dbCo.setContext(getApplicationContext());
        if(this.getInfoTokenRole().equals("Chercheur d'emploi")) {
            dbCo.setRequete("Select * from interima.candidature where idUti = '"+ getInfoTokenID() +"';");
        } else {
            dbCo.setRequete("Select * from interima.candidature where id IN (SELECT idOffre from interima.candidatureoffre WHERE idOffre IN (SELECT id from interima.offre WHERE idEmp='"+ getInfoTokenID() +"'));");
        }
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