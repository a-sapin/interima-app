package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProfilDisplay extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected_see_profil);

        ArrayList<ItemProfil> profils = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);

        int id = 0;

        String userrole = this.getInfoTokenRole();
        System.out.println(userrole);

        Resources resources = getApplicationContext().getResources();
        Drawable ic = ResourcesCompat.getDrawable(resources, R.drawable.profil, null);
        profils.add(new ItemProfil(id, "Mon profil", ic, ProfileModifAg.class));

        if(userrole.equals("Employeur")) {
            ic = ResourcesCompat.getDrawable(resources, R.drawable.candidatures, null);
            profils.add(new ItemProfil(id, "Publier une offre", ic, CreateOffer.class));
        }

        ic = ResourcesCompat.getDrawable(resources, R.drawable.recherche, null);
        profils.add(new ItemProfil(id, "Mes recherches", ic, MainActivity.class));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.candidatures, null);
        profils.add(new ItemProfil(id, "Mes candidatures", ic, ApplyListDisplay.class));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.lettre_de_motivation, null);
        profils.add(new ItemProfil(id, "Mes CV & lettres de motivation", ic, CVLMDisplay.class));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.forum, null);
        profils.add(new ItemProfil(id, "Aide à la candidature", ic, ApplyHelp.class));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.info, null);
        profils.add(new ItemProfil(id, "A propos de nous", ic, AboutUs.class));

        if(getInfoTokenID() == null) {

            ic = ResourcesCompat.getDrawable(resources, R.drawable.sidentifier, null);
            profils.add(new ItemProfil(id, "Se connecter", ic, SignIn.class));

            ic = ResourcesCompat.getDrawable(resources, R.drawable.signup, null);
            profils.add(new ItemProfil(id, "S'inscrire", ic, WhoAreYou.class));

        } else {
            // mettre la page de deconnection

        }

        RecyclerView list_profil = findViewById(R.id.list_profil);
        list_profil.setLayoutManager(layoutManager);
        list_profil.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        ProfilAdapter adapter = new ProfilAdapter(profils);
        list_profil.setAdapter(adapter);

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
                            // On est déjà dans ProfilDisplay
                            //Intent intentp = new Intent(getApplicationContext(), ProfilDisplay.class);
                            //startActivity(intentp);
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