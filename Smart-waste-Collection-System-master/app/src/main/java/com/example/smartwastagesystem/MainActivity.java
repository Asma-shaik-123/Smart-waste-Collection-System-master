package com.example.smartwastagesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void driver(View view) {
        Intent d=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(d);
    }

    public void admin(View view) {
        Intent a=new Intent(MainActivity.this,firebase_console.class);
        startActivity(a);
    }
}
