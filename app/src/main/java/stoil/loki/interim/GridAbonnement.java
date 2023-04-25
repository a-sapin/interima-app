package stoil.loki.interim;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GridAbonnement extends AppCompatActivity {
    GridView simpleGrid;
    int logos[] = {R.drawable.abonnement_annee, R.drawable.abonnement_indeterminee, R.drawable.abonnement_mois, R.drawable.abonnement_ponctuel,
            R.drawable.abonnement_semestre, R.drawable.abonnement_trimestre};
    String types[] = {"Annuel", "Indéterminé", "Mensuel", "Ponctuel", "Semestriel", "Trimestriel"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abonnements);
        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), logos, types);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), GridAbView.class);
                intent.putExtra("image", logos[position]);
                intent.putExtra("titre", types[position]);
                startActivity(intent);
            }
        });
    }
}