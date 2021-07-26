package com.example.bloodbankfirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignIn extends AppCompatActivity {

    EditText mobile;
    EditText Pass;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mobile = findViewById(R.id.editTextTextPersonName4);
        Pass = findViewById(R.id.editTextTextPassword);

        db = FirebaseFirestore.getInstance();





    }


    public void signiiin(View view) {





    }
}