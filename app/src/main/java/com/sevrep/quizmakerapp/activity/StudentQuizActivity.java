package com.sevrep.quizmakerapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.model.Questions;
import com.sevrep.quizmakerapp.singleton.DatabaseHelper;
import com.sevrep.quizmakerapp.singleton.SharedPrefHandler;

import java.util.ArrayList;
import java.util.List;

public class StudentQuizActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layoutTF;
    private ConstraintLayout layoutMC;

    private int subjectId;
    private int indexQ;
    private int USER_PROGRESS;
    private int studentScore;
    private String scoreText = "Score: ";

    private TextView tvQuizQuestion;
    private TextView tvRemaining;
    private ProgressBar progressBar;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;


    private SharedPrefHandler sharedPrefHandler;
    private DatabaseHelper databaseHelper;

    private List<Questions> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_quiz);

        TextView toolbar_student_quiz_title = findViewById(R.id.toolbar_title);
        tvQuizQuestion = findViewById(R.id.textViewQuizQuestion);
        progressBar = findViewById(R.id.pb_quizprogressbar);
        tvRemaining = findViewById(R.id.tv_remaining);
        layoutTF = findViewById(R.id.layoutTF);
        layoutMC = findViewById(R.id.layoutMC);
        Button buttonTrue = findViewById(R.id.buttonTrue);
        Button buttonFalse = findViewById(R.id.buttonFalse);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);

        buttonTrue.setOnClickListener(this);
        buttonFalse.setOnClickListener(this);
        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);

        sharedPrefHandler = new SharedPrefHandler(this);
        subjectId = Integer.parseInt(sharedPrefHandler.getSharedPref("subjectid"));

        databaseHelper = new DatabaseHelper(this);
        Cursor c = databaseHelper.getSubjectData(subjectId);

        String studentQuizTitle = "QUIZ: " + c.getString(c.getColumnIndex("subjectname"));
        toolbar_student_quiz_title.setText(studentQuizTitle);

        questionsList = new ArrayList<>();
        prepareQuestions();

        Questions tanong = questionsList.get(indexQ);

        tvQuizQuestion.setText(tanong.getQuestiontext());
        USER_PROGRESS = (int) Math.ceil(100.0 / questionsList.size());
        tvRemaining.setText(scoreText);

        updateChoices();
    }

    private void updateChoices() {
        Questions tanong = questionsList.get(indexQ);
        String questiontype = tanong.getQuestiontype();
        switch (questiontype) {
            case "trueorfalse":
                layoutTF.setVisibility(View.VISIBLE);
                layoutMC.setVisibility(View.GONE);
                break;
            case "multiplechoice":
                layoutMC.setVisibility(View.VISIBLE);
                layoutTF.setVisibility(View.GONE);
                String cA = "A: " + tanong.getQuestiontexta();
                buttonA.setText(cA);
                String cB = "B: " + tanong.getQuestiontextb();
                buttonB.setText(cB);
                String cC = "C: " + tanong.getQuestiontextc();
                buttonC.setText(cC);
                String cD = "D: " + tanong.getQuestiontextd();
                buttonD.setText(cD);
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        int objectId = v.getId();
        if (objectId == R.id.buttonTrue) {
            evaluateAnswer("true");
            updateQuestion();
            updateChoices();
        } else if (objectId == R.id.buttonFalse) {
            evaluateAnswer("false");
            updateQuestion();
            updateChoices();
        } else if (objectId == R.id.buttonA) {
            evaluateAnswer("a");
            updateQuestion();
            updateChoices();
        } else if (objectId == R.id.buttonB) {
            evaluateAnswer("b");
            updateQuestion();
            updateChoices();
        } else if (objectId == R.id.buttonC) {
            evaluateAnswer("c");
            updateQuestion();
            updateChoices();
        } else if (objectId == R.id.buttonD) {
            evaluateAnswer("d");
            updateQuestion();
            updateChoices();
        } else {
            throw new IllegalStateException("Unexpected value: " + v.getId());
        }

    }

    @Override
    public void onBackPressed() {
        goToStudent();
    }

    private void updateQuestion() {
        indexQ = (indexQ + 1) % questionsList.size();
        if (indexQ == 0) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setCancelable(false);
            adb.setTitle("The quiz is finished.");
            adb.setMessage("Your score is " + studentScore + "/" + questionsList.size());
            adb.setPositiveButton("Finish the quiz", (dialog, which) -> {
                customToast("Congratulations!!!");
                goToStudent();
            });
            adb.show();
        } else {
            Questions tanong = questionsList.get(indexQ);
            tvQuizQuestion.setText(tanong.getQuestiontext());
            progressBar.incrementProgressBy(USER_PROGRESS);
            scoreText = "Score: " + studentScore + "/" + questionsList.size();
            tvRemaining.setText(scoreText);
        }

    }

    private void evaluateAnswer(String userGuess) {
        Questions tanong = questionsList.get(indexQ);
        String sagot = tanong.getQuestionanswer();
        if (sagot.equals(userGuess)) {
            customToast("Great!");
            studentScore++;
        } else {
            String angSagotAy = "Not good!" + "\n"
                    + "The correct answer is: " + tanong.getQuestionanswer();
            customToast(angSagotAy);
        }
    }

    private void prepareQuestions() {
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
    }

    public void goToStudent() {
        sharedPrefHandler.removeSharedPref("subjectid");
        Intent iStudent = new Intent(this, StudentActivity.class);
        startActivity(iStudent);
        finish();
    }

    public void customToast(String mensahe) {
        Toast.makeText(this, mensahe, Toast.LENGTH_SHORT).show();
    }

}