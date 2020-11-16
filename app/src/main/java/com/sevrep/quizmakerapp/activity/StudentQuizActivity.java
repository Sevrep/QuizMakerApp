package com.sevrep.quizmakerapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.singleton.DatabaseHelper;
import com.sevrep.quizmakerapp.singleton.SharedPrefHandler;

public class StudentQuizActivity extends AppCompatActivity {

    private int subjectId;

    private SharedPrefHandler sharedPrefHandler;
    private DatabaseHelper databaseHelper;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quiz);

        sharedPrefHandler = new SharedPrefHandler(this);
        subjectId = Integer.parseInt(sharedPrefHandler.getSharedPref("subjectid"));

        databaseHelper = new DatabaseHelper(this);
        c = databaseHelper.getSubjectData(subjectId);

        TextView toolbar_student_quiz_title = findViewById(R.id.toolbar_title);
        String studentQuizTitle = "QUIZ: " + c.getString(c.getColumnIndex("subjectname"));
        toolbar_student_quiz_title.setText(studentQuizTitle);
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