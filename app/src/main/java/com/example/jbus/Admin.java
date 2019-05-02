package com.example.jbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import android.support.v7.widget.Toolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin extends AppCompatActivity implements View.OnClickListener {


    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferranceDrivers;
    private Toolbar toolbar;

    EditText Lng,lat,id,pwd;
    Button Loc,Dri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);




        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       Lng=findViewById(R.id.lng);
       lat=findViewById(R.id.lat);
       id=findViewById(R.id.DID);
       pwd=findViewById(R.id.DPwd);
       Loc=findViewById(R.id.AddLoc);
       Dri=findViewById(R.id.AddDri);


       Dri.setOnClickListener(this);
       Loc.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.AddDri:
                String Did,Dpwd;
                mDatabase= FirebaseDatabase.getInstance();
                mReferranceDrivers=mDatabase.getReference("driver");

                Did=id.getText().toString();
                Dpwd=pwd.getText().toString();

                mReferranceDrivers.child("driver3").child("id").setValue(Did);
                mReferranceDrivers.child("driver3").child("pass").setValue(Dpwd);




                break;

            case R.id.AddLoc:
                String l1,l2;
                mDatabase= FirebaseDatabase.getInstance();
                mReferranceDrivers=mDatabase.getReference("Locations");
                l1=Lng.getText().toString();
                l2=lat.getText().toString();
                mReferranceDrivers.child("AddedLocation").child("lag").setValue(l1);
                mReferranceDrivers.child("AddedLocation").child("lan").setValue(l2);
                break;

        }



    }
}
