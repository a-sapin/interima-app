package stoil.loki.interim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ApplyHelp extends AppCompatActivity {

    private TextView tv1, tv2, tv3, tv4, tv5, tv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_help);

        LinearLayout applyhelp = (LinearLayout) findViewById(R.id.applyhelp);

        tv1 = (TextView) findViewById(R.id.tvAH1);
        tv1.setText("Cette page de l'application est destinée aux utlisateurs novices qui " +
                "souhaitent candidater à une annonce disponible sur notre application.\n\n" +
                "SI vous ne l'avez pas déjà fait, rendez-vous dans la section Profil de la " +
                "barre de navigation. Lors de l'étape de connexion, appuyez sur le bouton " +
                "Inscription et renseignez les informations nécessaires. Après confirmation de " +
                "votre inscription, rendez-vous sur la page principale de l'application. Sur " +
                "l'annonce de votre choix, appuyez sur le bouton Candidater, comme ci-dessous:");

        tv2 = (TextView) findViewById(R.id.tvAH2);
        tv2.setText("Vous arriverez ensuite sur la page complète de l'annonce correspondante. " +
                "Défilez jusqu'à trouver le bouton Candidater sur cette page:");

        tv3 = (TextView) findViewById(R.id.tvAH3);
        tv3.setText("Vous serez amené à faire un choix, proposé par la page suivante:");

        tv4 = (TextView) findViewById(R.id.tvAH4);
        tv4.setText("En appuyant sur Faire une nouvelle candidature, vous serez amené sur la page " +
                "ci-dessous, dans laquelle vous devez renseigner toutes les informations " +
                "nécessaires dans les champs correspondants:");

        tv5 = (TextView) findViewById(R.id.tvAH5);
        tv5.setText("Les boutons CV et Lettre de motivation proposent à l'utilisateur de " +
                "renseigner un lien vers les documents respectifs. Des services d'hébergement de " +
                "fichiers partagés tels que Google Drive sont à votre disposition afin d'héberger " +
                "ces fichiers.\n\n" +
                "Lorsque vous avez renseigné tous les champs de la candidature, vous pouvez " +
                "appuyer sur le bouton Candidater en bas de la page pour envoyer votre candidature." +
                "\n\nEn appuyant sur Réutiliser une candidature, vous serez amené sur une page " +
                "comprenant une liste de candidatures que vous avez déjà envoyées auparavant. " +
                "Chaque candidature se présente sous la forme suivante:");

        tv6 = (TextView) findViewById(R.id.tvAH6);
        tv6.setText("Appuyer sur le bouton en forme d'oeil vous permet de revoir les informations " +
                "de la candidature de votre choix. Assurez-vous bien d'avoir choisi la bonne " +
                "candidature, puis appuyez sur le bouton Sélectionner de la candidature de votre " +
                "choix. Cela vous renverra sur la page de remplissage de la candidature, avec " +
                "les informations de la candidature de votre choix pré-remplies. N'oubliez pas " +
                "de vérifier à nouveau les informations de la candidature que vous souhaitez " +
                "envoyer, puis appuyez sur Candidater pour envoyer votre candidature.");
    }
}