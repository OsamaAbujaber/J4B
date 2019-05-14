package com.example.jbus;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jbus.model.Driver;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Reyclelistdrivir extends RecyclerView.Adapter<Reyclelistdrivir.ViewHolder> {
    public List<Driver> driver_list;
    public Context context;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferranceDrivers;
    private DatabaseReference DeleteDriver;
    private DatabaseReference CounterRef;

    int driversNumber;


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public Reyclelistdrivir(List<Driver> drivirlist) {
        this.driver_list = drivirlist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customdriver,viewGroup,false);
        context= viewGroup.getContext();
        mDatabase= FirebaseDatabase.getInstance();
        mReferranceDrivers=mDatabase.getReference("driver");
        DeleteDriver=mDatabase.getReference("driver");
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {


        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.setIsRecyclable(false);
        String driver_name =driver_list.get(i).getId();
        viewHolder.setName(driver_name);
        final String online = driver_list.get(i).getOnline();

        if(online.equals("1"))
            viewHolder.imageView5.setColorFilter(Color.GREEN);
        if(online.equals("0"))
            viewHolder.imageView5.setColorFilter(Color.GRAY);

        viewHolder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<driver_list.size();i++)
                {

                    DeleteDriver = mDatabase.getReference("driver").child(viewHolder.textView.getText().toString());
                    DeleteDriver.removeValue();
                    driversNumber--;
                    driver_list.remove(i);
                    CounterRef=mDatabase.getReference("Counter");
                    CounterRef.child("Numberofdrivers").setValue(driver_list.size());
                    break;




                }
            }
        });

            mReferranceDrivers.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

//                        String ss=dataSnapshot.getValue(String.class);
//                        Toast.makeText(context, ss, Toast.LENGTH_SHORT).show();
////                    Driver driver = dataSnapshot.getValue(Driver.class);
////                    driver_list.add(driver);
////                    String ss= s;
//////                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                    if(ss.equals("1"))
//                       viewHolder.imageView5.setColorFilter(Color.GREEN);
//                    if(ss.equals("0"))
//                        viewHolder.imageView5.setColorFilter(Color.GRAY);
                    Toast.makeText(context,  dataSnapshot.child("online").getValue(String.class)+"", Toast.LENGTH_SHORT).show();
                  String ss=dataSnapshot.child("online").getValue(String.class);
                  int x=  getItemViewType(i);
                    Toast.makeText(context,  i+"", Toast.LENGTH_SHORT).show();

                    if(ss.equals("1"))
                        viewHolder.setonline(viewHolder.imageView5);


                    if(ss.equals("0"))
                        viewHolder.setoffline(viewHolder.imageView5);

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
    public int getItemCount() {

        return driver_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        private ImageView imageView5;
        private ImageView imageView2;
        private TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            imageView2=mView.findViewById(R.id.imageView2);
            imageView5=mView.findViewById(R.id.imageView5);

        }
public  void  update_view(){


}
        public void setonline(ImageView imageView) {
            imageView.setColorFilter(Color.GREEN);


        }
        public void setoffline(ImageView imageView) {
            imageView.setColorFilter(Color.GRAY);


        }

        public void setName(String driver_name) {

            textView=mView.findViewById(R.id.textView3);
            textView.setText(driver_name);

        }
    }

}
