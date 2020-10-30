package com.sevrep.quizmakerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
    }

    @Override
    public void onBackPressed() {
        Intent iMain = new Intent(this, MainActivity.class);
        startActivity(iMain);
        finish();
    }
}