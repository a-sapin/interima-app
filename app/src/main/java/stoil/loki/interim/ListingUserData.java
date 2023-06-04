package stoil.loki.interim;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class ListingUserData<T> extends AsyncTask<String, Void, String> {

    private static final String url = "jdbc:mysql://interima.ddns.net:11006/interima?user=root&useUnicode=true&characterEncoding=utf8";
    private static final String user = "dev_user";
    private static final String password = "dev_user_password";

    private String requete;

    private Context context;

    private String res = "";

    private UserData userdata;
    private String role;

    private T callingActivity;

    public ListingUserData(T callingActivity, String role) {
        this.callingActivity = callingActivity;
        this.role = role;
    }


    public String getRes() {
        return this.res;
    }

    public void setRequete(String SQL) {
        this.requete = SQL;
    }

    public String getRequete() {
        return this.requete;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        Toast.makeText(context, "Connexion ... ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection coDb = DriverManager.getConnection(url, user, password);

            Log.d("DatabaseConnexion.java", "Connexion ok");

            Statement st = coDb.createStatement();
            Log.d("DatabaseConnexion.java", "requete : " + this.requete);

            ResultSet rs = st.executeQuery(this.requete);

            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();

            if(role.equals("Chercheur d'emploi")) {
                while(rs.next()) {
                    String nom = rs.getString("nom");
                    String prenom = rs.getString("prenom");
                    String email = rs.getString("email");
                    String nat = rs.getString("nationalite");
                    String tel = rs.getString("tel");
                    this.userdata = new UserData(nom, prenom, email, nat, tel);
                }
            }
            if(role.equals("Employeur")) {
                while(rs.next()) {
                    String nomEntreprise = rs.getString("nomEntreprise");
                    String nomServDept = rs.getString("nomServDept");
                    String nomSousSD = rs.getString("nomSousSD");
                    String siret = rs.getString("siret");
                    String nomC1 = rs.getString("nomC1");
                    String emailC1 = rs.getString("emailC1");
                    String telC1 = rs.getString("telC1");
                    String nomC2 = rs.getString("nomC2");
                    String emailC2 = rs.getString("emailC2");
                    String telC2 = rs.getString("telC2");
                    String adresse = rs.getString("adresse");
                    this.userdata = new UserData(nomC1, emailC1, telC1, nomServDept, nomSousSD, siret, nomC2, emailC2, telC2, adresse, nomEntreprise);
                }
            }
            if(role.equals("Agence d'intérim")) {
                while(rs.next()) {
                    String nomEntreprise = rs.getString("nomAgence");
                    String siret = rs.getString("siret");
                    String nomC1 = rs.getString("nomC1");
                    String emailC1 = rs.getString("emailC1");
                    String telC1 = rs.getString("telC1");
                    String nomC2 = rs.getString("nomC2");
                    String emailC2 = rs.getString("emailC2");
                    String telC2 = rs.getString("telC2");
                    String adresse = rs.getString("adresse");
                    this.userdata = new UserData(nomC1, emailC1, telC1, siret, nomC2, emailC2, telC2, adresse, nomEntreprise);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            res = e.toString();
        }

        return res;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (callingActivity != null) {
            if (callingActivity instanceof ProfilDisplay) {
                Log.d("ListingUserData.java", "Requete ok + passage a ProfilDisplay");
                ((ProfilDisplay) callingActivity).onQueryResult(this.userdata);
            }
        }
        res = result;
    }
}
