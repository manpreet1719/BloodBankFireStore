package com.example.bloodbankfirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void Siguppp(View view) {

        Intent obj1 = new Intent(MainActivity.this,SignUpp.class);

        startActivity(obj1);

    }

    public void signinn(View view) {



    }

    public void adminloginn(View view) {

        Intent obj2 = new Intent(MainActivity.this,Adminsignin.class);

        startActivity(obj2);



    }
}