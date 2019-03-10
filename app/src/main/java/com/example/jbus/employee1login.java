package com.example.jbus;

import android.content.Intent;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbus.model.Driver;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class employee1login extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferranceDrivers;
    private List<Driver> driversArray ;
  


    TextView t,T_id,T_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To view it as full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_employee1login);

        //Define FireBase
        mDatabase= FirebaseDatabase.getInstance();
        mReferranceDrivers=mDatabase.getReference("driver");
        driversArray = new ArrayList<>();
        readDrivers();
        // Define ID's
        t=  findViewById(R.id.vi);
        T_id=  findViewById(R.id.t_id);
        T_pwd=  findViewById(R.id.t_pwd);
        Button Login= findViewById(R.id.login);

        //Get All Users ID and Password From FireBase


        // Listener for login button
        Login.setOnClickListener(this);



    }

    public void readDrivers()
    {


        mReferranceDrivers.addChildEventListener(new ChildEventListener() {
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


    @Override
    public void onClick(View v) {


        

        
        String id_d = T_id.getText().toString().trim();
        String pass_d=T_pwd.getText().toString().trim();


        if(id_d.isEmpty() || pass_d.isEmpty()) 
        {

            Toast.makeText(employee1login.this, "fill all failds", Toast.LENGTH_SHORT).show();

        }

        
        else
        {

            boolean flag =false;

            for (Driver d1 : driversArray)
            {
                if(d1.getId().equals(id_d)&&d1.getPass().equals(pass_d))
                {
                    flag = true;
                    break;
                }

            }


            if(flag)
            {

                 startActivity(new Intent(employee1login.this,MapsActivity.class));
            }
            else
            {
                Toast.makeText(this, "wrong id or pass", Toast.LENGTH_SHORT).show();
            }

        }





    }

    }

