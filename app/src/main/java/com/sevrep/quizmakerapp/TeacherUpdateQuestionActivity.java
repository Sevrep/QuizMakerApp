package com.sevrep.quizmakerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.sevrep.quizmakerapp.singleton.DatabaseHelper;

public class TeacherUpdateQuestionActivity extends AppCompatActivity {

    private ConstraintLayout constraint_trueorfalse;
    private ConstraintLayout constraint_multiplechoice;

    private int questionid;
    private String questiontype;
    private String questionanswer;

    private EditText edtQuestion;
    private RadioButton rbnTrue;
    private RadioButton rbnFalse;
    private RadioButton rdbA;
    private RadioButton rdbB;
    private RadioButton rdbC;
    private RadioButton rdbD;
    private EditText edtChoiceA;
    private EditText edtChoiceB;
    private EditText edtChoiceC;
    private EditText edtChoiceD;

    private DatabaseHelper databaseHelper;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_update_question);

        questionid = Integer.parseInt(getIntent().getExtras().getString("extraQuestionId"));
        databaseHelper = new DatabaseHelper(this);
        c = databaseHelper.getQuestionDataById(questionid);
        questiontype = c.getString(c.getColumnIndex("questiontype"));

        constraint_trueorfalse = findViewById(R.id.constraint_trueorfalse);
        constraint_multiplechoice = findViewById(R.id.constraint_multiplechoice);
        rbnTrue = findViewById(R.id.rbnTrue);
        rbnFalse = findViewById(R.id.rbnFalse);
        edtQuestion = findViewById(R.id.edtQuestion);
        rdbA = findViewById(R.id.radio_button_a);
        rdbB = findViewById(R.id.radio_button_b);
        rdbC = findViewById(R.id.radio_button_c);
        rdbD = findViewById(R.id.radio_button_d);
        edtChoiceA = findViewById(R.id.edtChoiceA);
        edtChoiceB = findViewById(R.id.edtChoiceB);
        edtChoiceC = findViewById(R.id.edtChoiceC);
        edtChoiceD = findViewById(R.id.edtChoiceD);

        edtQuestion.setText(c.getString(c.getColumnIndex("questiontext")));

        switch (questiontype) {
            case "trueorfalse": showTF(); break;
            case "multiplechoice": showMC(); break;
            default:
        }

    }

    private void showMC() {
        constraint_trueorfalse.setVisibility(View.GONE);
        constraint_multiplechoice.setVisibility(View.VISIBLE);
        edtChoiceA.setText(c.getString(c.getColumnIndex("questiontexta")));
        edtChoiceB.setText(c.getString(c.getColumnIndex("questiontextb")));
        edtChoiceC.setText(c.getString(c.getColumnIndex("questiontextc")));
        edtChoiceD.setText(c.getString(c.getColumnIndex("questiontextd")));
        questionanswer = c.getString(c.getColumnIndex("questionanswer"));
        switch (questionanswer) {
            case "a": rdbA.setChecked(true); break;
            case "b": rdbB.setChecked(true); break;
            case "c": rdbC.setChecked(true); break;
            case "d": rdbD.setChecked(true); break;
            default:
        }

    }

    private void showTF() {
        constraint_trueorfalse.setVisibility(View.VISIBLE);
        constraint_multiplechoice.setVisibility(View.GONE);
        questionanswer = c.getString(c.getColumnIndex("questionanswer"));
        switch (questionanswer) {
            case "true": rbnTrue.setChecked(true); break;
            case "false": rbnFalse.setChecked(true); break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        goToTeacherUpdate();
    }

    public void goToTeacherUpdate() {
        Intent iTeacherUpdate = new Intent(this, TeacherUpdateActivity.class);
        startActivity(iTeacherUpdate);
        finish();
    }
}