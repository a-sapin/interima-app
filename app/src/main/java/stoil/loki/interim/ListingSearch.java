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
import java.util.Date;

public class ListingSearch<T> extends AsyncTask<String, Void, String> {

    private static final String url = "jdbc:mysql://interima.ddns.net:11006/interima?user=root&useUnicode=true&characterEncoding=utf8";
    private static final String user = "dev_user";
    private static final String password = "dev_user_password";

    private String requete;

    private Context context;

    private String res = "";

    private ArrayList<Search> searches = new ArrayList<>();

    private T callingActivity;

    public ListingSearch(T callingActivity) {
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

    @Override
    protected String doInBackground(String... params) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection coDb = DriverManager.getConnection(url, user, password);

            Log.d("DatabaseConnexion.java", "Connexion ok");

            Statement st = coDb.createStatement();
            //String SQL = "Select * from interima.offre;";
            //this.requete = SQL;
            Log.d("DatabaseConnexion.java", "requete : " + this.requete);

            ResultSet rs = st.executeQuery(this.requete);

            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();

            // Parcours des lignes
            while (rs.next()) {
                // Parcours des colonnes
                    int id = rs.getInt("id");
                    int idUti = rs.getInt("idUti");
                    String titre = rs.getString("metier");
                    Date date_recherche = rs.getDate("dateRecherche");

                    Search ss = new Search(id, idUti, titre, date_recherche);
                    searches.add(ss);

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
            if (callingActivity instanceof SavedSearchDisplay) {
                Log.d("ListingSearch.java", "Requete ok + passage a savedsearchdisplay");
                ((SavedSearchDisplay) callingActivity).onQueryResult(searches);
            }
        }
        res = result;
    }
}
