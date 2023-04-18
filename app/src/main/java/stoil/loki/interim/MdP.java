package stoil.loki.interim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MdP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finishing_sign_up);

        Button sign_up = findViewById(R.id.button12);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // envoyer toutes les donnes de l inscription en bdd
                // faire la connexion de l utilisateur -> personnaliser les autres pages
            }
        });
    }
}
