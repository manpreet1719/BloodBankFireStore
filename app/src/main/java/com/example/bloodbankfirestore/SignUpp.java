package com.example.bloodbankfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.UUID;

import io.grpc.Compressor;

public class SignUpp extends AppCompatActivity {
    FirebaseFirestore db;


   EditText namee;
   EditText Email;
   EditText addr;
   EditText dob;
   EditText lstdate;
   EditText mobb;
   CheckBox chk;
   DatePickerDialog datePickerDialog;
   String nme,Emaill,addd,dobb,lsttdate,mobil,bldgrpp;
   String imageeurll;
    int flag = 1 ;

    private Uri selectedImageUri = null;
    private Uri filePath;
    private Bitmap compressed;
    FirebaseStorage storage;
    StorageReference storageReference;
//    private StorageReference storageReference;
    private final int PICK_IMAGE_REQUEST = 22;


    Spinner spinner;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;
    SignupDetail signupDetail;
    ImageView imageView;
    int SELECT_PICTURE = 200;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upp);

        namee = findViewById(R.id.editTextTextPersonName);
        Email = findViewById(R.id.editTextTextPersonName6);
        addr = findViewById(R.id.editTextTextPersonName2);
        dob = findViewById(R.id.editTextDate);
        lstdate = findViewById(R.id.date);
        mobb = findViewById(R.id.editTextTextPersonName5);
        spinner = findViewById(R.id.spinner);
        chk = findViewById(R.id.checkBox);
        imageView = findViewById(R.id.imageView);
        arrayList = new ArrayList();
        db = FirebaseFirestore.getInstance();
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

        arrayAdapter = new ArrayAdapter(SignUpp.this, android.R.layout.simple_list_item_1, arrayList);
        spinner.setAdapter(arrayAdapter);

        lstdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(SignUpp.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                lstdate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    public class Constants {

        public static final String STORAGE_PATH_UPLOADS = "uploads/";
        public static final String DATABASE_PATH_UPLOADS = "uploads";
    }


    public void selecttimagesign(View view) {  //change it

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


    public void upldddsignup(View view) { //change it

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
            StorageReference sRef = storageReference.child(AdminLogin.Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

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


    private boolean validateInputs(String   nme, String Emaill, String addd, String dobb, String  lsttdate , String mobil,String bldgrpp,int flag ) {
        if (nme.isEmpty()) {
            namee.setError("Name required");
            namee.requestFocus();
            return true;
        }

        if (Emaill.isEmpty()) {
            Email.setError("Email required");
            Email.requestFocus();
            return true;
        }

        if (addd.isEmpty()) {
            addr.setError("Address required");
            addr.requestFocus();
            return true;
        }
        if (dobb.isEmpty()) {
            dob.setError("DOB required");
            dob.requestFocus();
            return true;
        }
        if (lsttdate.isEmpty()) {
            lstdate.setError("Last Blood Donated Date required");
            lstdate.requestFocus();
            return true;
        }
        if (mobil.isEmpty()) {
            mobb.setError("Mobile required");
            mobb.requestFocus();
            return true;
        }



        if (bldgrpp.isEmpty()) {
            Toast.makeText(this, "Choose BloodGroup ", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (flag == 1) {
            Toast.makeText(this, "Choose Image and Click on Upload ", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }






    public void savedataa(View view) {

        nme = namee.getText().toString();
        Emaill = Email.getText().toString();
        addd = addr.getText().toString();
        dobb =dob.getText().toString();
        lsttdate = lstdate.getText().toString();
        mobil = mobb.getText().toString();
        bldgrpp = spinner.getSelectedItem().toString();

        if (!validateInputs(nme, Emaill, addd , dobb ,lsttdate,mobil,bldgrpp, flag)) {


            signupDetail = new SignupDetail(nme, Emaill, addd, dobb, lsttdate, mobil, bldgrpp, imageeurll);

            CollectionReference SignuppDetails = db.collection("signupdata");

            SignuppDetails.add(signupDetail).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Toast.makeText(SignUpp.this, "Succesfully Signup", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(SignUpp.this, "Failed To add data", Toast.LENGTH_SHORT).show();

                }
            });


        }


    }


}