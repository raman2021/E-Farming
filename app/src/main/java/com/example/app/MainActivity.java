package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    TextView textV;
    FirebaseAuth Fauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        image=(ImageView)findViewById(R.id.image);
        textV=(TextView)findViewById(R.id.text);


        image.animate().alpha(0f).setDuration(0);
        textV.animate().alpha(0f).setDuration(0);

        image.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                textV.animate().alpha(1f).setDuration(900);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Fauth = FirebaseAuth.getInstance();
                if(Fauth.getCurrentUser()!=null){
                    if(Fauth.getCurrentUser().isEmailVerified()){
                        Fauth = FirebaseAuth.getInstance();

                        databaseReference = FirebaseDatabase.getInstance("https://e-farming-c93ab-default-rtdb.firebaseio.com/").getReference("Users").child(FirebaseAuth.getInstance().getUid()+"/Role");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                String role = datasnapshot.getValue(String.class);
                                if(role.equals("Farmer")){
                                    Intent r = new Intent(MainActivity.this, BottomNavigationFarmer.class);
                                    startActivity(r);
                                    //startActivity(new Intent(MainActivity.this,BottomNavigationFarmer.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError ) {
                                Toast.makeText(MainActivity.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("have you verified yor mail");
                        builder.setCancelable(false);
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this,ShopPage.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        Fauth.signOut();
                    }
                }else {


                    Intent intent = new Intent(MainActivity.this, ShopPage.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}