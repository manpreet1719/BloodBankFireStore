package com.example.bloodbankfirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignIn extends AppCompatActivity {

    EditText mobile;
    EditText password;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mobile = findViewById(R.id.editTextTextPersonName4);
        password = findViewById(R.id.editTextTextPassword2);

        db = FirebaseFirestore.getInstance();





    }


    public void signiiin(View view) {





    }
}