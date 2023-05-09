package stoil.loki.interim;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        Button apply = findViewById(R.id.button);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChooseApply.class);
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
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(view.getContext(), share);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setForceShowIcon(true);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        Toast.makeText(getApplicationContext(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                // Showing the popup menu
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

}
