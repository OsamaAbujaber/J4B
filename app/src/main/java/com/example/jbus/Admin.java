package com.example.jbus;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin extends AppCompatActivity implements View.OnClickListener {


    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferranceDrivers;
    private Toolbar toolbar;

    EditText Lng, lat, id, pwd;
    Button Loc, Dri, current;
    int driversNumber;

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        String temp= getIntent().getExtras().get("id").toString();
        driversNumber= Integer.parseInt(temp);





        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Lng = findViewById(R.id.lng);
        lat = findViewById(R.id.lat);
        id = findViewById(R.id.DID);
        pwd = findViewById(R.id.DPwd);
        Loc = findViewById(R.id.AddLoc);
        Dri = findViewById(R.id.AddDri);
        current = findViewById(R.id.Current);


        Dri.setOnClickListener(this);
        Loc.setOnClickListener(this);
        current.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.AddDri:
                String Did, Dpwd;
                mDatabase = FirebaseDatabase.getInstance();
                mReferranceDrivers = mDatabase.getReference("driver");

                Did = id.getText().toString();
                Dpwd = pwd.getText().toString();
                driversNumber+=1;
                mReferranceDrivers.child("driver"+driversNumber).child("id").setValue(Did);
                mReferranceDrivers.child("driver"+driversNumber).child("pass").setValue(Dpwd);
                mReferranceDrivers.child("driver"+driversNumber).child("lag").setValue("0");
                mReferranceDrivers.child("driver"+driversNumber).child("lan").setValue("0");
                mReferranceDrivers.child("driver"+driversNumber).child("online").setValue("0");

                break;

            case R.id.AddLoc:
                String l1, l2;
                mDatabase = FirebaseDatabase.getInstance();
                mReferranceDrivers = mDatabase.getReference("Locations");
                l1 = Lng.getText().toString();
                l2 = lat.getText().toString();
                mReferranceDrivers.child("AddedLocation").child("lag").setValue(l1);
                mReferranceDrivers.child("AddedLocation").child("lan").setValue(l2);
                break;


            case R.id.Current:

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    buildAlertMessageNoGps();

                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    getLocation();

                }
                break;

        }

    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(Admin.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Admin.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Admin.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Lng.setText(longitude);
                lat.setText(lattitude);


            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Lng.setText(longitude);
                lat.setText(lattitude);


            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Lng.setText(longitude);
                lat.setText(lattitude);


            } else {

               Toast.makeText(this, "Unable to Trace your location", Toast.LENGTH_SHORT).show();

            }
        }


    }



    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}





