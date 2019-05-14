package com.example.jbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.media.RemoteControlClient;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbus.model.Driver;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverList extends AppCompatActivity implements View.OnClickListener {
    ListView ListOfDriver;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferranceDrivers;
    private DatabaseReference DeleteDriver;
    private DatabaseReference CounterRef;
    private List<Driver> driversArray ;
    int driversNumber;
    ImageView imageView1;
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);
        mDatabase= FirebaseDatabase.getInstance();
        mReferranceDrivers=mDatabase.getReference("driver");
        driversArray = new ArrayList<>();
        readDrivers();

        ListOfDriver =findViewById(R.id.ListOfDriver);
        add=findViewById(R.id.add);
        customAdab customAdab =new customAdab();
        ListOfDriver.setAdapter(customAdab);
        DeleteDriver=mDatabase.getReference("driver");
        add.setOnClickListener(this);





    }





    class  customAdab extends BaseAdapter  {



        @Override
        public int getCount() {
            return driversArray.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int i, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.customdriver, null);
            ImageView imageView = view.findViewById(R.id.imageView4);
            final TextView textView1 = view.findViewById(R.id.textView3);
            imageView1 = view.findViewById(R.id.imageView5);
            textView1.setText(driversArray.get(i).getId());
            ImageView imageView2=view.findViewById(R.id.imageView2);

            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0;i<driversArray.size();i++)
                    {
                        Driver d1 =driversArray.get(i);



                            DeleteDriver = mDatabase.getReference("driver").child(textView1.getText().toString());
                            DeleteDriver.removeValue();
                            driversNumber--;
                            driversArray.remove(i);
                            CounterRef=mDatabase.getReference("Counter");
                            CounterRef.child("Numberofdrivers").setValue(driversArray.size());
                            break;




                    }                }
            });

            return view;
        }
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

                Driver driver = dataSnapshot.getValue(Driver.class);
                driversArray.add(driver);
                String ss= dataSnapshot.child("online").getValue(String.class);
                Toast.makeText(DriverList.this, "ONLINE "+s, Toast.LENGTH_SHORT).show();
                if(ss.equals("1"))
                    imageView1.setColorFilter(Color.GREEN);
                if(ss.equals("0"))
                    imageView1.setColorFilter(Color.GRAY);
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
        Intent intent =new Intent(DriverList.this,Admin.class);
        startActivity(intent);
    }

}
