package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WhoAreYou extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.who_are_you);

        Button seeker = findViewById(R.id.button5);
        Button corp = findViewById(R.id.button6);
        Button agency = findViewById(R.id.button7);
        Button gest = findViewById(R.id.button8);

        seeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUp.class);
                view.getContext().startActivity(intent);
            }
        });

        corp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUpCo.class);
                view.getContext().startActivity(intent);
            }
        });

        agency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUpCo.class);
                view.getContext().startActivity(intent);
            }
        });

        gest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // faire un comportement pour un gestionnaire
            }
        });

    }
}