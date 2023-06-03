package stoil.loki.interim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ApplyHelp extends AppCompatActivity {

    private TextView tv1, tv2, tv3, tv4, tv5, tv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_help);

        LinearLayout applyhelp = (LinearLayout) findViewById(R.id.applyhelp);

        tv1 = (TextView) findViewById(R.id.tvAH1);
        tv1.setText("Cette page de l'application est destinée aux utlisateurs novices qui " +
                "souhaitent candidater à une annonce disponible sur notre application.\n\n" +
                "SI vous ne l'avez pas déjà fait, rendez-vous dans la section Profil de la " +
                "barre de navigation. Lors de l'étape de connexion, appuyez sur le bouton " +
                "Inscription et renseignez les informations nécessaires. Après confirmation de " +
                "votre inscription, rendez-vous sur la page principale de l'application. Sur " +
                "l'annonce de votre choix, appuyez sur le bouton Candidater, comme ci-dessous:\n");

        tv2 = (TextView) findViewById(R.id.tvAH2);
        tv2.setText("\nVous arriverez ensuite sur la page complète de l'annonce correspondante. " +
                "Défilez jusqu'à trouver le bouton Candidater sur cette page:\n");

        tv3 = (TextView) findViewById(R.id.tvAH3);
        tv3.setText("\nVous serez amené à faire un choix, proposé par la page suivante:\n");

        tv4 = (TextView) findViewById(R.id.tvAH4);
        tv4.setText("\nEn appuyant sur Faire une nouvelle candidature, vous serez amené sur la page " +
                "ci-dessous, dans laquelle vous devez renseigner toutes les informations " +
                "nécessaires dans les champs correspondants:\n");

        tv5 = (TextView) findViewById(R.id.tvAH5);
        tv5.setText("\nLes boutons CV et Lettre de motivation proposent à l'utilisateur de " +
                "renseigner un lien vers les documents respectifs. Des services d'hébergement de " +
                "fichiers partagés tels que Google Drive sont à votre disposition afin d'héberger " +
                "ces fichiers.\n\n" +
                "Lorsque vous avez renseigné tous les champs de la candidature, vous pouvez " +
                "appuyer sur le bouton Candidater en bas de la page pour envoyer votre candidature." +
                "\n\nEn appuyant sur Réutiliser une candidature, vous serez amené sur une page " +
                "comprenant une liste de candidatures que vous avez déjà envoyées auparavant. " +
                "Chaque candidature se présente sous la forme suivante:\n");

        tv6 = (TextView) findViewById(R.id.tvAH6);
        tv6.setText("\nAppuyer sur le bouton en forme d'oeil vous permet de revoir les informations " +
                "de la candidature de votre choix. Assurez-vous bien d'avoir choisi la bonne " +
                "candidature, puis appuyez sur le bouton Sélectionner de la candidature de votre " +
                "choix. Cela vous renverra sur la page de remplissage de la candidature, avec " +
                "les informations de la candidature de votre choix pré-remplies. N'oubliez pas " +
                "de vérifier à nouveau les informations de la candidature que vous souhaitez " +
                "envoyer, puis appuyez sur Candidater pour envoyer votre candidature.");

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
                        if(((ApplyHelp)menu.getContext()).getInfoTokenID() != null) {
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
                        if(((ApplyHelp)menu.getContext()).getInfoTokenID() != null) {
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