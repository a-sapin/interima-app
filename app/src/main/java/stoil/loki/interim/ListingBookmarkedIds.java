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

public class ListingBookmarkedIds<T> extends AsyncTask<String, Void, String> {

    private static final String url = "jdbc:mysql://interima.ddns.net:11006/interima?user=root&useUnicode=true&characterEncoding=utf8";
    private static final String user = "dev_user";
    private static final String password = "dev_user_password";

    private String requete;

    private Context context;

    private String res = "";

    private ArrayList<Integer> bookmarked_ids = new ArrayList<>();

    private T callingActivity;

    public ListingBookmarkedIds(T callingActivity) {
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
                    bookmarked_ids.add(id);
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
            if (callingActivity instanceof MainActivity) {
                Log.d("ListingBookmarkedIds.java", "Requete ok + passage a main");
                ((MainActivity) callingActivity).onQueryResult2(bookmarked_ids);
            }
            if (callingActivity instanceof Bookmarks) {
                Log.d("ListingBookmarkedIds.java", "Requete ok + passage a bookmarks");
                ((Bookmarks) callingActivity).onQueryResult2(bookmarked_ids);
            }
            if (callingActivity instanceof RecherchePage) {
                Log.d("ListingBookmarkedIds.java", "Requete ok + passage a recherche");
                ((RecherchePage) callingActivity).onQueryResult2(bookmarked_ids);
            }
        }
        res = result;
    }
}
