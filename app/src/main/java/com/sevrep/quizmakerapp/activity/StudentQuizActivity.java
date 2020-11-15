package com.sevrep.quizmakerapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.singleton.SharedPrefHandler;

public class StudentQuizActivity extends AppCompatActivity {

    private SharedPrefHandler sharedPrefHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quiz);

        sharedPrefHandler = new SharedPrefHandler(this);
    }

    @Override
    public void onBackPressed() {
        goToStudent();
    }

    public void goToStudent() {
        sharedPrefHandler.removeSharedPref("subjectid");
        customToast("SharedPref: " + sharedPrefHandler.getSharedPref("subjectid"));
        Intent iStudent = new Intent(this, StudentActivity.class);
        startActivity(iStudent);
        finish();
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }
}