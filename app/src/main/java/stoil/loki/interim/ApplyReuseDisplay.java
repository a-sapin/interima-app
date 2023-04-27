package stoil.loki.interim;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ApplyReuseDisplay extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_reuse);

        ArrayList<CandidatureData> candidatures = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            candidatures.add(new CandidatureData("Candidature Developpeur FullStack"));
        }

        RecyclerView list_candidatures = findViewById(R.id.apply_list);
        list_candidatures.setLayoutManager(layoutManager);
        list_candidatures.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        ApplyReuseAdapter adapter = new ApplyReuseAdapter(candidatures);
        list_candidatures.setAdapter(adapter);

    }
}