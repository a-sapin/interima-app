package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class GridAbView extends AppCompatActivity {
    ImageView selectedImage;
    TextView title, prix, avantages, conditions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abonnement_selection);
        selectedImage = (ImageView) findViewById(R.id.selectedImage);
        title = findViewById(R.id.textView37);

        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 0);

        TextView titre = findViewById(R.id.textView37);
        titre.setText(intent.getStringExtra("titre"));

        TextView prix = findViewById(R.id.prix);
        String prixT = "Prix : " + intent.getFloatExtra("prix", 0) + "€";
        prix.setText(prixT);

        TextView avantages = findViewById(R.id.avantages);
        String avantagesT = "Avantages : "+intent.getStringExtra("avantages");
        avantages.setText(avantagesT);

        TextView conditions = findViewById(R.id.conditions);
        String conditionsT = "Conditions : "+intent.getStringExtra("conditions");
        conditions.setText(conditionsT);


        selectedImage.setImageResource(intent.getIntExtra("image", 0));
        title.setText(intent.getStringExtra("titre"));

        Button next = findViewById(R.id.button10);

        Spinner pay = findViewById(R.id.spinner);
        List<String> pay_means = new ArrayList<>();
        pay_means.add("Prélèvement automatique");
        pay_means.add("Carte");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pay_means);
        pay.setAdapter(adapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // passer au paiement donc refaire la redirection vers la bonne page
                Intent intents = new Intent(view.getContext(), MdP.class);

                intents.putExtra("role", intent.getStringExtra("role") );
                intents.putExtra("email1", intent.getStringExtra("email1") );
                intents.putExtra("departement", intent.getStringExtra("departement"));
                intents.putExtra("adresse", intent.getStringExtra("adresse"));
                intents.putExtra("nomEntreprise", intent.getStringExtra("nomEntreprise"));
                intents.putExtra("nom1", intent.getStringExtra("nom1"));
                intents.putExtra("telephone1", intent.getStringExtra("telephone1"));
                intents.putExtra("siret", intent.getStringExtra("siret"));
                intents.putExtra("sousService", intent.getStringExtra("sousService"));
                intents.putExtra("nom2", intent.getStringExtra("nom2"));
                intents.putExtra("email2", intent.getStringExtra("email2"));
                intents.putExtra("tel2", intent.getStringExtra("tel2"));

                intents.putExtra("idA", id );
                intents.putExtra("paiement", pay.getSelectedItem().toString());

                view.getContext().startActivity(intents);
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
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.profil).setChecked(true);
    }
}