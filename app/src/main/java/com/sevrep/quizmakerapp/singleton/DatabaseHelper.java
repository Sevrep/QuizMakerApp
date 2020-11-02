package com.sevrep.quizmakerapp.singleton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context c;

    private static final String DB_NAME = "myDB.db";
    private static final String TABLE_USERS = "tblUserAccount";
    private static final String TABLE_SUBJECT = "tblSubject";

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,7);
        c = context;
    }

    public void onCreate(SQLiteDatabase db){
        try {
            String sqlQuery = "CREATE TABLE " + TABLE_USERS + "(fullname text primary key, username text, password text, type text)";
            db.execSQL(sqlQuery);
            Toast.makeText(c, "Users table created successfully.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("DATABASEHELPER ", "Users table creation error.", e);
        }
        try {
            db.execSQL("CREATE TABLE " + TABLE_SUBJECT + "(subjectid integer primary key autoincrement, subjectname text, subjectteacher text)");
            Toast.makeText(c, "Subject table created successfully.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("DATABASEHELPER ", "Subject table creation error.", e);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        onCreate(db);
    }

    /**USER*/
    public Cursor getUserData(String fullname){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_USERS+" WHERE fullname ='"+fullname+"' ",null);
        c.moveToFirst();
        return c;
    }

    public Cursor getFullnameData(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_USERS+" WHERE username ='"+username+"' ",null);
        c.moveToFirst();
        return c;
    }

    public Cursor getAllUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_USERS,null);
        c.moveToFirst();
        return c;
    }

    public void createUser(String fullname, String username, String password, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fullname", fullname);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("type", type);
        db.insert(TABLE_USERS,null,cv);
    }

    public boolean checkUsernameDuplicate(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " +TABLE_USERS+ " WHERE username = '" + username + "' ";
        Cursor c = db.rawQuery(sqlQuery,null);
        if(c.getCount() != 0){
            return true;
        }
        c.close();
        return false;
    }

    public boolean checkFullnameDuplicate(String fullname) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " +TABLE_USERS+ " WHERE fullname = '" + fullname + "' ";
        Cursor c = db.rawQuery(sqlQuery,null);
        if(c.getCount() != 0){
            return true;
        }
        c.close();
        return false;
    }

    public boolean checkUsernameType(String username, String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " +TABLE_USERS+ " WHERE username = '" +username+ "' AND type = '" +type+ "' ";
        Cursor c = db.rawQuery(sqlQuery,null);
        if(c.getCount() != 0){
            return true;
        }
        c.close();
        return false;
    }

    public boolean loginUser(String username, String password, String type){
        SQLiteDatabase db= this.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " +TABLE_USERS+ " WHERE username = '" +username+ "' AND password = '" +password+ "' AND type = '" +type+ "' ";
        Cursor c = db.rawQuery(sqlQuery,null);
        if(c.getCount() != 0){
            return true;
        }
        c.close();
        return false;
    }

    public void updateUserData(String fullname, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("password",password);
        db.update(TABLE_USERS,cv,"fullname = '" + fullname + "' ",null);
    }

    public void deleteUserData(String fullname){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS,"fullname = '" + fullname + "' ",null);
        // TODO delete all entries in other tables with given full name
    }

    /**SUBJECT*/
    public Cursor getAllSubjects(String subjectteacher){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+ TABLE_SUBJECT +" WHERE subjectteacher = '"+subjectteacher+"' ",null);
        c.moveToFirst();
        return c;
    }

    public void createSubject(String subjectname, String subjectteacher){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subjectname", subjectname);
        cv.put("subjectteacher", subjectteacher);
        db.insert(TABLE_SUBJECT, null, cv);
        Toast.makeText(c, subjectname+ " added to database.", Toast.LENGTH_LONG).show();
    }

    public Cursor getSubjectData(int subjectid) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_SUBJECT + " WHERE subjectid ='" + subjectid + "' ",null);
        c.moveToFirst();
        return c;
    }
}
