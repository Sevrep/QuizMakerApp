package com.sevrep.quizmakerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sevrep.quizmakerapp.singleton.SharedPrefHandler;

public class TeacherActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab_main, fab1_trueorfalse, fab2_multiple;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView textview_trueorfalse, textview_multiple;

    Boolean isOpen = false;

    SharedPrefHandler sharedPrefHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        fab_main = findViewById(R.id.fab);
        fab1_trueorfalse = findViewById(R.id.fab1_trueorfalse);
        fab2_multiple = findViewById(R.id.fab2_multiple);

        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        textview_trueorfalse = findViewById(R.id.textview_trueorfalse);
        textview_multiple = findViewById(R.id.textview_multiple);

        fab_main.setOnClickListener(this);
        fab1_trueorfalse.setOnClickListener(this);
        fab2_multiple.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        int objectId = v.getId();
        if (objectId == R.id.fab) {
            if (isOpen) {
                textview_trueorfalse.setVisibility(View.INVISIBLE);
                textview_multiple.setVisibility(View.INVISIBLE);
                fab2_multiple.startAnimation(fab_close);
                fab1_trueorfalse.startAnimation(fab_close);
                fab_main.startAnimation(fab_anticlock);
                fab2_multiple.setClickable(false);
                fab1_trueorfalse.setClickable(false);
                isOpen = false;
            } else {
                textview_trueorfalse.setVisibility(View.VISIBLE);
                textview_multiple.setVisibility(View.VISIBLE);
                fab2_multiple.startAnimation(fab_open);
                fab1_trueorfalse.startAnimation(fab_open);
                fab_main.startAnimation(fab_clock);
                fab2_multiple.setClickable(true);
                fab1_trueorfalse.setClickable(true);
                isOpen = true;
            }
        } else if (objectId == R.id.fab1_trueorfalse) {
            customToast("Go to true or false activity.");
        } else if (objectId == R.id.fab2_multiple) {
            customToast("Go to multiple choice activity.");
        } else {
            throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }

}