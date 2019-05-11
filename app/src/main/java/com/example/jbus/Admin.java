package com.example.jbus;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.jbus.model.Driver;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity implements View.OnClickListener {


    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferranceDrivers;
    private DatabaseReference CounterRef;
    private DatabaseReference Notfi;
    private DatabaseReference DeleteDriver;
    private Toolbar toolbar;

    EditText Lng, lat, id, pwd;
    Button Loc, Dri, current,del;
    int driversNumber;

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude, longitude;


    private List<Driver> driversArray ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

       // String temp= getIntent().getExtras().get("id").toString();
       // driversNumber= Integer.parseInt(temp);


        mDatabase= FirebaseDatabase.getInstance();
        DeleteDriver=mDatabase.getReference("driver");
        driversArray = new ArrayList<>();
        read();

        mDatabase.getReference("Problem").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String notifi= (String) dataSnapshot.getValue();
                notibuild(notifi,s);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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
        del=findViewById(R.id.More);

        Dri.setOnClickListener(this);
        Loc.setOnClickListener(this);
        current.setOnClickListener(this);
        del.setOnClickListener(this);
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

                Driver D=new Driver(Did,Dpwd,"0","0","0");
                driversArray.add(D);

                CounterRef=mDatabase.getReference("Counter");
                CounterRef.child("Numberofdrivers").setValue(driversNumber);

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


            case R.id.More:

                for (int i=0;i<driversArray.size();i++)
                {
                    Driver d1 =driversArray.get(i);
                    if(d1.getId().equals(id.getText().toString())&&d1.getPass().equals(pwd.getText().toString()))
                    {

                        DeleteDriver = mDatabase.getReference("driver").child("driver"+(i+1));
                        DeleteDriver.removeValue();
                        driversNumber--;
                        driversArray.remove(i);
                        CounterRef=mDatabase.getReference("Counter");
                        CounterRef.child("Numberofdrivers").setValue(driversArray.size());
                        break;

                    }


                }



                break;

            /*
            case R.id.Adi:
                NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
                builder.setSmallIcon(R.mipmap.ic_launcher_round);
                builder.setContentTitle("Drivers ");
                builder.setContentText("ffffffffffffffffffffffff");

                Intent intent=new Intent(this,DriverList.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);

                NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0,builder.build());
                Toast.makeText(this, "Fuck you ", Toast.LENGTH_SHORT).show();
                break;*/

        }

    }


    public void read()
    {

        DeleteDriver.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Driver driver = dataSnapshot.getValue(Driver.class);
                driversArray.add(driver);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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


    public void notibuild(String text,String s){
        if(!text.equals("0")) {
            int a = text.indexOf(" ");
            String z = text.substring(0, a);
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(z);
            builder.setMessage(text);
            final AlertDialog alert = builder.create();
            alert.show();

            Notfi = mDatabase.getReference("Problem");
            Notfi.child("EMR").setValue("0");
            Notfi.child("HELP").setValue("0");
        }
    }

}





