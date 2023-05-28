package stoil.loki.interim;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class SessionManager<T extends Activity, U extends Activity> {
    private static final int ID_Utilisateur = -1;

    private static final String KEY_ROLE= "role";
    private static final String KEY_ID= "id";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private T activity;

    public SessionManager(Context context, String email, String role, String mdp, T activity) {
        this.context = context;
        DatabaseConnexion<SessionManager, T> dbCo = new DatabaseConnexion<>(this, activity);
        dbCo.setContext(context);

        // requete de recuperation de l id de l utilisateur
        String SQL = "";

        if (role.equals("Gestionnaire")){
            SQL = "SELECT id FROM interima.utilisateur WHERE id = (SELECT idUti FROM interima.gestionnaire WHERE email = '"+email+"' AND mdp = '"+mdp+"');";

        } else if (role.equals("Chercheur d'emploi")) {
            SQL = "SELECT id FROM interima.utilisateur WHERE id = (SELECT idUti FROM interima.chercheuremploi WHERE email = '"+email+"' AND mdp = '"+mdp+"');";

        } else if (role.equals("Employeur")) {
            SQL = "SELECT id FROM interima.utilisateur WHERE id IN (SELECT idUti FROM interima.employeur WHERE emailC1 = '"+email+"' AND mdp = '"+mdp+"');";

        } else if (role.equals("Agence d'intérim")) {
            SQL = "SELECT id FROM interima.utilisateur WHERE id IN (SELECT idUti FROM interima.agenceinterim WHERE emailC1 = '"+email+"' AND mdp = '"+mdp+"');";
        }

        dbCo.setRequete(SQL);
        dbCo.execute("");

        sharedPreferences = context.getSharedPreferences("User DATA", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    public void onQueryResult(String result) {
        result=result.replaceAll("\\s", ""); //Deleting spaces\\
        setTokenID(result);
    }

    public void setTokenID(String token) {
        editor.putString(KEY_ID, token);
        editor.apply();
    }
    public void setTokenRole(String token) {
        editor.putString(KEY_ROLE, token);
        editor.apply();
    }

    public String getTokenID() {
        return sharedPreferences.getString(KEY_ID, null);
    }

    public String getTokenRole() {
        return sharedPreferences.getString(KEY_ROLE, null);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = context.getSharedPreferences("User DATA", Context.MODE_PRIVATE).edit();
        editor.remove("role");
        editor.remove("id");
        editor.apply();
        return;
    }
}
