package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GridAbView extends AppCompatActivity {
    ImageView selectedImage;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abonnement_selection);
        selectedImage = (ImageView) findViewById(R.id.selectedImage);
        title = findViewById(R.id.textView37);
        Intent intent = getIntent(); // get Intent which we set from Previous Activity
        selectedImage.setImageResource(intent.getIntExtra("image", 0));
        title.setText(intent.getStringExtra("titre"));

        Button next = findViewById(R.id.button10);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // passer au paiement donc refaire la redirection vers la bonne page
                Intent intent = new Intent(view.getContext(), MdP.class);

                view.getContext().startActivity(intent);
            }
        });// get image from Intent and set it in ImageView
    }
}