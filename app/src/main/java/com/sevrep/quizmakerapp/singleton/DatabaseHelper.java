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
    private static final String TABLE_QUESTION_SUBJECT = "tblQuestionSubject";
    private static final String TABLE_QUESTION = "tblQuestion";

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,11);
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
        try {
            db.execSQL("CREATE  TABLE " + TABLE_QUESTION_SUBJECT + "(questionsubjectid integer primary key autoincrement, questionid integer, subjectid integer, fullname text)");
            Toast.makeText(c, "Question subject table created successfully.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("DATABASEHELPER ", "Question subject table creation error.", e);
        }
        try {
            db.execSQL("CREATE  TABLE " + TABLE_QUESTION + "(questionid integer primary key unique, questiontext text, questiontexta text, questiontextb text, questiontextc text, questiontextd text, questionanswer text, questiontype text, subjectid integer, fullname text)");
            Toast.makeText(c, "Question table created successfully.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("DATABASEHELPER ", "Question table creation error.", e);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
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

    public void updateSubject(int subjectid, String subjectname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subjectname", subjectname);
        db.update(TABLE_SUBJECT,cv,"subjectid = '" + subjectid + "' ",null);
        Toast.makeText(c, "Subject " + subjectid + " updated successfully.", Toast.LENGTH_LONG).show();
    }

    public void deleteSubject(int subjectid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SUBJECT,"subjectid = '" + subjectid + "' ",null);
    }


    /**QUESTION SUBJECT*/
    public void addQuestionToSubject(int questionid, int subjectid, String fullname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("questionid", questionid);
        cv.put("subjectid", subjectid);
        cv.put("fullname", fullname);
        db.insert(TABLE_QUESTION_SUBJECT, null, cv);
        Toast.makeText(c, "New question: " + questionid + "\nadded to subject: " + subjectid + "\nby: " + fullname + ".", Toast.LENGTH_LONG).show();
    }

    public Cursor getAllQuestionsInThisSubject(int subjectid) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "
                + TABLE_QUESTION + " INNER JOIN "
                + TABLE_QUESTION_SUBJECT + " ON "
                + TABLE_QUESTION + ".questionid = " + TABLE_QUESTION_SUBJECT + ".questionid WHERE "
                + TABLE_QUESTION_SUBJECT + ".subjectid = '" + subjectid + "' ",null);
        c.moveToFirst();
        return c;
    }


    /**QUESTION*/
    public void createQuestion(String questiontext, String questiontexta, String questiontextb, String questiontextc, String questiontextd, String questionanswer, String questiontype, int subjectid, String fullname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("questiontext", questiontext);
        cv.put("questiontexta", questiontexta);
        cv.put("questiontextb", questiontextb);
        cv.put("questiontextc", questiontextc);
        cv.put("questiontextd", questiontextd);
        cv.put("questionanswer", questionanswer);
        cv.put("questiontype", questiontype);
        cv.put("subjectid", subjectid);
        cv.put("fullname", fullname);
        db.insert(TABLE_QUESTION, null, cv);
        Toast.makeText(c, "Question added to database.", Toast.LENGTH_LONG).show();
    }

    public Cursor getQuestionData(String questiontext) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_QUESTION + " WHERE questiontext ='" + questiontext + "' ",null);
        c.moveToFirst();
        return c;
    }

    public Cursor getQuestionDataById(int questionid) {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_QUESTION + " WHERE questionid ='" + questionid + "' ",null);
        c.moveToFirst();
        return c;
    }

    public void updateQuestionMC(int questionid, String questiontext, String questiontexta, String questiontextb, String questiontextc, String questiontextd, String questionanswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("questiontext", questiontext);
        cv.put("questiontexta", questiontexta);
        cv.put("questiontextb", questiontextb);
        cv.put("questiontextc", questiontextc);
        cv.put("questiontextd", questiontextd);
        cv.put("questionanswer", questionanswer);
        db.update(TABLE_QUESTION,cv,"questionid = '" + questionid + "' ",null);
        Toast.makeText(c, "Question " + questionid + " updated successfully.", Toast.LENGTH_LONG).show();
    }

    public void updateQuestionTF(int questionid, String questiontext, String questionanswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("questiontext", questiontext);
        cv.put("questionanswer", questionanswer);
        db.update(TABLE_QUESTION,cv,"questionid = '" + questionid + "' ",null);
        Toast.makeText(c, "Question " + questionid + " updated successfully.", Toast.LENGTH_LONG).show();
    }
}
