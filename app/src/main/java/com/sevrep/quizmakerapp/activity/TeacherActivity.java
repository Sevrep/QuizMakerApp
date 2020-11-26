package com.sevrep.quizmakerapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sevrep.quizmakerapp.MainActivity;
import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.adapter.SubjectAdapter;
import com.sevrep.quizmakerapp.model.Subject;
import com.sevrep.quizmakerapp.singleton.DatabaseHelper;
import com.sevrep.quizmakerapp.singleton.SharedPrefHandler;

import java.util.ArrayList;
import java.util.List;

public class TeacherActivity extends AppCompatActivity implements SubjectAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private List<Subject> subjectList;
    private String subjectTeacher;

    private SharedPrefHandler sharedPrefHandler;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        databaseHelper = new DatabaseHelper(this);
        sharedPrefHandler = new SharedPrefHandler(this);
        subjectTeacher = sharedPrefHandler.getSharedPref("fullname");

        FloatingActionButton fab_main = findViewById(R.id.fab);
        fab_main.setOnClickListener(v -> {
            final EditText edtSubjectName = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Topic")
                    .setMessage("Enter topic name.")
                    .setView(edtSubjectName)
                    .setPositiveButton("Add", (dialog1, which) -> {
                        String subjectName = edtSubjectName.getText().toString().trim();
                        if (TextUtils.isEmpty(subjectName)) {
                            customToast("Enter topic name.");
                        } else {
                            databaseHelper.createSubject(subjectName, subjectTeacher);
                            loadSubjects();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        });

        recyclerView = findViewById(R.id.rv_teacher_subjectlist);
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

                    customToast("Goodbye teacher " + sharedPrefHandler.getSharedPref("fullname") + ".");
                    sharedPrefHandler.removeSharedPref("fullname");

                    Intent iMain = new Intent(this, MainActivity.class);
                    startActivity(iMain);
                    finish();
                }).create().show();
    }

    @Override
    public void onItemClick(int position) {
        Subject clickedSubject = subjectList.get(position);
        int pageSubjectId = clickedSubject.getSubjectid();
        sharedPrefHandler.setSharedPref("subjectid", String.valueOf(pageSubjectId));

        Intent iTeacherUpdate = new Intent(this, TeacherUpdateActivity.class);
        startActivity(iTeacherUpdate);
        finish();
    }

    @Override
    public void onMoreClick(int position) {
        Subject clickedSubject = subjectList.get(position);
        final EditText edtSubjectName = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Edit topic name")
                .setMessage(clickedSubject.getSubjectname())
                .setView(edtSubjectName)
                .setPositiveButton("Update", (dialog1, which) -> {
                    String subjectName = edtSubjectName.getText().toString().trim();
                    if (TextUtils.isEmpty(subjectName)) {
                        customToast("Enter topic name.");
                    } else {
                        int subjectId = clickedSubject.getSubjectid();
                        databaseHelper.updateSubject(subjectId, subjectName);
                        loadSubjects();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    public void onDelClick(int position) {
        Subject clickedSubject = subjectList.get(position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("WARNING!!!")
                .setMessage("Are you sure you want to delete " + clickedSubject.getSubjectname() + "?")
                .setPositiveButton("DELETE", (dialog1, which) -> {
                    int subjectId = clickedSubject.getSubjectid();
                    databaseHelper.deleteSubject(subjectId);
                    loadSubjects();
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void loadSubjects() {
        subjectList.clear();
        Cursor cursor = databaseHelper.getAllSubjects(subjectTeacher);
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

        SubjectAdapter subjectAdapter = new SubjectAdapter(this, subjectList);
        recyclerView.setAdapter(subjectAdapter);
        SubjectAdapter.setOnItemClickListener(this);
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }
}