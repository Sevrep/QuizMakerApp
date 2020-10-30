package com.sevrep.quizmakerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sevrep.quizmakerapp.singleton.DatabaseHelper;

import java.util.Objects;

public class SignActivity extends AppCompatActivity {

    private EditText edtFullname, edtUsername, edtPassword, edtConfirm;
    private Spinner sprType;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        Toolbar toolbar = findViewById(R.id.toolbar_sign);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        edtFullname = findViewById(R.id.edtFullname);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirm = findViewById(R.id.edtConfirm);
        sprType = findViewById(R.id.sprType);
        Button btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> validateEntries());

        databaseHelper = new DatabaseHelper(this);
    }

    private void validateEntries() {
        String fullname = edtFullname.getText().toString().trim();
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirm = edtConfirm.getText().toString().trim();
        String type = sprType.getSelectedItem().toString();

        if (TextUtils.isEmpty(fullname)) {
            edtFullname.setError("Enter full name.");
            return;
        }
        if (TextUtils.isEmpty(username)) {
            edtUsername.setError("Enter username.");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Enter password.");
            return;
        }
        if (TextUtils.isEmpty(confirm)) {
            edtConfirm.setError("Enter confirmation password.");
            return;
        }
        if (type.equals("ACCOUNT TYPE")) {
            customToast("Please choose and account type.");
        } else {
            if(databaseHelper.checkUsernameDuplicate(username)) {
                customToast("Username already taken!");
            } else if (databaseHelper.checkFullnameDuplicate(fullname)) {
                customToast("You already have an account!");
            } else {
                databaseHelper.createUser(fullname, username, password, type);
                customToast("Account saved!.");
                goToMain();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        goToMain();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        goToMain();
    }

    private void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }

    public void goToMain() {
        Intent iMain = new Intent(this, MainActivity.class);
        startActivity(iMain);
        finish();
    }
}