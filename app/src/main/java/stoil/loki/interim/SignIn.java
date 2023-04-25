package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_uti);

        Button sign_in = findViewById(R.id.button9);
        Button sign_up = findViewById(R.id.button15);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // faire la verification et ensuite vers le changement sur toutes les pages ou la connexion est necessaire

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WhoAreYou.class);
                view.getContext().startActivity(intent);
            }
        });

    }
}
