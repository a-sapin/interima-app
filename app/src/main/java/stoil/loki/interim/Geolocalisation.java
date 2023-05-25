package stoil.loki.interim;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import android.widget.TextView;
import android.widget.Toast;


public class Geolocalisation extends AppCompatActivity implements Serializable {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.geolocalisation);

            Button next = findViewById(R.id.geoScreenNext);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


            //Requête de la permission COARSE LOCATION
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission non accordée", Toast.LENGTH_SHORT).show();
                //Permission is not granted
                // Request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else //Permission granted
            {
                Toast.makeText(this, "Permission déjà accordée", Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Location permission granted, do your work here
                } else {
                    // Location permission denied
                }
                break;
            // Handle other permissions here if needed
        }

    }
}