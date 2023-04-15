package stoil.loki.interim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FullOffer extends AppCompatActivity {

    private Offer offer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_offer);

        Intent intent = getIntent();
        offer = (Offer) intent.getSerializableExtra("offer");

        String text_example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor tellus eget risus pharetra, vel consequat nunc tempor. Integer ac neque arcu. Maecenas malesuada erat eu ultrices eleifend. Aenean a semper leo, sit amet lobortis est. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed ornare dolor eu metus consequat ullamcorper. Sed porta sit amet sapien id euismod. Aliquam hendrerit convallis ante, in feugiat enim pharetra eu. Proin vitae libero nulla. Aliquam quis rutrum velit. Suspendisse potenti. In hac habitasse platea dictumst. Sed scelerisque ex ac mauris consectetur, at ultrices felis iaculis. Sed mattis ante a odio mollis malesuada.\n" +
                "\n" +
                "Nunc vestibulum turpis et mi rutrum, sit amet feugiat dolor pulvinar. Sed ut fringilla turpis. Sed ut est quis nisl facilisis dapibus. Quisque ut laoreet eros. Donec pretium nibh eget massa consequat, euismod pellentesque ipsum gravida. Sed varius, libero ut pretium aliquet, elit elit commodo justo, ut aliquam nulla sapien a augue. Quisque ut suscipit mauris. Vivamus mollis augue eget tortor feugiat malesuada. Vivamus feugiat augue a lacinia tempor.";

        TextView description = findViewById(R.id.textView8);
        description.setText(text_example);


    }
}
