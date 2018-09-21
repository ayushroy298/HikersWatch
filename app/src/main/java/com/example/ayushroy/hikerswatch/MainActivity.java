package com.example.ayushroy.hikerswatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    Location last;
    TextView lati;
    TextView longi;
    TextView accu;
    TextView alti;
    TextView addr;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        lati=findViewById(R.id.latitudeTextView);
        longi=findViewById(R.id.longitudeTextView);
        accu=findViewById(R.id.accuracyTextView);
        alti=findViewById(R.id.altitudeTextView);
        addr=findViewById(R.id.addressTextView);


        locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                if(location==null)
                    location=last;
                try {
                    if (location.getLatitude() != last.getLatitude() || location.getLongitude() != last.getLongitude() || location.getAltitude() != last.getAltitude()) {
                        Double l = location.getLatitude();
                        lati.setText("Latitude\n" + l.toString());
                        l = location.getLongitude();
                        longi.setText("Longitude\n" + l.toString());
                        float f = location.getAccuracy();
                        accu.setText("Accuracy\n" + Float.toString(f));
                        l = location.getAltitude();
                        alti.setText("Altitude\n" + l.toString());

                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        try {
                            List<Address> listAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (listAddress != null && listAddress.size() > 0) {
                                String address = "";
                                if (listAddress.get(0).getFeatureName() != null)
                                    address += listAddress.get(0).getFeatureName() + "\n";
                                if (listAddress.get(0).getLocality() != null)
                                    address += listAddress.get(0).getLocality() + "\n";
                                if (listAddress.get(0).getSubAdminArea() != null)
                                    address += listAddress.get(0).getSubAdminArea() + "\n";
                                if (listAddress.get(0).getAdminArea() != null)
                                    address += listAddress.get(0).getAdminArea() + "\n";
                                if (listAddress.get(0).getPostalCode() != null)
                                    address += listAddress.get(0).getPostalCode() + "\n";
                                if (listAddress.get(0).getCountryName() != null)
                                    address += listAddress.get(0).getCountryName() + "\n";
                                Log.i("Chacha", address);
                                addr.setText("Address\n" + address);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    last = location;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            last = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Double l = last.getLatitude();
            lati.setText("Latitude\n" + l.toString());
            l = last.getLongitude();
            longi.setText("Longitude\n" + l.toString());
            float f = last.getAccuracy();
            accu.setText("Accuracy\n" + Float.toString(f));
            l = last.getAltitude();
            alti.setText("Altitude\n" + l.toString());

            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                    List<Address> listAddress = geocoder.getFromLocation(last.getLatitude(), last.getLongitude(), 1);
                    if (listAddress != null && listAddress.size() > 0) {
                        String address = "";
                        if (listAddress.get(0).getFeatureName() != null)
                            address += listAddress.get(0).getFeatureName() + "\n";
                        if (listAddress.get(0).getLocality() != null)
                            address += listAddress.get(0).getLocality() + "\n";
                        if (listAddress.get(0).getSubAdminArea() != null)
                            address += listAddress.get(0).getSubAdminArea() + "\n";
                        if (listAddress.get(0).getAdminArea() != null)
                            address += listAddress.get(0).getAdminArea() + "\n";
                        if (listAddress.get(0).getPostalCode() != null)
                            address += listAddress.get(0).getPostalCode() + "\n";
                        if (listAddress.get(0).getCountryName() != null)
                            address += listAddress.get(0).getCountryName() + "\n";
                        Log.i("Chacha", address);
                        addr.setText("Address\n" + address);

                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
