package stoil.loki.interim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ApplyHelp extends AppCompatActivity {

    private TextView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_help);

        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.applyhelp);

        LinearLayout.LayoutParams tvlayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        tv1 = (TextView) findViewById(R.id.tvAH1);
        tv1.setText("Cette page de l'application est destinée aux utlisateurs néophytes qui " +
                "souhaitent candidater à une annonce disponible sur notre application.\n\n" +
                "");
    }
}