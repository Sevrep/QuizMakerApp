package com.sevrep.quizmakerapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sevrep.quizmakerapp.MainActivity;
import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.adapter.AdminAppUserAdapter;
import com.sevrep.quizmakerapp.model.AdminAppUser;
import com.sevrep.quizmakerapp.singleton.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity implements AdminAppUserAdapter.OnItemClickListener{

    private List<AdminAppUser> adminAppUserList;
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar_sign);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.rv_admin_userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        adminAppUserList = new ArrayList<>();

        loadAdminAppUsers();
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

    @Override
    public void onItemClick(int position) {
        Intent iUpdate = new Intent(this, AdminUpdateActivity.class);
        AdminAppUser clickedAppUser = adminAppUserList.get(position);
        iUpdate.putExtra("extra_fullname", clickedAppUser.getFullname());
        startActivity(iUpdate);
        finish();
    }

    private void loadAdminAppUsers() {
        adminAppUserList.clear();
        Cursor cursor = databaseHelper.getAllUsers();
        if (cursor.moveToFirst()) {
            do {
                AdminAppUser adminAppUser = new AdminAppUser(
                        cursor.getString(cursor.getColumnIndex("fullname")),
                        cursor.getString(cursor.getColumnIndex("username")),
                        cursor.getString(cursor.getColumnIndex("password")),
                        cursor.getString(cursor.getColumnIndex("type"))
                );
                adminAppUserList.add(adminAppUser);
            } while (cursor.moveToNext());
        }

        AdminAppUserAdapter adminAppUserAdapter = new AdminAppUserAdapter(this, adminAppUserList);
        recyclerView.setAdapter(adminAppUserAdapter);
        AdminAppUserAdapter.setOnItemClickListener(this);
    }

    public void goToMain() {
        Intent iMain = new Intent(this, MainActivity.class);
        startActivity(iMain);
        finish();
    }

}