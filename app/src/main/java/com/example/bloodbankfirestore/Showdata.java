package com.example.bloodbankfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class Showdata extends AppCompatActivity {

    FirebaseFirestore db;

    private AdminDetail adminDetail;
    TextView showdetail;
    String mobnoo;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata);

        adminDetail = (AdminDetail) getIntent().getSerializableExtra("listdata");
        showdetail = findViewById(R.id.textView);
        showdetail.setText("Name - " + adminDetail.getNamee() + "\n" + "Address - " +  adminDetail.getAddress() + "\n" + "Mobile - " +  adminDetail.getMob() + "\n" + "BloodGroup - " +  adminDetail.getBldgrp());

        db = FirebaseFirestore.getInstance();


    }

    public void updatedonor(View view) {

        Intent intent=new Intent(Showdata.this,UpdateDonorDetail.class);
        intent.putExtra("updatedata",adminDetail);
        startActivity(intent);




    }

    public void deletedonor(View view) {

        db.collection("admindata").document(adminDetail.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {

                Toast.makeText(Showdata.this, "Donor Deleted", Toast.LENGTH_SHORT).show();

            }
        });






    }

    public void callldonor(View view) {

        mobnoo = adminDetail.getMob();

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+ mobnoo));
        startActivity(callIntent);;


    }

    public void gooback(View view) {

        Intent obj = new Intent(this,dataList.class);

        startActivity(obj);

    }
}