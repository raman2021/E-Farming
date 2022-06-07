package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserType extends AppCompatActivity {

    Button farmer, customer;
    Intent intent;
    String type;
    ConstraintLayout backimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        AnimationDrawable animationDrawable= new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.farmer), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.farmer2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.farmers), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.tractor), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.veg), 3000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(900);
        animationDrawable.setExitFadeDuration(1500);

        backimage= findViewById(R.id.registerPage2);
        backimage.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        intent = getIntent();
        type = intent.getStringExtra("Home").toString().trim();

        farmer = (Button)findViewById(R.id.farmer);
        customer = (Button)findViewById(R.id.customer);

        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Email")){
                    Intent loginF = new Intent(UserType.this,FarmerPage.class);
                    startActivity(loginF);
                    finish();
                }
                if(type.equals("SignUp")){
                    Intent registerF = new Intent(UserType.this,FarmerRegistration.class);
                    startActivity(registerF);
                   // finish();
                }
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Email")){
                    Intent loginC = new Intent(UserType.this,CustomerPage.class);
                    startActivity(loginC);
                    finish();
                }
                if(type.equals("SignUp")) {
                    Intent registerC = new Intent(UserType.this, CustomerRegistration.class);
                    startActivity(registerC);
                    // finish();
                }
            }
        });
    }
}
