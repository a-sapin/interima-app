package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FullOffer extends AppCompatActivity {

    private Offer offer;
    private static final int PERMISSION_REQUEST_CODE = 1993;

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                String smsbody = "Partagé via Interima: TITRE, DUREE, " +
                        "REMUNERATION, LIEN SI SOURCE DISPONIBLE";
                sendIntent.putExtra("sms_body", smsbody);
                startActivity(sendIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Activez l'accès à vos messages pour " +
                        "partager cette annonce par SMS.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String encodeString(String text) {
        try {
            byte[] utf8Bytes = text.getBytes("ISO-8859-1");
            return new String(utf8Bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return text;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_offer);

        Intent intent = getIntent();
        offer = (Offer) intent.getSerializableExtra("offer");

//        String text_example = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam auctor tellus eget risus pharetra, vel consequat nunc tempor. Integer ac neque arcu. Maecenas malesuada erat eu ultrices eleifend. Aenean a semper leo, sit amet lobortis est. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed ornare dolor eu metus consequat ullamcorper. Sed porta sit amet sapien id euismod. Aliquam hendrerit convallis ante, in feugiat enim pharetra eu. Proin vitae libero nulla. Aliquam quis rutrum velit. Suspendisse potenti. In hac habitasse platea dictumst. Sed scelerisque ex ac mauris consectetur, at ultrices felis iaculis. Sed mattis ante a odio mollis malesuada.\n" +
//                "\n" +
//                "Nunc vestibulum turpis et mi rutrum, sit amet feugiat dolor pulvinar. Sed ut fringilla turpis. Sed ut est quis nisl facilisis dapibus. Quisque ut laoreet eros. Donec pretium nibh eget massa consequat, euismod pellentesque ipsum gravida. Sed varius, libero ut pretium aliquet, elit elit commodo justo, ut aliquam nulla sapien a augue. Quisque ut suscipit mauris. Vivamus mollis augue eget tortor feugiat malesuada. Vivamus feugiat augue a lacinia tempor.";

        TextView description = findViewById(R.id.textView8);
        description.setText(encodeString(offer.getDescription()));

        TextView titre = findViewById(R.id.textView2);
        titre.setText(encodeString(offer.getTitle()));

        TextView source = findViewById(R.id.textView3);
        source.setText(encodeString(source.getText().toString()+offer.getUrl()));

        TextView datePoste = findViewById(R.id.textView4);
        datePoste.setText(datePoste.getText().toString()+offer.getDatePublication());

        TextView periode = findViewById(R.id.textView9);
        String textPeriode = "du " + offer.getDateDebut() + " à " + offer.getDateFin();
        periode.setText(textPeriode);

        TextView renumeration = findViewById(R.id.textView10);
        String textRemu = "Rénumération : " + offer.getSalaire()+"€/hr.";
        renumeration.setText(textRemu);

        TextView fin = findViewById(R.id.textView5);
        String textfin = "Fin : " + offer.getDateFermeture();
        fin.setText(textfin);

        Button apply = findViewById(R.id.button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChooseApply.class);
                intent.putExtra("offerid", offer.getId());
                view.getContext().startActivity(intent);
            }
        });

        ImageButton bookmark = findViewById(R.id.button2);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ajout/retirer bookmark de la bdd + changement de l image du bouton

                Drawable imageDrawable = bookmark.getDrawable();

                Resources resources = getResources();
                Drawable marque = ResourcesCompat.getDrawable(resources, R.drawable.marque_page, null);
                Drawable marque_vide = ResourcesCompat.getDrawable(resources, R.drawable.marque_page_vide, null);

                if(imageDrawable.getConstantState().equals(marque.getConstantState())) {
                    bookmark.setImageDrawable(marque_vide);
                    // retirer le bookmark de la bdd

                } else {
                    bookmark.setImageDrawable(marque);
                    // ajouter le bookmark dans la bdd

                }
            }
        });

        ImageButton share = findViewById(R.id.button3);

        share.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), share);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setForceShowIcon(true);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()) {
                            case R.id.share_sms:
                                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                    sendIntent.setData(Uri.parse("sms:"));
                                    String TITRE = encodeString(offer.getTitle());
                                    String REMUNERATION = Float.toString(offer.getSalaire());
                                    String SOURCE = offer.getUrl();
                                    String smsbody = "Partagé via Interima: "+TITRE+", "+textPeriode+", " +
                                            REMUNERATION+"€/hr.";
                                    if (!SOURCE.isEmpty()){
                                        smsbody+=" ("+encodeString(SOURCE)+")";
                                    }
                                    sendIntent.putExtra("sms_body", smsbody);
                                    startActivity(sendIntent);
                                } else {
                                    ActivityCompat.requestPermissions(FullOffer.this, new String[]{android.Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
                                }
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Partage via " + menuItem.getTitle() + " à venir", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Gérez la redirection ici
                switch (item.getItemId()) {
                    case R.id.home:
                        // Redirigez vers l'écran d'accueil

                        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intenth);
                        return true;

                    case R.id.favoris:

                        Intent intentf = new Intent(getApplicationContext(), Bookmarks.class);
                        startActivity(intentf);
                        return true;
                    case R.id.recherche:
                        Intent intents = new Intent(getApplicationContext(), RecherchePage.class);
                        startActivity(intents);
                        return true;
                    case R.id.notifs:

                        Intent intentn = new Intent(getApplicationContext(), Notifications.class);
                        startActivity(intentn);
                        return true;

                    case R.id.profil:
                        // si connecter donner la page du profil
                        // sinon on demande la co ou inscription

                        if(true) {
                            Intent intentp = new Intent(getApplicationContext(), ProfilDisplay.class);
                            startActivity(intentp);
                        } else {
                            Intent intentp = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(intentp);
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    public ArrayList<String> getInfoToken() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("User DATA", Context.MODE_PRIVATE);
        ArrayList<String> value = new ArrayList<>();
        value.add(sharedPreferences.getString("role", null));
        value.add(sharedPreferences.getString("id", null));

        return value;
    }

    public String getInfoTokenID() {
        ArrayList<String> info = getInfoToken();
        return info.get(1);
    }

    public String getInfoTokenRole() {
        ArrayList<String> info = getInfoToken();
        return info.get(0);
    }

}
