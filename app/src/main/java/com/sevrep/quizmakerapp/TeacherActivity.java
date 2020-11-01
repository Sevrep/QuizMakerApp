package com.sevrep.quizmakerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sevrep.quizmakerapp.singleton.SharedPrefHandler;

public class TeacherActivity extends AppCompatActivity {

    SharedPrefHandler sharedPrefHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        FloatingActionButton fab_main = findViewById(R.id.fab);
        fab_main.setOnClickListener(v -> {
            final EditText edtSubjectNAme = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Subject")
                    .setMessage("What do you want to do next?")
                    .setView(edtSubjectNAme)
                    .setPositiveButton("Add", (dialog1, which) -> {
                        String subjectName = edtSubjectNAme.getText().toString().trim();
                        if (TextUtils.isEmpty(subjectName)) {
                            customToast("Enter subject name.");
                        } else {
                            customToast(subjectName + " saved to database.");
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        });

        sharedPrefHandler = new SharedPrefHandler(this);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logging out?")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (arg0, arg1) -> {

                    customToast("Goodbye teacher " + sharedPrefHandler.getSharedPref("fullname") + ".");
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