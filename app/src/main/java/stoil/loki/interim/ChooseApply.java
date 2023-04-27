package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseApply extends AppCompatActivity {

    private Offer offer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_apply);

        Button reuse = findViewById(R.id.button5);
        Button newOne = findViewById(R.id.button6);

        reuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // changer la classe a lancer pour le choix de la candidature a donne
                Intent intent = new Intent(view.getContext(), ApplyReuseDisplay.class);
                view.getContext().startActivity(intent);
            }
        });

        newOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // changer la classe a lancer pour le choix de la candidature a donne
                Intent intent = new Intent(view.getContext(), Apply.class);
                view.getContext().startActivity(intent);
            }
        });

    }
}