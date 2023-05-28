package stoil.loki.interim;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateOffer extends AppCompatActivity {

    private Offer offer;
    //Intent intent = new Intent(this, MainActivity.class);
    private DatabaseUpdateCreate<CreateOffer> dbCo;

    String titre, description, datedeb, datefin, urlsource, urlimg;
    float salaire_H; float geolong; float geolat;
    String[] motscles;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_offer);

        Button buttonDebut = findViewById(R.id.buttDebut);
        Button buttonFin = findViewById(R.id.buttFin);
        Button submit = findViewById(R.id.submit);

        buttonDebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Calendar instance to get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog and set the initial date to the current date
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the TextView
                        TextView dateDebutTextView = findViewById(R.id.dateDebut);
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        dateDebutTextView.setText(selectedDate);
                        dateDebutTextView.setTextColor(Color.BLACK);
                    }
                }, year, month, dayOfMonth);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

        buttonFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Calendar instance to get the current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog and set the initial date to the current date
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the TextView
                        TextView dateDebutTextView = findViewById(R.id.dateFin);
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        dateDebutTextView.setText(selectedDate);
                        dateDebutTextView.setTextColor(Color.BLACK);
                    }
                }, year, month, dayOfMonth);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });

        //TODO: OCEANE FAIS LA REQUETE POUR CREER L'OFFRE D'EMPLOI//

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validForm = false;

                //TODO: Check if form is valid here
                if (true) validForm = true;

                if (validForm)
                {

                    titre = ((EditText) findViewById(R.id.string_title)).getText().toString();
                    description = ((EditText) findViewById(R.id.string_desc)).getText().toString();
                    motscles = ((EditText) findViewById(R.id.string_keywords)).getText().toString().split("\\P{L}+");
                    datedeb = ((TextView) findViewById(R.id.dateDebut)).getText().toString();
                    datefin = ((TextView) findViewById(R.id.dateFin)).getText().toString();
                    urlimg = ((EditText) findViewById(R.id.string_img_url)).getText().toString();
                    urlsource = ((EditText) findViewById(R.id.string_source)).getText().toString();


                    try {
                        salaire_H = Float.parseFloat(((EditText) findViewById(R.id.float_wage)).getText().toString());
                        geolong = Float.parseFloat(((EditText) findViewById(R.id.float_long)).getText().toString());
                        salaire_H = Float.parseFloat(((EditText) findViewById(R.id.float_lat)).getText().toString());
                    } catch (NumberFormatException e) {
                        //Do nothing lol
                        //throw new RuntimeException(e);
                    }

                    //TODO: OCEANE FAIS LA REQUETE POUR CREER L'OFFRE D'EMPLOI//
                    SQLSubmit();
                }

                //Go back to Main//
                //startActivity(intent);


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


    private void SQLSubmit() {
        Log.d("SignIn.java", "Starting connection from CreateOffer.java");
        dbCo = new DatabaseUpdateCreate<>(CreateOffer.this, true);
        dbCo.setContext(getApplicationContext());

        String SQL;
        //SQL = "SELECT mdp FROM interima.utilisateur WHERE id IN (SELECT idUti FROM interima.gestionnaire WHERE email = '"+email.getText().toString()+"');";
        SQL = "INSERT INTO offre (idEmp, titre, publication, fermeture, debut, fin, url, salaire, geolat, geolong, img, description) values ('"+getInfoTokenID()+"', '"+titre+"', '2023-05-01', '2023-06-02', '"+datedeb+"', '"+datefin+"', '"+urlsource+"', '77.5', '"+geolat+"', '"+geolong+"', '"+urlimg+"', '"+description+"');";


        Log.d("CreateOffer.java", "Requete :" + SQL);

        dbCo.setRequete(SQL);
        dbCo.execute("");

    }


    public void onQueryResult(String result) {
        // Traitez le résultat de la requête ici
        //Log.d("SignIn", "Résultat de la requête : " + result + " pw = " + pw.getText().toString() + " res = " + pw.getText().toString().trim().equals(result.trim()));
        String res = result;
        if (res!="1")
        {
             System.out.println("Successful insertion to database");
             Intent intent = new Intent(getApplicationContext(), MainActivity.class);
             startActivity(intent);
             finish();

        }
        else System.out.println("Something went wrong.");
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