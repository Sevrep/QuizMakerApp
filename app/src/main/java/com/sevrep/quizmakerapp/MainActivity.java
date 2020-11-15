package com.sevrep.quizmakerapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sevrep.quizmakerapp.activity.AdminActivity;
import com.sevrep.quizmakerapp.activity.SignActivity;
import com.sevrep.quizmakerapp.activity.StudentActivity;
import com.sevrep.quizmakerapp.activity.TeacherActivity;
import com.sevrep.quizmakerapp.singleton.DatabaseHelper;
import com.sevrep.quizmakerapp.singleton.SharedPrefHandler;

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
    private EditText edtUsername, edtPassword;
    private String loginType = "TEACHER";
    private int securityCheckCtr = 3;

    SharedPrefHandler sharedPrefHandler;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rellay1 = findViewById(R.id.rellay1);
        handler.postDelayed(runnable, 3000);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        imgLogo.startAnimation(myAnimation);

        btnTeacher = findViewById(R.id.btnTeacher);
        btnTeacher.setOnClickListener(this);
        btnTeacher.setAlpha(1f);
        btnStudent = findViewById(R.id.btnStudent);
        btnStudent.setOnClickListener(this);
        btnStudent.setAlpha(0.1f);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        changeButtonText(loginType);
        TextView txtNoAccount = findViewById(R.id.txtNoAccount);
        txtNoAccount.setOnClickListener(this);

        sharedPrefHandler = new SharedPrefHandler(this);
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View v) {
        int objectId = v.getId();
        if (objectId == R.id.btnTeacher) {
            btnTeacher.setAlpha(1f);
            btnStudent.setAlpha(0.1f);
            loginType = "TEACHER";
            changeButtonText(loginType);
        } else if (objectId == R.id.btnStudent) {
            btnStudent.setAlpha(1f);
            btnTeacher.setAlpha(0.1f);
            loginType = "STUDENT";
            changeButtonText(loginType);
        } else if (objectId == R.id.btnLogin) {
            checkLogin();
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

    public void checkLogin() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            edtUsername.setError("Enter username.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Enter password.");
            return;
        }

        if ((!username.equals("admin")) && (!password.equals("admin"))) {
            if (databaseHelper.loginUser(username, password, loginType)) {
                loginAccount(username);
            } else if (!databaseHelper.checkUsernameType(username, loginType)) {
                customToast("There is no " + loginType + " with username: " + username);
            } else {
                lockAccount();
            }
        } else {
            customToast("Welcome executor!");
            Intent iAdmin = new Intent(this, AdminActivity.class);
            startActivity(iAdmin);
            finish();
        }
    }

    private void loginAccount(String username) {
        Cursor cursor = databaseHelper.getFullnameData(username);
        sharedPrefHandler.setSharedPref("fullname", cursor.getString(cursor.getColumnIndex("fullname")));
        switch (cursor.getString(cursor.getColumnIndex("type"))) {
            case "TEACHER":
                customToast("Welcome teacher " + sharedPrefHandler.getSharedPref("fullname") + "!");
                Intent iTeacher = new Intent(this, TeacherActivity.class);
                startActivity(iTeacher);
                finish();
                break;
            case "STUDENT":
                customToast("Welcome " + sharedPrefHandler.getSharedPref("fullname") + "!");
                Intent iStudent = new Intent(this, StudentActivity.class);
                startActivity(iStudent);
                finish();
                break;
            default:
        }
    }

    private void lockAccount() {
        if (securityCheckCtr > 0) {
            customToast("You have " + securityCheckCtr + " tries left before lockout!");
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Warning!");
            builder.setMessage("Account locked!");
            builder.setCancelable(false);

            final AlertDialog alert = builder.create();
            alert.show();
            new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long l) {
                    customToast("Please wait: " + ((l / 1000) + 1) + " seconds.");
                }

                @Override
                public void onFinish() {
                    alert.cancel();
                }
            }.start();
            securityCheckCtr = 4;
        }
        securityCheckCtr--;
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }

    public void changeButtonText(String butones) {
        String buttonText = butones + " LOGIN";
        btnLogin.setText(buttonText);
    }

}