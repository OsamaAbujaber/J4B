package com.example.jbus;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.jbus.model.Driver;
import com.example.jbus.model.admin;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListDriver extends AppCompatActivity {
    private RecyclerView listdriveview;
    private List<com.example.jbus.model.Driver> drivirlist;
    private Reyclelistdrivir reyclelistdrivir;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferranceDrivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_driver);
        mDatabase= FirebaseDatabase.getInstance();
        mReferranceDrivers=mDatabase.getReference("driver");
        drivirlist = new ArrayList<>();
        listdriveview = findViewById(R.id.list_driver_view);
        read();
        reyclelistdrivir = new Reyclelistdrivir(drivirlist);
        listdriveview.setLayoutManager(new LinearLayoutManager(this));

        listdriveview.setAdapter(reyclelistdrivir);



    }

    public void read() {


        mReferranceDrivers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Driver driver = dataSnapshot.getValue(Driver.class);
                drivirlist.add(driver);
                reyclelistdrivir.notifyDataSetChanged();

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
}
