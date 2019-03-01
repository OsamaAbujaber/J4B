package com.example.jbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

       Button Student= (Button) findViewById(R.id.s1);
       Button Employee= (Button) findViewById(R.id.employee);

        Student.setOnClickListener(this);
        Employee.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
           case R.id.s1 :
               break;

            case R.id.employee:
               startActivity(new Intent(MainActivity.this,employee1login.class));
               break;


        }


    }
}
