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

   /* ImageButton imageButton;
    Button addP;
    Spinner Items;
    TextInputLayout dp, qt, pc;
    String des, quan, prod, price;

    private  Uri mcimguri;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, data;
    FirebaseAuth Fauth;

    String FarmerId, RandomUID; */
   ImageView imageView;
   Uri imguri;
    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

       imageView = findViewById(R.id.imageView);
       storage= FirebaseStorage.getInstance();
       storageReference = storage.getReference();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            imageView.setImageURI(imguri);
            uploadImage();
        }


      /*  imageButton = (ImageButton) findViewById(R.id.Upload);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Items = (Spinner) findViewById(R.id.products);
        dp = (TextInputLayout) findViewById(R.id.description);
        qt = (TextInputLayout) findViewById(R.id.quantity);
        pc = (TextInputLayout) findViewById(R.id.price);
        addP = (Button) findViewById(R.id.add);
        Fauth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getInstance("https://e-farming-c93ab-default-rtdb.firebaseio.com/").getReference("ItemDetails");

        try {

            String userid =  FirebaseAuth.getInstance().getCurrentUser().getUid();
            data = firebaseDatabase.getInstance().getReference("Farmer").child(userid);
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Farmer farmerr = snapshot.getValue(Farmer.class);
                    //imageButton = (ImageButton) findViewById(R.id.Upload);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            onSelectImageClick(v);
                        }
                    });
                    addP.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            prod = Items.getSelectedItem().toString().trim();
                            des = dp.getEditText().getText().toString().trim();
                            quan = qt.getEditText().getText().toString().trim();
                            price = pc.getEditText().getText().toString().trim();

                            if (isValid()) {
                                uploadImage();
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

            Log.e("Error: ", e.getMessage());
        }
    }


    private void onSelectImageClick(View v) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            imageButton.setImageURI(imguri);
            uploadImage();
        }
    }



     private void uploadImage() {
        if (imguri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(AddProduct.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUID);
            FarmerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            ItemDetails info = new ItemDetails(prod, quan, price, des, String.valueOf(uri), RandomUID, FarmerId);
                            firebaseDatabase.getInstance().getReference("ItemDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(AddProduct.this, "Item posted successfully", Toast.LENGTH_SHORT).show();


                                        }
                                    });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    progressDialog.setCanceledOnTouchOutside(false);

                }
            });

        }


    }

    private boolean isValid() {
        dp.setErrorEnabled(false);
        dp.setError("");
        qt.setErrorEnabled(false);
        qt.setError("");
        pc.setErrorEnabled(false);
        pc.setError("");

        boolean isValiDescription = false, isValidPrice = false, isvalidQuantity = false, isvalid = false;
        if (TextUtils.isEmpty(des)) {
            dp.setErrorEnabled(true);
            dp.setError("Description is Required");

        } else {

            dp.setError(null);
            isValiDescription = true;
        }
        if (TextUtils.isEmpty(quan)) {
            qt.setErrorEnabled(true);
            qt.setError("Quantity is Required");
        } else {
            isvalidQuantity = true;
        }
        if (TextUtils.isEmpty(price)) {
            pc.setErrorEnabled(true);
            pc.setError("Price is Required");
        } else {
            isValidPrice = true;
        }
        isvalid = (isValiDescription && isvalidQuantity && isValidPrice) ? true : false;

        return isvalid;
    }






   /* private void startCropImageActivity(Uri imguri) {

        CropImage.activity(imguri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    private void onSelectImageClick(View v) {

        CropImage.startPickImageActivity(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mcimguri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCropImageActivity(mcimguri);
        } else {
            Toast.makeText(this, "cancelling,required permission not granted", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imguri = CropImage.getPickImageResultUri(this, data);

            if (CropImage.isReadExternalStoragePermissionsRequired(this, imguri)) {
                mcimguri = imguri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            } else {

                startCropImageActivity(imguri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageButton) findViewById(R.id.Upload)).setImageURI(result.getUri());
                Toast.makeText(this, "Cropped Successfully", Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed" + result.getError(), Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    } */






}

    private void uploadImage() {

        final ProgressDialog pa = new ProgressDialog(this);
        pa.setTitle("uploading.....");
        pa.show();
        final String randomKey =UUID.randomUUID().toString();

        StorageReference riversRef = storageReference.child("images/" + randomKey);

        riversRef.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pa.dismiss();
                Snackbar.make(findViewById(android.R.id.content), "image uploaded", Snackbar.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pa.dismiss();
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                pa.setMessage("percentage: " +(int) progressPercent + "%");
            }
        });
    }
    }






