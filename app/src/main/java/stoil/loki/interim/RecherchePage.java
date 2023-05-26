package stoil.loki.interim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class RecherchePage extends AppCompatActivity {

    private EditText search_bar;
    private ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherche_page);

        search_bar = (EditText) findViewById(R.id.search_bar);

        search = (ImageButton) findViewById(R.id.search);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.offersList);
        recyclerView.setLayoutManager(layoutManager);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Principe: cliquer trigger l'instanciation d'un OfferAdapter
                // dont on filtre les résultats qui ne correspondent pas à
                // un mot clé entré dans search_bar

                ArrayList<Offer> offers = new ArrayList<Offer>();
                for (int i = 0; i < 10; i++) {
                    offers.add(new Offer(1, "Developpeur Fullstack", "capgemini.com"));
                }

                recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
                OfferAdapter adapter = new OfferAdapter(offers);
                recyclerView.setAdapter(adapter);
            }
        });

    }
}