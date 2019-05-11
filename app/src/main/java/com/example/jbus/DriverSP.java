package com.example.jbus;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbus.model.Driver;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DriverSP extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferranceDrivers;
    private DatabaseReference FireButton;

    TextView feed;
    Switch swit;
   // String ID;
    String online ;
    String temp;
    Button emr, help;
    int x;
    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver ==null)
        {
            broadcastReceiver= new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                  //  Toast.makeText(context,"\n" +intent.getExtras().get("Lag")+"\n"+intent.getExtras().get("Lan") ,Toast.LENGTH_SHORT).show();

    String stringLag= String.valueOf(intent.getExtras().get("Lag"));
    String stringLan= String.valueOf(intent.getExtras().get("Lan"));
    // ID=getIntent().getStringExtra("ID");
     temp=getIntent().getStringExtra("number");
     x=Integer.parseInt(temp)+1;
      mReferranceDrivers=mDatabase.getReference("driver");
    mReferranceDrivers.child("driver"+x).child("lag").setValue(stringLag);
    mReferranceDrivers.child("driver"+x).child("lan").setValue(stringLan);
    mReferranceDrivers.child("driver"+x).child("online").setValue(online);
                    swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked == true)
                            {
                                online="1";
                                mReferranceDrivers.child("driver"+x).child("online").setValue(online);

                            }
                            else
                            {
                                online="0";
                                mReferranceDrivers.child("driver"+x).child("online").setValue(online);
                                mReferranceDrivers.child("driver"+x).child("lag").setValue("0");
                                mReferranceDrivers.child("driver"+x).child("lan").setValue("0");
                                Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                                stopService(i);
                            }
                        }
                    });

                }
            };

            registerReceiver(broadcastReceiver,new IntentFilter("location_update"));

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(broadcastReceiver != null)
        {
            unregisterReceiver(broadcastReceiver);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sp);

        emr=findViewById(R.id.EME);
        help=findViewById(R.id.help);
        emr.setOnClickListener(this);
        help.setOnClickListener(this);

        //Define FireBase
        mDatabase= FirebaseDatabase.getInstance();


        swit = findViewById(R.id.swt);

        
        if(!runtime_per())
            enable_buttons();
    }


    private void enable_buttons() {

         swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if(isChecked == true)
                 {
                        online="1";
                     Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                     startService(i);
                 }
                 else
                     {
                         online="0";
                         Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                         stopService(i);
                         mReferranceDrivers.child("driver"+x).child("lag").setValue("");
                         mReferranceDrivers.child("driver"+x).child("lan").setValue("");
                     }
             }
         });



    }

    private boolean runtime_per() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
            return true;
        }
            return false;


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==100)
        { if(grantResults[0]==PackageManager.PERMISSION_GRANTED  && grantResults[1]== PackageManager.PERMISSION_GRANTED)
                enable_buttons();

          else {
              runtime_per();
        }
        }

    }

    @Override
    public void onClick(View v) {
        temp=getIntent().getStringExtra("number");
        x=Integer.parseInt(temp)+1;
        switch(v.getId()){

            case R.id.EME:
                FireButton=mDatabase.getReference("Problem");
                FireButton.child("EMR").setValue("Driver"+x+" Emergency Call ");

                break;

            case R.id.help:
                FireButton=mDatabase.getReference("Problem");
                FireButton.child("HELP").setValue("Driver"+x+" Is Needing help");

                break;


        }



    }
}