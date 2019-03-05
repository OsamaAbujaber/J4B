package com.example.jbus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class employee1login extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferranceEMP;
    private List<EMP> emps =new ArrayList<>();
     List<String> info=new ArrayList<String>();


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
        mReferranceEMP=mDatabase.getReference("EMP");

        // Define ID's
        t= (TextView) findViewById(R.id.vi);
        T_id= (TextView) findViewById(R.id.t_id);
        T_pwd= (TextView) findViewById(R.id.t_pwd);
        Button Login= (Button) findViewById(R.id.login);

        //Get All Users ID and Password From FireBase
        readEMP();

        // Listener for login button
        Login.setOnClickListener(this);



    }

    public void readEMP(){


       for(int i=1;i<4;i++) {

       mReferranceEMP.child("EMP"+i).addValueEventListener(new ValueEventListener() {
           @Override

           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              String x=dataSnapshot.getValue()+"";
              // sub the first string which contan {id,pwd}
               int ind1=x.indexOf(",");
              //sub the id alone
              String id=x.substring(0,ind1);
              int ind2=id.indexOf("=");
              id=id.substring(ind2+1,id.length());
              id.trim();

               //sub the pwd alone
              String pwd=x.substring(ind1+1,x.length());
              int ind3=pwd.indexOf("=");
              pwd=pwd.substring(ind3+1,pwd.length());
              pwd=pwd.replace("}","");
              //add values to the list
              info.add(id);
              info.add(pwd);


           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

       }//end of for loop






    }


    @Override
    public void onClick(View v) {




         for (int i=0;i<info.size();i+=2){
        if(T_id.getText().toString().equals(info.get(i)))
        {
            Toast.makeText(this, "id correct", Toast.LENGTH_LONG).show();

            if (T_pwd.getText().toString().equals(info.get(i+1)))
            {
                Toast.makeText(this, "PWD correct", Toast.LENGTH_LONG).show();
            }


        }
    }

    }

}
