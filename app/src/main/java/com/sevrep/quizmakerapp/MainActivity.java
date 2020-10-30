package com.sevrep.quizmakerapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rellay1;
    Handler handler = new Handler(Looper.myLooper());
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
        }
    };
    private Button btnTeacher, btnStudent, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rellay1 = findViewById(R.id.rellay1);
        handler.postDelayed(runnable, 2000);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        imgLogo.startAnimation(myAnimation);

        btnTeacher = findViewById(R.id.btnTeacher);
        btnTeacher.setOnClickListener(this);
        btnTeacher.setAlpha(1f);
        btnStudent = findViewById(R.id.btnStudent);
        btnStudent.setOnClickListener(this);
        btnStudent.setAlpha(0.1f);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnLogin.setText(R.string.teacher_login);
        TextView txtNoAccount = findViewById(R.id.txtNoAccount);
        txtNoAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int objectId = v.getId();
        if (objectId == R.id.btnTeacher) {
            btnTeacher.setAlpha(1f);
            btnStudent.setAlpha(0.1f);
            btnLogin.setText(R.string.teacher_login);
        } else if (objectId == R.id.btnStudent) {
            btnStudent.setAlpha(1f);
            btnTeacher.setAlpha(0.1f);
            btnLogin.setText(R.string.student_login);
        } else if (objectId == R.id.btnLogin) {
            customToast("Clicked btnLogin.");
        } else if (objectId == R.id.txtNoAccount) {
            Intent iSign = new Intent(this, SignActivity.class);
            startActivity(iSign);
            finish();
        } else {
            throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (arg0, arg1) -> {
                    customToast("Application closed!");
                    finish();
                })
                .create()
                .show();
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }

}