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

import java.io.Serializable;
import java.util.ArrayList;

import android.widget.TextView;
import android.widget.Toast;

class ViewHolder {
    TextView title;
    TextView company;
    Button applyButton;
}

public class MainActivity extends AppCompatActivity implements Serializable {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private ArrayList<Offer> offers = new ArrayList<Offer>();
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocalisation);
        setContentView(R.layout.activity_main);

        //Requête de la permission COARSE LOCATION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            //Toast.makeText(this, "Permission not granted yet", Toast.LENGTH_SHORT).show();
            // Permission is not granted
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);


        }
        else //Permission granted
        {
            Toast.makeText(this, "Permission granted already", Toast.LENGTH_SHORT).show();
        }

        for (int i = 0; i < 10; i++) {
            offers.add(new Offer(1, "Developpeur Fullstack", "capgemini.com"));
        }

        RecyclerView recyclerView = findViewById(R.id.offersList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        OfferAdapter adapter = new OfferAdapter(offers);
        recyclerView.setAdapter(adapter);

        // sign in button
        Button sign_in = findViewById(R.id.login_button);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignIn.class);
                view.getContext().startActivity(intent);
            }
        });

        // notifications button

        ImageButton notifications = findViewById(R.id.notification);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Notifications.class);
                view.getContext().startActivity(intent);
            }
        });

        // search bar

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