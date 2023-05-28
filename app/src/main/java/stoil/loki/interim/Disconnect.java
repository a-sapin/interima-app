package stoil.loki.interim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Disconnect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disconnect);

        clearToken();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void clearToken() {
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("User DATA", Context.MODE_PRIVATE).edit();
        editor.remove("role");
        editor.remove("id");
        return;
    }

}