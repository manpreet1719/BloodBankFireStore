package com.example.bloodbankfirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Adminsignin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsignin);
    }


    public void adminnnsign(View view) {

        Intent obj2 = new Intent(Adminsignin.this,AdminLogin.class);

        startActivity(obj2);

    }

}