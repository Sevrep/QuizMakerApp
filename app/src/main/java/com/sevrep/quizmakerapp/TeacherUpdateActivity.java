package com.sevrep.quizmakerapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sevrep.quizmakerapp.singleton.DatabaseHelper;

import java.util.Objects;

public class TeacherUpdateActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton fab_add_question, fab1_trueorfalse, fab2_multiple;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private TextView textview_trueorfalse, textview_multiple;
    private int subjectId;

    private Boolean isOpen = false;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_update);

        subjectId = Objects.requireNonNull(getIntent().getExtras()).getInt("extra_subjectid");

        databaseHelper = new DatabaseHelper(this);
        Cursor c = databaseHelper.getSubjectData(subjectId);

        TextView toolbar_teacher_update_title = findViewById(R.id.toolbar_teacher_update_title);
        toolbar_teacher_update_title.setText(c.getString(c.getColumnIndex("subjectname")));

        fab_add_question = findViewById(R.id.fab_add_question);
        fab1_trueorfalse = findViewById(R.id.fab1_trueorfalse);
        fab2_multiple = findViewById(R.id.fab2_multiple);

        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        textview_trueorfalse = findViewById(R.id.textview_trueorfalse);
        textview_multiple = findViewById(R.id.textview_multiple);

        fab_add_question.setOnClickListener(this);
        fab1_trueorfalse.setOnClickListener(this);
        fab2_multiple.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        goToTeacher();
    }

    @Override
    public void onClick(View v) {
        int objectId = v.getId();
        if (objectId == R.id.fab_add_question) {
            if (isOpen) {
                textview_trueorfalse.setVisibility(View.INVISIBLE);
                textview_multiple.setVisibility(View.INVISIBLE);
                fab2_multiple.startAnimation(fab_close);
                fab1_trueorfalse.startAnimation(fab_close);
                fab_add_question.startAnimation(fab_anticlock);
                fab2_multiple.setClickable(false);
                fab1_trueorfalse.setClickable(false);
                isOpen = false;
            } else {
                textview_trueorfalse.setVisibility(View.VISIBLE);
                textview_multiple.setVisibility(View.VISIBLE);
                fab2_multiple.startAnimation(fab_open);
                fab1_trueorfalse.startAnimation(fab_open);
                fab_add_question.startAnimation(fab_clock);
                fab2_multiple.setClickable(true);
                fab1_trueorfalse.setClickable(true);
                isOpen = true;
            }
        } else if (objectId == R.id.fab1_trueorfalse) {
            customToast("Open true or false AlertDialog.");
        } else if (objectId == R.id.fab2_multiple) {
            customToast("Open multiple choice AlertDialog.");
        } else {
            throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    public void goToTeacher() {
        Intent iTeacher = new Intent(this, TeacherActivity.class);
        startActivity(iTeacher);
        finish();
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }

}