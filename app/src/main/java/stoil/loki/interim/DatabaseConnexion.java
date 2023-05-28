package stoil.loki.interim;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnexion<T, U> extends AsyncTask<String, Void, String> {

    private static final String url = "jdbc:mysql://interima.ddns.net:11006/interima";
    private static final String user = "dev_user";
    private static final String password = "dev_user_password";

    private String requete;

    private Context context;

    private String res = "";

    private T callingActivity;

    private U otherActivity;

    public DatabaseConnexion(T callingActivity, U otherActivity) {
        this.callingActivity = callingActivity;
        this.otherActivity = otherActivity;
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
//            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                res += rs.getString(1).toString() + "\n";
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

            if (callingActivity instanceof SignIn) {
                ((SignIn) callingActivity).onQueryResult(result);
            } else if (callingActivity instanceof SessionManager) {
                ((SessionManager) callingActivity).onQueryResult(result);
            } else if (callingActivity instanceof OfferAdapter) {
                ((OfferAdapter) callingActivity).bookmarkOnOff(result);
            }
        }
        res = result;
    }
}
