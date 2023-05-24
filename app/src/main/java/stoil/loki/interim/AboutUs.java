package stoil.loki.interim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        LinearLayout mainlayout = (LinearLayout) findViewById(R.id.aboutus);

        LinearLayout.LayoutParams tvlayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tv1 = new TextView(this);
        tv1.setText("L'équipe PSOME (Puccio Sapin Ongaro Mobile Engineering) est une équipe " +
                "constituée d'Océane ONGARO, de Lorenzo PUCCIO et d'Arthur SAPIN dans le cadre de " +
                "l'unité d'enseignement HAI811I - Développement et programmation pour supports " +
                "mobiles. Nous vous proposons l'application Interima.\n\n" +
                "L'application Interima est une application d'intérim qui propose à un " +
                "utilisateur chercheur d'emploi de s'inscrire, de partager et de candidater à des annonces " +
                "publiées par des employeurs ou des agences d'intérim. L'application repose sur " +
                "une base de données MySQL et permet à un utilisateur de consulter ses " +
                "candidatures ainsi que leur état: approuvée ou rejetée par l'entité qui " +
                "publie l'annonce. Interima propose à ses utilisateurs de rechercher une annonce " +
                "avec une liste de mots clés, puis de filtrer ses résultats par domaine.");

        tv1.setTextSize(16);
        tv1.setLayoutParams(tvlayout);
        mainlayout.addView(tv1);

    }
}