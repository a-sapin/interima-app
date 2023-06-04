package stoil.loki.interim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EditProfileCo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_co);

        Button next = findViewById(R.id.button10);

        Intent intentp = getIntent();

        EditText email1E = findViewById(R.id.editTextTextEmailAddress2);
        email1E.setText(intentp.getStringExtra("udEmail"));

        EditText nomEntrepriseE = findViewById(R.id.editTextTextPersonName);
        nomEntrepriseE.setText(intentp.getStringExtra("udNomEntreprise"));

        EditText adresseE = findViewById(R.id.editTextTextPersonName2);
        adresseE.setText(intentp.getStringExtra("udAdresse"));

        EditText NomDepartementE = findViewById(R.id.editTextTextPostalAddress);
        NomDepartementE.setText(intentp.getStringExtra("udNomServDept"));

        EditText Nom1E = findViewById(R.id.editTextTextPersonName3);
        Nom1E.setText(intentp.getStringExtra("udNom"));

        EditText telephone1E = findViewById(R.id.editTextPhone2);
        telephone1E.setText(intentp.getStringExtra("udTel"));

        EditText siretE = findViewById(R.id.editTextNumber);
        siretE.setText(intentp.getStringExtra("udSiret"));

        EditText sousServiceE = findViewById(R.id.editTextTextPersonName5);
        sousServiceE.setText(intentp.getStringExtra("udNomSousSD"));

        EditText nom2E = findViewById(R.id.editTextTextPersonName6);
        nom2E.setText(intentp.getStringExtra("udNom2"));

        EditText email2E = findViewById(R.id.editTextTextPersonName7);
        email2E.setText(intentp.getStringExtra("udEmail2"));

        EditText tel2E = findViewById(R.id.editTextTextPersonName8);
        tel2E.setText(intentp.getStringExtra("udTel2"));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email1E.getText().toString();
                String nomEntreprise = nomEntrepriseE.getText().toString();
                String adresse = adresseE.getText().toString();
                String NomDepartement = NomDepartementE.getText().toString();
                String Nom1 = Nom1E.getText().toString();
                String telephone1 = telephone1E.getText().toString();
                String siret = siretE.getText().toString();
                String sousService = sousServiceE.getText().toString();
                String nom2 = nom2E.getText().toString();
                String email2 = email2E.getText().toString();
                String tel2 = tel2E.getText().toString();

                if (nomEntreprise.isEmpty() || sousService.isEmpty() || NomDepartement.isEmpty() || siret.isEmpty() || Nom1.isEmpty() || email1.isEmpty() || telephone1.isEmpty() || adresse.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Les champs obligatoires ne sont pas tous remplis", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseUpdateCreate<EditProfileCo> dbCo = new DatabaseUpdateCreate(EditProfileCo.this, 0);
                    dbCo.setContext(getApplicationContext());
                    String USERid = getInfoTokenID();
                    String SQL = "UPDATE interima.employeur SET nomEntreprise = '"+nomEntreprise+"', nomServDept = '"+NomDepartement+"', nomSousSD = '"+sousService+"', siret = '"+siret+"', nomC1 = '"+Nom1+"', nomC2 = '"+nom2+"', emailC1 = '"+email1+"', emailC2 = '"+email2+"', telC1 = '"+telephone1+"', telC2 = '"+tel2+"', adresse = '"+adresse+"' WHERE idUti = "+USERid+";";
                    Log.d("EditProfile.java", "Requete :" + SQL);
                    dbCo.setRequete(SQL);
                    dbCo.execute("");

                    Toast.makeText(getApplicationContext(), "Vos informations ont bien été modifiées!", Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intenth = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intenth);
                        return true;
                    case R.id.favoris:
                        if(((EditProfileCo)menu.getContext()).getInfoTokenID() != null) {
                            Intent intentf = new Intent(getApplicationContext(), Bookmarks.class);
                            startActivity(intentf);
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Connectez-vous pour " +
                                    "accéder à cette fonctionnalité.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.recherche:
                        Intent intents = new Intent(getApplicationContext(), RecherchePage.class);
                        startActivity(intents);
                        return true;
                    case R.id.notifs:
                        if(((EditProfileCo)menu.getContext()).getInfoTokenID() != null) {
                            Intent intentn = new Intent(getApplicationContext(), Notifications.class);
                            startActivity(intentn);
                            return true;
                        } else {
                            Toast.makeText(getApplicationContext(), "Connectez-vous pour " +
                                    "accéder à cette fonctionnalité.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.profil:
                        Intent intentp = new Intent(getApplicationContext(), ProfilDisplay.class);
                        startActivity(intentp);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    public void onQueryResult() {
        Toast.makeText(getApplicationContext(), "Vos informations ont bien été modifiées!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.getMenu().findItem(R.id.profil).setChecked(true);
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