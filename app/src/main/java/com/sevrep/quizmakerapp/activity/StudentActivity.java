package com.sevrep.quizmakerapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.sevrep.quizmakerapp.MainActivity;
import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.adapter.StudentSubjectAdapter;
import com.sevrep.quizmakerapp.model.Subject;
import com.sevrep.quizmakerapp.singleton.DatabaseHelper;
import com.sevrep.quizmakerapp.singleton.SharedPrefHandler;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements StudentSubjectAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private List<Subject> subjectList;
    private String subjectStudent;

    private SharedPrefHandler sharedPrefHandler;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        databaseHelper = new DatabaseHelper(this);
        sharedPrefHandler = new SharedPrefHandler(this);
        subjectStudent = sharedPrefHandler.getSharedPref("fullname");

        recyclerView = findViewById(R.id.rv_student_subjectlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        subjectList = new ArrayList<>();

        loadSubjects();
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logging out?")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (arg0, arg1) -> {

                    customToast("Goodbye " + sharedPrefHandler.getSharedPref("fullname") + ".");
                    sharedPrefHandler.removeSharedPref("fullname");

                    Intent iMain = new Intent(this, MainActivity.class);
                    startActivity(iMain);
                    this.finish();
                }).create().show();
    }

    @Override
    public void onHighScoreClick(int position) {
        Subject clickedSubject = subjectList.get(position);

        customToast("HIGH SCORE: " + "\n"
                + clickedSubject.getSubjectid() + ": "
                + clickedSubject.getSubjectname());
    }

    @Override
    public void onItemClick(int position) {
        Subject clickedSubject = subjectList.get(position);
        int pageSubjectId = clickedSubject.getSubjectid();
        sharedPrefHandler.setSharedPref("subjectid", String.valueOf(pageSubjectId));

        Intent iStudentQuiz = new Intent(this, StudentQuizActivity.class);
        startActivity(iStudentQuiz);
        finish();
    }

    private void loadSubjects() {
        subjectList.clear();
        Cursor cursor = databaseHelper.getAllStudentSubjects();
        if (cursor.moveToFirst()) {
            do {
                Subject subject = new Subject(
                        cursor.getInt(cursor.getColumnIndex("subjectid")),
                        cursor.getString(cursor.getColumnIndex("subjectname")),
                        cursor.getString(cursor.getColumnIndex("subjectteacher"))
                );
                subjectList.add(subject);
            } while (cursor.moveToNext());
        }

        StudentSubjectAdapter studentSubjectAdapter = new StudentSubjectAdapter(this, subjectList);
        recyclerView.setAdapter(studentSubjectAdapter);
        StudentSubjectAdapter.setOnItemClickListener(this);
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }

}