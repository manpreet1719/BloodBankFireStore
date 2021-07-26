package com.example.bloodbankfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class AdminLogin extends AppCompatActivity {


    EditText name;
    EditText addd;
    EditText mobilee;
    EditText bldgrpp;
    Spinner spinner;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;
    FirebaseFirestore db;

    String namee;
    String address;
    String mob;
    String bldgrp;
    AdminDetail adminDetail;
    ImageView imageView;
    String imageeurll;

    private Uri selectedImageUri = null;
    private Uri filePath;
    private Bitmap compressed;
    FirebaseStorage storage;
    StorageReference storageReference;
    //    private StorageReference storageReference;
    private final int PICK_IMAGE_REQUEST = 22;
    int flag =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        name = findViewById(R.id.editTextTextPersonName3);
        addd = findViewById(R.id.editTextTextPersonName7);
        mobilee = findViewById(R.id.editTextTextPersonName8);
        spinner = findViewById(R.id.spinner2);
        arrayList = new ArrayList();
        db = FirebaseFirestore.getInstance();
        imageView = findViewById(R.id.imageView3);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        arrayList.add("(A+)");
        arrayList.add("(A-)");
        arrayList.add("(B+)");
        arrayList.add("(B-)");
        arrayList.add("(O+)");
        arrayList.add("(O-)");
        arrayList.add("(AB+)");
        arrayList.add("(AB-)");

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        spinner.setAdapter(arrayAdapter);



    }



    public class Constants {

        public static final String STORAGE_PATH_UPLOADS = "uploads/";
        public static final String DATABASE_PATH_UPLOADS = "uploads";
    }

    public void selecttimage(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);



    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    public void uplddd(View view) {

        flag = 0;

        uploadFile();

    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }





    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageeurll = uri.toString();
                                }
                            });

                            //creating the upload object to store uploaded image details
//                            imageeurll = taskSnapshot.getDownloadUrl().toString();
                            //Upload upload = new Upload(editTextName.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());

                            //adding an upload to firebase database
//                            String uploadId = mDatabase.push().getKey();
//                            mDatabase.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            //display an error if no file is selected
        }
    }



    private boolean validateInputs(String namee, String address, String mob, String  bldgrp, int flag ) {
        if (namee.isEmpty()) {
            name.setError("Name required");
            name.requestFocus();
            return true;
        }

        if (address.isEmpty()) {
            addd.setError("Brand required");
            addd.requestFocus();
            return true;
        }

        if (mob.isEmpty()) {
            mobilee.setError("Description required");
            mobilee.requestFocus();
            return true;
        }

        if (bldgrp.isEmpty()) {
            Toast.makeText(this, "Select BloodGroup", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (flag == 1) {
            Toast.makeText(this, "Choose Image and click on Upload ", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void adddonorr(View view) {


        namee = name.getText().toString();
        address = addd.getText().toString();
        mob =mobilee.getText().toString();
        bldgrp = spinner.getSelectedItem().toString();

        if (!validateInputs(namee, address, mob , bldgrp, flag)) {


            adminDetail = new AdminDetail(namee, address, mob, bldgrp, imageeurll);


            CollectionReference admindetails = db.collection("admindata");

            admindetails.add(adminDetail).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(AdminLogin.this, "Donor added", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {

                    Toast.makeText(AdminLogin.this, "Not able to add Donor!", Toast.LENGTH_SHORT).show();

                }
            });


        }




    }

    public void gettdonorlistt(View view) {





        Intent obj = new Intent(this,dataList.class);

        startActivity(obj);


    }



}