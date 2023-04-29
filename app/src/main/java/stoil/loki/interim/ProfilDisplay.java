package stoil.loki.interim;

import android.content.Intent;
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

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected_see_profil);

        ArrayList<ItemProfil> profils = new ArrayList<>();

        int id = 0;
        Resources resources = getApplicationContext().getResources();
        Drawable ic = ResourcesCompat.getDrawable(resources, R.drawable.profil, null);
        profils.add(new ItemProfil(id, "Profil", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.marque_page, null);
        profils.add(new ItemProfil(id, "Favoris", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.recherche, null);
        profils.add(new ItemProfil(id, "Mes recherches", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.notification_push, null);
        profils.add(new ItemProfil(id, "Notifications", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.candidatures, null);
        profils.add(new ItemProfil(id, "Mes candidatures", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.lettre_de_motivation, null);
        profils.add(new ItemProfil(id, "Mes lettres de motivation", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.cv, null);
        profils.add(new ItemProfil(id, "Mes CV", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.forum, null);
        profils.add(new ItemProfil(id, "Forum", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.settings, null);
        profils.add(new ItemProfil(id, "Paramètres", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.info, null);
        profils.add(new ItemProfil(id, "A propos de nous", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.document_legal, null);
        profils.add(new ItemProfil(id, "Informations légales", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.support, null);
        profils.add(new ItemProfil(id, "Centre d'aide", ic));

        ic = ResourcesCompat.getDrawable(resources, R.drawable.marque_page, null);
        profils.add(new ItemProfil(id, "Avis", ic));

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
}