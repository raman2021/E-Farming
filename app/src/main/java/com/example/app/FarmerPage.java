package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FarmerPage extends AppCompatActivity {
    TextInputLayout eMail, pAss;
    Button loginn;
    FirebaseAuth Fauth;
    String id,pswd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_page);

        try {
            eMail = (TextInputLayout)findViewById(R.id.Emailfarmer);
            pAss = (TextInputLayout) findViewById(R.id.Passwordfarmer);
            loginn = (Button) findViewById(R.id.loggin);

            Fauth = FirebaseAuth.getInstance();

            loginn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = eMail.getEditText().getText().toString().trim();
                    pswd = pAss.getEditText().getText().toString().trim();
                    if(isValid()){
                        final ProgressDialog fDialog = new ProgressDialog(FarmerPage.this);
                        fDialog.setCanceledOnTouchOutside(false);
                        fDialog.setCancelable(false);
                        fDialog.setMessage("please wait.....");
                        fDialog.show();

                        Fauth.signInWithEmailAndPassword(id,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    fDialog.dismiss();

                                    if(Fauth.getCurrentUser().isEmailVerified()){
                                        fDialog.dismiss();
                                        Toast.makeText(FarmerPage.this, "Done", Toast.LENGTH_SHORT).show();
                                        Intent F = new Intent(FarmerPage.this, BottomNavigationFarmer.class);
                                        startActivity(F);
                                        finish();
                                    }else{
                                        ReusableCodeForAll.ShowAlert(FarmerPage.this,"verification failed","wrong email");
                                    }

                                }else{
                                    fDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(FarmerPage.this, "Error", task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){
        eMail.setErrorEnabled(false);
        eMail.setError("");
        pAss.setErrorEnabled(false);
        pAss.setError("");

        boolean isValid=false, isValidEmail=false, isValidPswd=false;
        if(TextUtils.isEmpty(id)){
            eMail.setErrorEnabled(true);
            eMail.setError("wrong email");
        }else{
            if(id.matches(pattern)){
                isValidEmail=true;
            }else{
                eMail.setErrorEnabled(true);
                eMail.setError("invalid id");
            }
        }
        if (TextUtils.isEmpty(pswd)) {
            pAss.setErrorEnabled(true);
            pAss.setError("enter password");
        }else{
            isValidPswd=true;
        }
        isValid=(isValidEmail && isValidPswd)? true : false;
        return isValid;
    }
}