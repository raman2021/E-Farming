package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import dagger.Reusable;

public class FarmerRegistration extends AppCompatActivity {

    TextInputLayout FirstF, LastF, EmailF, PassF, CpassF;
    Button reg, mail;
    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String firstF, lastF, emailF, passF, cpassF, role = "Farmer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_registration);

        FirstF = (TextInputLayout) findViewById(R.id.FirstName);
        LastF = (TextInputLayout) findViewById(R.id.LastName);
        EmailF = (TextInputLayout) findViewById(R.id.Email);
        PassF = (TextInputLayout) findViewById(R.id.Password);
        CpassF = (TextInputLayout) findViewById(R.id.ConfirmPassword);


        reg = (Button) findViewById(R.id.register);
        mail = (Button) findViewById(R.id.email);

        databaseReference = firebaseDatabase.getInstance("https://e-farming-c93ab-default-rtdb.firebaseio.com/").getReference("Farmer");
        FAuth = FirebaseAuth.getInstance();


        reg.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                firstF = FirstF.getEditText().getText().toString().trim();
                lastF = LastF.getEditText().getText().toString().trim();
                emailF = EmailF.getEditText().getText().toString().trim();
                passF = PassF.getEditText().getText().toString().trim();
                cpassF = CpassF.getEditText().getText().toString().trim();
                if (isValid()) {


                    final ProgressDialog fDialog = new ProgressDialog(FarmerRegistration.this);
                    fDialog.setCancelable(false);
                    fDialog.setCanceledOnTouchOutside(false);
                    fDialog.setMessage("please wait .......");
                    fDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailF, passF).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance("https://e-farming-c93ab-default-rtdb.firebaseio.com/").getReference("Users").child(userid);
                                final HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Role", role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, String> hashMap1 = new HashMap<>();
                                        hashMap1.put("First Name", firstF);
                                        hashMap1.put("Last Name", lastF);
                                        hashMap1.put("Email Address", emailF);
                                        hashMap1.put("Password", passF);
                                        hashMap1.put("Confirm Password", cpassF);
                                        firebaseDatabase.getInstance().getReference("Farmer")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        fDialog.dismiss();
                                                       FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(FarmerRegistration.this);
                                                                    builder.setMessage("Done and verify your email");
                                                                    builder.setCancelable(false);
                                                                   builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                            Intent b = new Intent(FarmerRegistration.this, FarmerPage.class);
                                                                            startActivity(b);
                                                                        }
                                                                    });

                                                                    AlertDialog Alert = builder.create();
                                                                    Alert.show();
                                                                } else {
                                                                    fDialog.dismiss();
                                                                    ReusableCodeForAll.ShowAlert(FarmerRegistration.this, "Error", task.getException().getMessage());

                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                });


                            } else {
                                fDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(FarmerRegistration.this, "Error", task.getException().getMessage());
                            }

                        }
                    });

                }

            }

        });

        EmailF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(FarmerRegistration.this, FarmerPage.class);
                startActivity(i);
                finish();
            }
        });

    }




        String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        public boolean isValid () {
            EmailF.setErrorEnabled(false);
            EmailF.setError("");
            FirstF.setErrorEnabled(false);
            FirstF.setError("");
            LastF.setErrorEnabled(false);
            LastF.setError("");
            PassF.setErrorEnabled(false);
            PassF.setError("");
            CpassF.setErrorEnabled(false);
            CpassF.setError("");

            boolean isValid = false, isValidname = false, isValidname2 = false, isValidemail = false, isValidp = false, isValidcp = false;
            if (TextUtils.isEmpty(firstF)) {
                FirstF.setErrorEnabled(true);
                FirstF.setError("Enter First Name");
            } else {
                isValidname = true;
            }
            if (TextUtils.isEmpty(lastF)) {
                LastF.setErrorEnabled(true);
                LastF.setError("Enter Last Name");
            } else {
                isValidname2 = true;
            }
            if (TextUtils.isEmpty(emailF)) {
                EmailF.setErrorEnabled(true);
                EmailF.setError("Enter email");
            } else {
                if (emailF.matches(pattern)) {

                    isValidemail = true;
                } else {
                    EmailF.setErrorEnabled(true);
                    EmailF.setError("valid email");
                }
            }
            if (TextUtils.isEmpty(passF)) {
                PassF.setErrorEnabled(true);
                PassF.setError("password");
            } else {
                if (passF.length() < 8) {
                    PassF.setErrorEnabled(true);
                    PassF.setError("valid password");
                } else {
                    isValidp = true;
                }
            }
            if (TextUtils.isEmpty(cpassF)) {
                CpassF.setErrorEnabled(true);
                CpassF.setError(" confirm password");
            } else {
                if (!passF.equals(cpassF)) {
                    CpassF.setErrorEnabled(true);
                    CpassF.setError(" not matched");
                } else {
                    isValidcp = true;
                }
            }
            isValid = (isValidname || isValidemail || isValidp && isValidcp && isValidname2) ? true : false;
            return isValid;

        }



}