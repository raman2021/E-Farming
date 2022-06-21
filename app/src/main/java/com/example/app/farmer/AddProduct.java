package com.example.app.farmer;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app.BottomNavigationFarmer;
import com.example.app.FarmerPage;
import com.example.app.FarmerRegistration;
import com.example.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.view.CropImageView;

import java.io.File;
import java.util.UUID;
//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;

public class AddProduct extends AppCompatActivity {


    Button addP;
    Spinner Items;
    TextInputLayout dp, qt, pc;
    String des, quan, prod, price;
    ImageButton imageButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, data;
    FirebaseAuth Fauth;
    ProgressDialog progressDialog;
    Uri imguri = null;
    FirebaseStorage storage;
    StorageReference storageReference;
    private static final int Gallery_Code = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        imageButton = findViewById(R.id.imageView);

        Items = (Spinner) findViewById(R.id.products);
        dp = (TextInputLayout) findViewById(R.id.description);
        qt = (TextInputLayout) findViewById(R.id.quantity);
        pc = (TextInputLayout) findViewById(R.id.price);
        addP = (Button) findViewById(R.id.add);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("ItemDetails");
        progressDialog = new ProgressDialog(this);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Intent.ACTION_GET_CONTENT));
                intent.setType("image/*");

                startActivityForResult(intent, Gallery_Code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Code && resultCode == RESULT_OK) {
            imguri = data.getData();
            imageButton.setImageURI(imguri);

        }

        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prod = Items.getSelectedItem().toString().trim();
                des = dp.getEditText().getText().toString().trim();
                quan = qt.getEditText().getText().toString().trim();
                price = pc.getEditText().getText().toString().trim();

                if (!(prod.isEmpty() && des.isEmpty() && quan.isEmpty() && price.isEmpty() && imguri != null)) {

                    progressDialog.setTitle("Uploading......");
                    progressDialog.show();

                    StorageReference filepath = storage.getReference().child("image").child(imguri.getLastPathSegment());
                    filepath.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();
                                    DatabaseReference newPost = databaseReference.push();
                                    newPost.child("Description").setValue(des);
                                    newPost.child("Quantity").setValue(quan);
                                    newPost.child("Product").setValue(prod);
                                    newPost.child("Price : $").setValue(price);
                                    newPost.child("Image").setValue(task.getResult().toString());
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    });
                }


            }
        });





    }
}






