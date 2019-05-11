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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbus.model.Driver;
import com.example.jbus.model.admin;
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
    private DatabaseReference dmReferranceDrivers;
    private List<Driver> driversArray ;
    private List<admin> adminArray ;
    String num;

    Intent i;
    String id;
    TextView T_id,T_pwd;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_employee1login);


        animation= AnimationUtils.loadAnimation(this,R.anim.frombottom);

        Button Login= findViewById(R.id.login);
        Login.setAnimation(animation);

        //Define FireBase
        mDatabase= FirebaseDatabase.getInstance();
        mReferranceDrivers=mDatabase.getReference("driver");
        driversArray = new ArrayList<>();

        dmReferranceDrivers=mDatabase.getReference("admin");
        adminArray = new ArrayList<>();



        T_id=  findViewById(R.id.t_id);
        T_pwd=  findViewById(R.id.t_pwd);
        read();

        //Get All Users ID and Password From FireBase
        // Listener for login button
        Login.setOnClickListener(this);
    }

    public void read()
    {
        dmReferranceDrivers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                admin admin0=dataSnapshot.getValue(admin.class);
                adminArray.add(admin0);


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
        boolean flag =false;
        String id_d = T_id.getText().toString().trim();
        String pass_d=T_pwd.getText().toString().trim();


        if(id_d.isEmpty() || pass_d.isEmpty()) 
        {
            Toast.makeText(employee1login.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
        }

        else
        {
            for (admin d1 : adminArray)
            {
                Toast.makeText(this, adminArray.get(0).getId(), Toast.LENGTH_SHORT).show();
                if(d1.getId().equals(id_d)&&d1.getPass().equals(pass_d))
                {

                    i=new Intent(employee1login.this,DriverList.class);
                    i.putExtra("id",driversArray.size() );
                    startActivity(i);
                    break;
                }   }
            for (int x=0;x<driversArray.size();x++)
            {
                Driver d1=driversArray.get(x);
                if(d1.getId().equals(id_d)&&d1.getPass().equals(pass_d))
                {
                    flag = true;
                    id=d1.getId();
                    num=x+"";
                    break;
                }
            }
            if(flag){
                i= new Intent(employee1login.this,DriverSP.class);
                 i.putExtra("ID",id);
                 i.putExtra("number",num);
                startActivity(i);
            }
            else if(flag)

                        Toast.makeText(this, "wrong id or pass", Toast.LENGTH_SHORT).show();

        }
    }

    }

