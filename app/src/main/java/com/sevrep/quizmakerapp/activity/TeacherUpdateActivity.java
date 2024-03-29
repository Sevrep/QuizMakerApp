package com.sevrep.quizmakerapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.adapter.QuestionsAdapter;
import com.sevrep.quizmakerapp.model.Questions;
import com.sevrep.quizmakerapp.singleton.DatabaseHelper;
import com.sevrep.quizmakerapp.singleton.SharedPrefHandler;

import java.util.ArrayList;
import java.util.List;

public class TeacherUpdateActivity extends AppCompatActivity implements View.OnClickListener, QuestionsAdapter.OnItemClickListener {

    private FloatingActionButton fab_add_question, fab1_trueorfalse, fab2_multiple;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private TextView textview_trueorfalse, textview_multiple;
    private int subjectId;

    private Boolean fabMenuIsOpen = false;

    private SharedPrefHandler sharedPrefHandler;
    private DatabaseHelper databaseHelper;
    private Cursor c;

    private RecyclerView recyclerView;
    private List<Questions> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_update);

        sharedPrefHandler = new SharedPrefHandler(this);
        subjectId = Integer.parseInt(sharedPrefHandler.getSharedPref("subjectid"));

        databaseHelper = new DatabaseHelper(this);
        c = databaseHelper.getSubjectData(subjectId);

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

        recyclerView = findViewById(R.id.rv_teacher_update_questionlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        questionsList = new ArrayList<>();

        loadQuestions();

    }

    @Override
    public void onBackPressed() {
        goToTeacher();
    }

    @Override
    public void onClick(View v) {
        int objectId = v.getId();
        if (objectId == R.id.fab_add_question) {
            if (fabMenuIsOpen) {
                hideFabMenu();
            } else {
                openFabMenu();
            }
        } else if (objectId == R.id.fab1_trueorfalse) {
            openAddTrueFalseDialog();
            hideFabMenu();
        } else if (objectId == R.id.fab2_multiple) {
            openAddMultipleChoiceDialog();
            hideFabMenu();
        } else {
            throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    @Override
    public void onItemClick(int position) {
        Questions clickedQuestion = questionsList.get(position);
        int pageQuestionId = clickedQuestion.getQuestionid();

        Intent iQuestionEdit = new Intent(this, TeacherUpdateQuestionActivity.class);
        iQuestionEdit.putExtra("extraQuestionId", String.valueOf(pageQuestionId));
        startActivity(iQuestionEdit);
    }

    private void openAddMultipleChoiceDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(TeacherUpdateActivity.this);
        View popupMultipleChoice = layoutInflater.inflate(R.layout.activity_teacher_update_multiplechoice_dialog, null);
        EditText edtQuestion = popupMultipleChoice.findViewById(R.id.edtQuestion);
        RadioButton rdbA = popupMultipleChoice.findViewById(R.id.radio_button_a);
        RadioButton rdbB = popupMultipleChoice.findViewById(R.id.radio_button_b);
        RadioButton rdbC = popupMultipleChoice.findViewById(R.id.radio_button_c);
        RadioButton rdbD = popupMultipleChoice.findViewById(R.id.radio_button_d);
        EditText edtChoiceA = popupMultipleChoice.findViewById(R.id.edtChoiceA);
        EditText edtChoiceB = popupMultipleChoice.findViewById(R.id.edtChoiceB);
        EditText edtChoiceC = popupMultipleChoice.findViewById(R.id.edtChoiceC);
        EditText edtChoiceD = popupMultipleChoice.findViewById(R.id.edtChoiceD);

        c = databaseHelper.getSubjectData(subjectId);
        AlertDialog.Builder adb = new AlertDialog.Builder(TeacherUpdateActivity.this);
        adb.setTitle(c.getString(c.getColumnIndex("subjectname")));
        adb.setCancelable(false);
        adb.setView(popupMultipleChoice);
        adb.setPositiveButton("Add", (dialog1, which) -> {
            String questiontext = edtQuestion.getText().toString().trim();
            String questiontexta = edtChoiceA.getText().toString().trim();
            String questiontextb = edtChoiceB.getText().toString().trim();
            String questiontextc = edtChoiceC.getText().toString().trim();
            String questiontextd = edtChoiceD.getText().toString().trim();
            String questiontype = "multiplechoice";
            String questionanswer = "";
            int subjectid = subjectId;
            String fullname = c.getString(c.getColumnIndex("subjectteacher"));

            if (rdbA.isChecked()) {
                questionanswer = "a";
            }
            if (rdbB.isChecked()) {
                questionanswer = "b";
            }
            if (rdbC.isChecked()) {
                questionanswer = "c";
            }
            if (rdbD.isChecked()) {
                questionanswer = "d";
            }

            if (TextUtils.isEmpty(questiontext)) {
                customToast("Enter a question.");
            } else {
                customToast(questiontext + "\n"
                        + questiontexta + "\n"
                        + questiontextb + "\n"
                        + questiontextc + "\n"
                        + questiontextd + "\n"
                        + "ANSWER: " + questionanswer + "\n"
                        + "TYPE: " + questiontype + "\n"
                        + "TOPIC: " + subjectid + "\n"
                        + "TEACHER: " + fullname + "\n"
                );
                databaseHelper.createQuestion(questiontext, questiontexta, questiontextb, questiontextc, questiontextd, questionanswer, questiontype, subjectid, fullname);
                c = databaseHelper.getQuestionData(questiontext);
                int questionid = c.getInt(c.getColumnIndex("questionid"));
                databaseHelper.addQuestionToSubject(questionid, subjectid, fullname);
                loadQuestions();
            }
        });
        adb.setNegativeButton("Cancel", null);
        adb.create();
        adb.show();
    }

    public void openAddTrueFalseDialog() {
        c = databaseHelper.getSubjectData(subjectId);
        AlertDialog.Builder adb = new AlertDialog.Builder(TeacherUpdateActivity.this);
        adb.setTitle(c.getString(c.getColumnIndex("subjectname")));
        adb.setCancelable(false);

        LayoutInflater layoutInflater = LayoutInflater.from(TeacherUpdateActivity.this);
        View popupTrueFalse = layoutInflater.inflate(R.layout.activity_teacher_update_truefalse_dialog, null);
        EditText edtQuestion = popupTrueFalse.findViewById(R.id.edtQuestion);
        RadioButton rbnTrue = popupTrueFalse.findViewById(R.id.rbnTrue);
        RadioButton rbnFalse = popupTrueFalse.findViewById(R.id.rbnFalse);

        adb.setView(popupTrueFalse);
        adb.setPositiveButton("Add", (dialog1, which) -> {

            String questiontext = edtQuestion.getText().toString().trim();
            String questiontype = "trueorfalse";
            String questionanswer = "";
            int subjectid = subjectId;
            String fullname = c.getString(c.getColumnIndex("subjectteacher"));

            if (rbnTrue.isChecked()) {
                questionanswer = "true";
            }
            if (rbnFalse.isChecked()) {
                questionanswer = "false";
            }
            if (TextUtils.isEmpty(questiontext)) {
                customToast("Enter a question.");
            } else {
                databaseHelper.createQuestion(questiontext, null, null, null, null, questionanswer, questiontype, subjectid, fullname);
                c = databaseHelper.getQuestionData(questiontext);
                int questionid = c.getInt(c.getColumnIndex("questionid"));
                databaseHelper.addQuestionToSubject(questionid, subjectid, fullname);
                loadQuestions();
            }
        });
        adb.setNegativeButton("Cancel", null);
        adb.create();
        adb.show();
    }

    private void loadQuestions() {
        questionsList.clear();
        Cursor cursor = databaseHelper.getAllQuestionsInThisSubject(subjectId);
        if (cursor.moveToFirst()) {
            do {
                Questions questions = new Questions(
                        cursor.getInt(cursor.getColumnIndex("questionid")),
                        cursor.getString(cursor.getColumnIndex("questiontext")),
                        cursor.getString(cursor.getColumnIndex("questiontexta")),
                        cursor.getString(cursor.getColumnIndex("questiontextb")),
                        cursor.getString(cursor.getColumnIndex("questiontextc")),
                        cursor.getString(cursor.getColumnIndex("questiontextd")),
                        cursor.getString(cursor.getColumnIndex("questionanswer")),
                        cursor.getString(cursor.getColumnIndex("questiontype")),
                        cursor.getInt(cursor.getColumnIndex("subjectid")),
                        cursor.getString(cursor.getColumnIndex("fullname"))
                );
                questionsList.add(questions);
            } while (cursor.moveToNext());
        }

        QuestionsAdapter questionsAdapter = new QuestionsAdapter(this, questionsList);
        recyclerView.setAdapter(questionsAdapter);
        QuestionsAdapter.setOnItemClickListener(this);
    }

    public void hideFabMenu() {
        textview_trueorfalse.setVisibility(View.INVISIBLE);
        textview_multiple.setVisibility(View.INVISIBLE);
        fab2_multiple.startAnimation(fab_close);
        fab1_trueorfalse.startAnimation(fab_close);
        fab_add_question.startAnimation(fab_anticlock);
        fab2_multiple.setClickable(false);
        fab1_trueorfalse.setClickable(false);
        fabMenuIsOpen = false;
    }

    public void openFabMenu() {
        textview_trueorfalse.setVisibility(View.VISIBLE);
        textview_multiple.setVisibility(View.VISIBLE);
        fab2_multiple.startAnimation(fab_open);
        fab1_trueorfalse.startAnimation(fab_open);
        fab_add_question.startAnimation(fab_clock);
        fab2_multiple.setClickable(true);
        fab1_trueorfalse.setClickable(true);
        fabMenuIsOpen = true;
    }

    public void goToTeacher() {
        sharedPrefHandler.removeSharedPref("subjectid");
        Intent iTeacher = new Intent(this, TeacherActivity.class);
        startActivity(iTeacher);
        finish();
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }

}