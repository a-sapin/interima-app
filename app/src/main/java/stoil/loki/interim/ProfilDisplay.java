package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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
    private UserData currentuser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected_see_profil);

        ArrayList<ItemProfil> profils = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);

        int id = 0;

        String userrole = this.getInfoTokenRole();
        if(userrole != null) {
            System.out.println(userrole);
        }

        Resources resources = getApplicationContext().getResources();
        Drawable ic = ResourcesCompat.getDrawable(resources, R.drawable.profil, null);

        if(userrole != null) {
            if(userrole.equals("Employeur") || userrole.equals("Agence d'intérim")) {
                profils.add(new ItemProfil(id, "Mon profil", ic, ProfileModifAg.class, currentuser));
                ic = ResourcesCompat.getDrawable(resources, R.drawable.crayon, null);
                profils.add(new ItemProfil(id, "Modifier profil", ic, EditProfileCo.class, currentuser));
                ic = ResourcesCompat.getDrawable(resources, R.drawable.candidatures, null);
                profils.add(new ItemProfil(id, "Publier une offre", ic, CreateOffer.class, null));
                if(userrole.equals("Agence d'intérim")) {
                    ic = ResourcesCompat.getDrawable(resources, R.drawable.candidatures, null);
                    profils.add(new ItemProfil(id, "Publier une offre depuis un fichier JSON",
                            ic, UploadOffer.class, null));
                }
            } else {
                profils.add(new ItemProfil(id, "Mon profil", ic, ProfileModifUti.class, currentuser));
                ic = ResourcesCompat.getDrawable(resources, R.drawable.crayon, null);
                profils.add(new ItemProfil(id, "Modifier profil", ic, EditProfile.class, currentuser));
            }
            ic = ResourcesCompat.getDrawable(resources, R.drawable.recherche, null);
            profils.add(new ItemProfil(id, "Mes recherches", ic, SavedSearchDisplay.class, null));

            ic = ResourcesCompat.getDrawable(resources, R.drawable.candidatures, null);
            profils.add(new ItemProfil(id, "Mes candidatures", ic, ApplyListDisplay.class, null));

            if(userrole.equals("Chercheur d'emploi")) {
                ic = ResourcesCompat.getDrawable(resources, R.drawable.lettre_de_motivation, null);
                profils.add(new ItemProfil(id, "Mes CV & lettres de motivation", ic, CVLMDisplay.class, null));
            }
        }

        if(userrole == null || userrole.equals("Chercheur d'emploi")) {
            ic = ResourcesCompat.getDrawable(resources, R.drawable.forum, null);
            profils.add(new ItemProfil(id, "Aide à la candidature", ic, ApplyHelp.class, null));
        }

        ic = ResourcesCompat.getDrawable(resources, R.drawable.info, null);
        profils.add(new ItemProfil(id, "A propos de nous", ic, AboutUs.class, null));

        if(getInfoTokenID() == null) {

            ic = ResourcesCompat.getDrawable(resources, R.drawable.sidentifier, null);
            profils.add(new ItemProfil(id, "Se connecter", ic, SignIn.class, null));

            ic = ResourcesCompat.getDrawable(resources, R.drawable.signup, null);
            profils.add(new ItemProfil(id, "S'inscrire", ic, WhoAreYou.class, null));

        } else {
            ic = ResourcesCompat.getDrawable(resources, R.drawable.sidentifier, null);
            profils.add(new ItemProfil(id, "Se déconnecter", ic, Disconnect.class, null));
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
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intenth);
                        return true;
                    case R.id.favoris:
                        if(((ProfilDisplay)menu.getContext()).getInfoTokenID() != null) {
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
                        if(((ProfilDisplay)menu.getContext()).getInfoTokenID() != null) {
                            Intent intentn = new Intent(getApplicationContext(), Notifications.class);
                            startActivity(intentn);
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Connectez-vous pour " +
                                    "accéder à cette fonctionnalité.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.profil:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.profil).setChecked(true);
        if(getInfoTokenID() != null) {
            String userrole = getInfoTokenRole();
            ListingUserData<ProfilDisplay> dbCo = new ListingUserData<>(ProfilDisplay.this, userrole);
            dbCo.setContext(getApplicationContext());
            if(userrole.equals("Chercheur d'emploi")) {
                dbCo.setRequete("SELECT * FROM interima.chercheuremploi WHERE idUti="+getInfoTokenID()+";");
            }
            if(userrole.equals("Employeur")) {
                dbCo.setRequete("SELECT * FROM interima.employeur WHERE idUti="+getInfoTokenID()+";");
            }
            if(userrole.equals("Agence d'intérim")) {
                dbCo.setRequete("SELECT * FROM interima.agenceinterim WHERE idUti="+getInfoTokenID()+";");
            }
            dbCo.execute("");
        }
    }

    public void onQueryResult(UserData ud) {
        this.currentuser = ud;
        ArrayList<ItemProfil> profils = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);

        int id = 0;

        String userrole = this.getInfoTokenRole();
        if(userrole != null) {
            System.out.println(userrole);
        }

        Resources resources = getApplicationContext().getResources();
        Drawable ic = ResourcesCompat.getDrawable(resources, R.drawable.profil, null);

        if(userrole != null) {
            if(userrole.equals("Employeur") || userrole.equals("Agence d'intérim")) {
                profils.add(new ItemProfil(id, "Mon profil", ic, ProfileModifAg.class, currentuser));
                ic = ResourcesCompat.getDrawable(resources, R.drawable.crayon, null);
                profils.add(new ItemProfil(id, "Modifier profil", ic, EditProfileCo.class, currentuser));
                ic = ResourcesCompat.getDrawable(resources, R.drawable.candidatures, null);
                profils.add(new ItemProfil(id, "Publier une offre", ic, CreateOffer.class, null));
                if(userrole.equals("Agence d'intérim")) {
                    ic = ResourcesCompat.getDrawable(resources, R.drawable.candidatures, null);
                    profils.add(new ItemProfil(id, "Publier une offre depuis un fichier JSON",
                            ic, UploadOffer.class, null));
                }
            } else {
                profils.add(new ItemProfil(id, "Mon profil", ic, ProfileModifUti.class, currentuser));
                ic = ResourcesCompat.getDrawable(resources, R.drawable.crayon, null);
                profils.add(new ItemProfil(id, "Modifier profil", ic, EditProfile.class, currentuser));
            }
            ic = ResourcesCompat.getDrawable(resources, R.drawable.recherche, null);
            profils.add(new ItemProfil(id, "Mes recherches", ic, SavedSearchDisplay.class, null));

            ic = ResourcesCompat.getDrawable(resources, R.drawable.candidatures, null);
            profils.add(new ItemProfil(id, "Mes candidatures", ic, ApplyListDisplay.class, null));

            if(userrole.equals("Chercheur d'emploi")) {
                ic = ResourcesCompat.getDrawable(resources, R.drawable.lettre_de_motivation, null);
                profils.add(new ItemProfil(id, "Mes CV & lettres de motivation", ic, CVLMDisplay.class, null));
            }
        }

        if(userrole == null || userrole.equals("Chercheur d'emploi")) {
            ic = ResourcesCompat.getDrawable(resources, R.drawable.forum, null);
            profils.add(new ItemProfil(id, "Aide à la candidature", ic, ApplyHelp.class, null));
        }

        ic = ResourcesCompat.getDrawable(resources, R.drawable.info, null);
        profils.add(new ItemProfil(id, "A propos de nous", ic, AboutUs.class, null));

        if(getInfoTokenID() == null) {

            ic = ResourcesCompat.getDrawable(resources, R.drawable.sidentifier, null);
            profils.add(new ItemProfil(id, "Se connecter", ic, SignIn.class, null));

            ic = ResourcesCompat.getDrawable(resources, R.drawable.signup, null);
            profils.add(new ItemProfil(id, "S'inscrire", ic, WhoAreYou.class, null));

        } else {
            ic = ResourcesCompat.getDrawable(resources, R.drawable.sidentifier, null);
            profils.add(new ItemProfil(id, "Se déconnecter", ic, Disconnect.class, null));
        }

        RecyclerView list_profil = findViewById(R.id.list_profil);
        list_profil.setLayoutManager(layoutManager);
        list_profil.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        ProfilAdapter adapter = new ProfilAdapter(profils);
        list_profil.setAdapter(adapter);
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