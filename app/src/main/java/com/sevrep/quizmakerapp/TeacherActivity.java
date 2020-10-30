package com.sevrep.quizmakerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sevrep.quizmakerapp.singleton.SharedPrefHandler;

public class TeacherActivity extends AppCompatActivity {

    SharedPrefHandler sharedPrefHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        sharedPrefHandler = new SharedPrefHandler(this);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logging out?")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (arg0, arg1) -> {

                    customToast("Goodbye " +sharedPrefHandler.getSharedPref("fullname")+ ".");
                    sharedPrefHandler.removeSharedPref("fullname");

                    Intent iMain = new Intent(this, MainActivity.class);
                    startActivity(iMain);
                    this.finish();
                }).create().show();
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }
}