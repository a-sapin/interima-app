package stoil.loki.interim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UploadOffer extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private String handledfile;
    private String handledfile_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_offer);

        Button select = findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileBrowser();
            }
        });

        Button upload = findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView jsoncontent = (TextView) findViewById(R.id.jsoncontent);
                String toread = jsoncontent.getText().toString();
                try {
                    JSONObject offerinfo = new JSONObject(toread);
                    if(jsonFieldChecker(offerinfo)) {
                        String titre = offerinfo.getString("titre");
                        String debut = offerinfo.getString("debut");
                        String fin = offerinfo.getString("fin");
                        String source = offerinfo.getString("source");
                        float salaire = (float) offerinfo.getDouble("salaire");
                        double longitude = offerinfo.getDouble("longitude");
                        double latitude = offerinfo.getDouble("latitude");
                        String image = offerinfo.getString("image");
                        String description = offerinfo.getString("description");
                        if(isDateFormatCorrect(debut) && isDateFormatCorrect(fin)) {
                            DatabaseUpdateCreate<UploadOffer> dbCo = new DatabaseUpdateCreate<>(UploadOffer.this, 1);
                            dbCo.setContext(getApplicationContext());
                            String SQL = "INSERT INTO offre (idEmp, titre, publication, fermeture, debut, fin, url, salaire, geolat, geolong, img, description) values ('"+getInfoTokenID()+"', '"+titre+"', '2023-05-01', '2023-06-02', '"+debut+"', '"+fin+"', '"+source+"', '"+salaire+"', '"+latitude+"', '"+longitude+"', '"+image+"', '"+description+"');";
                            Log.d("UploadOffer.java", "Requete :" + SQL);
                            dbCo.setRequete(SQL);
                            dbCo.execute("");
                        } else {
                            Toast.makeText(UploadOffer.this, "Format de date invalide", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UploadOffer.this, "Format de fichier JSON invalide", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(UploadOffer.this, "Une erreur est survenue lors de l'envoi.", Toast.LENGTH_SHORT).show();
                    Log.e("UploadOffer.upload.onClick", "JSONException");
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
                        if(((UploadOffer)menu.getContext()).getInfoTokenID() != null) {
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
                        if(((UploadOffer)menu.getContext()).getInfoTokenID() != null) {
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
        Toast.makeText(UploadOffer.this, "Annonce publiée", Toast.LENGTH_SHORT).show();
        Intent intentf = new Intent(getApplicationContext(), Bookmarks.class);
        startActivity(intentf);
        finish();
    }

    private void openFileBrowser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/json");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
                Cursor returnCursor =
                        getContentResolver().query(uri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                this.handledfile = returnCursor.getString(nameIndex);
                TextView jsoncontent = (TextView) findViewById(R.id.jsoncontent);
                jsoncontent.setText(stringBuilder.toString());
                this.handledfile_path = trimFilePath(uri.getPath());
                //System.out.println(this.handledfile_path);
            } catch (IOException e) {
                Log.e("UploadOffer.onActivityResult", "IOException");
            }
        }
    }

    private String trimFilePath(String filepath) {
        String trimmed_fp = "";
        Pattern p = Pattern.compile(":(.+)");
        Matcher m = p.matcher(filepath);
        while(m.find()) {
            trimmed_fp = m.group(1);
        }
        return trimmed_fp;
    }

    private boolean isDateFormatCorrect(String date) {
        return Pattern.matches("\\d{4}-\\d{2}-\\d{2}", date);
    }

    private boolean jsonFieldChecker(JSONObject file) {
        return file.has("titre") && file.has("debut") && file.has("fin")
                && file.has("source") && file.has("salaire")
                && file.has("longitude") && file.has("latitude")
                && file.has("image") && file.has("description");
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