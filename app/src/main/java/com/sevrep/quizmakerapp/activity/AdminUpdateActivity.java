package com.sevrep.quizmakerapp.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.singleton.DatabaseHelper;

import java.util.Objects;

public class AdminUpdateActivity extends AppCompatActivity implements View.OnClickListener{

    private String fullname;

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtConfirm;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update);

        fullname = Objects.requireNonNull(getIntent().getExtras()).getString("extra_fullname");

        Toolbar toolbar = findViewById(R.id.toolbar_user_update);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        EditText edtFullname = findViewById(R.id.edtFullname);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirm = findViewById(R.id.edtConfirm);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);
        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(this);
        Cursor c = databaseHelper.getUserData(fullname);

        edtFullname.setText(c.getString(c.getColumnIndex("fullname")));
        edtUsername.setText(c.getString(c.getColumnIndex("username")));
        edtPassword.setText(c.getString(c.getColumnIndex("password")));

        edtFullname.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        int objectId = v.getId();
        if (objectId == R.id.btnUpdate) {
            updateAccount();
        } else if (objectId == R.id.btnDelete) {
            deleteAccount();
        } else {
            throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        goToAdmin();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        goToAdmin();
    }

    public void updateAccount() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirm = edtConfirm.getText().toString().trim();

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

        if (!password.equals(confirm)) {
            customToast("Password and confirm password do not match.");
        } else {
            databaseHelper.updateUserData(fullname, username, password);
            Toast.makeText(this, "Account updated.", Toast.LENGTH_LONG).show();
            goToAdmin();
        }

    }

    public void deleteAccount() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Confirmation")
                .setMessage("Are you sure you want to delete this account?.")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    databaseHelper.deleteUserData(fullname);
                    goToAdmin();
                })
                .setNegativeButton("No", (dialog, which) -> { });
        adb.show();
    }

    public void goToAdmin() {
        Intent iAdmin = new Intent(this, AdminActivity.class);
        startActivity(iAdmin);
        finish();
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }


}