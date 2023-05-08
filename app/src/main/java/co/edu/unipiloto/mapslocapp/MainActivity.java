package co.edu.unipiloto.mapslocapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btn_location;
    private TextView tv_1, tv_2, tv_3, tv_4, tv_5;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_location = findViewById(R.id.btn_location);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);
        tv_4 = findViewById(R.id.tv_4);
        tv_5 = findViewById(R.id.tv_5);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {

                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            tv_1.setText("Latitud: " + (double) addresses.get(0).getLatitude());
                            tv_2.setText("Longitud: " + (double) addresses.get(0).getLongitude());
                            tv_3.setText("País: " + addresses.get(0).getCountryName());
                            tv_4.setText("Localidad: " + addresses.get(0).getLocality());
                            tv_5.setText("Dirección" + addresses.get(0).getAddressLine(0));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }
}