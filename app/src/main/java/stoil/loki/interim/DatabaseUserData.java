package stoil.loki.interim;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseUserData<T> extends AsyncTask<String, Void, String>  {

    private static final String url = "jdbc:mysql://interima.ddns.net:11006/interima";
    private static final String user = "dev_user";
    private static final String password = "dev_user_password";

    private String requete;

    private Context context;

    private String res = "";

    private ArrayList<String> userData = new ArrayList<>();

    private ArrayList<String> candidatureTitle = new ArrayList<>();

    private T callingActivity;

    public DatabaseUserData(T callingActivity) {
        this.callingActivity = callingActivity;
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

    private String encodeString(String text) {
        try {
            byte[] utf8Bytes = text.getBytes("ISO-8859-1");
            return new String(utf8Bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return text;
        }
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
//            ResultSetMetaData rsmd = rs.getMetaData();

            int idUti = 0;

            while (rs.next()) {
                idUti = rs.getInt("idUti");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String nat = rs.getString("nationalite");
                userData.add(nom);
                userData.add(prenom);
                userData.add(nat);
            }

            rs.close();
            st.close();

            st = coDb.createStatement();
            Log.d("DatabaseConnexion.java", "requete : " + this.requete);

            String SQL = "select titre from interima.offre where id in (select idOffre from interima.candidatureoffre where idCandidature in (select id from interima.candidature where idUti = '"+ idUti +"'));";

            rs = st.executeQuery(SQL);

            while (rs.next()) {
                String titre = rs.getString("titre");
                candidatureTitle.add(encodeString(titre));
            }

            rs.close();
            st.close();

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

            if (callingActivity instanceof Apply) {
                ((Apply) callingActivity).onQueryResult(userData);
            } else if (callingActivity instanceof ApplyReuse) {
                ((ApplyReuse) callingActivity).onQueryResult(userData, candidatureTitle);
            }
        }
        res = result;
    }
}
