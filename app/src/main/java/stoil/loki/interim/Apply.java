package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Apply extends AppCompatActivity {

    private Offer offer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply);

        Button apply = findViewById(R.id.button18);

        // verifier que tous les champs sont remplis avant de transmettre le apply
        // mettre un Toast avec le fait que tous les champs ne sont pas remplis par exemple
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                Toast.makeText(getApplicationContext(), "Candidature envoyée", Toast.LENGTH_SHORT).show();
                view.getContext().startActivity(intent);
            }
        });

    }
}