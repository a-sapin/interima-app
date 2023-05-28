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

public class ListingCandidatureData<T> extends AsyncTask<String, Void, String> {

    private static final String url = "jdbc:mysql://interima.ddns.net:11006/interima?user=root&useUnicode=true&characterEncoding=utf8";
    private static final String user = "dev_user";
    private static final String password = "dev_user_password";

    private String requete;

    private Context context;

    private String res = "";

    private ArrayList<CandidatureData> candidatures = new ArrayList<>();

    private T callingActivity;

    public ListingCandidatureData(T callingActivity) {
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
            Log.d("DatabaseConnexion.java", "requete : " + this.requete);

            ResultSet rs = st.executeQuery(this.requete);

            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();

            // Parcours des lignes
            while (rs.next()) {
                // Parcours des colonnes
                    int id = rs.getInt("id");
                    int idUti = rs.getInt("idUti");
                    String lienCV = rs.getString("lienCV");
                    String lienLM = rs.getString("lienLM");
                    String commentaires = rs.getString("commentaires");
                    String statut = rs.getString("statut");

                    CandidatureData cd = new CandidatureData(id, idUti, 0, lienCV, lienLM, commentaires, statut, "");
                    candidatures.add(cd);

            }
            for(int i = 0; i < candidatures.size(); i++) {
                Log.d("DatabaseConnexion.java", "candidature : " + candidatures.get(i).getId());
            }

            rs.close();
            st.close();

            Connection coDb2 = DriverManager.getConnection(url, user, password);
            Statement st2 = coDb2.createStatement();

            for(CandidatureData cd : candidatures) {
                String SQL = "SELECT id, titre from interima.offre WHERE id = (SELECT idOffre from interima.candidatureoffre WHERE idCandidature="+cd.getId()+");";
                Log.d("DatabaseConnexion.java", "requete : " + SQL);
                ResultSet rs2 = st2.executeQuery(SQL);
                while(rs2.next()) {
                    System.out.println(rs2.getInt("id"));
                    cd.setIdOffre(rs2.getInt("id"));
                    cd.setOffertitle(rs2.getString("titre"));
                }
                rs2.close();
            }
            st2.close();

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
            if (callingActivity instanceof ApplyListDisplay) {
                Log.d("ListingCandidatureData.java", "Requete ok + passage a ApplyListDisplay");
                ((ApplyListDisplay) callingActivity).onQueryResult(candidatures);
            } else if (callingActivity instanceof ApplyReuseDisplay) {
                Log.d("ListingCandidatureData.java", "Requete ok + passage a ApplyReuseDisplay");
                ((ApplyReuseDisplay) callingActivity).onQueryResult(candidatures);
            }
        }
        res = result;
    }
}
