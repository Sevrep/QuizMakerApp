package com.sevrep.quizmakerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.model.Questions;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {

    private final Context mCtx;
    private final List<Questions> questionsList;
    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static void setOnItemClickListener(QuestionsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public QuestionsAdapter(Context mCtx, List<Questions> questionsList) {
        this.mCtx = mCtx;
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public QuestionsAdapter.QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.activity_teacher_update_list_layout, parent, false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsAdapter.QuestionsViewHolder holder, int position) {
        Questions questions = questionsList.get(position);
        holder.tv_update_listlayout_question_text.setText(questions.getQuestiontext());
        String answer = "ANSWER: " + questions.getQuestionanswer();
        holder.tv_update_listlayout_question_answer.setText(answer);
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_update_listlayout_question_text, tv_update_listlayout_question_answer;

        public QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_update_listlayout_question_text = itemView.findViewById(R.id.tv_update_listlayout_question_text);
            tv_update_listlayout_question_answer = itemView.findViewById(R.id.tv_update_listlayout_question_answer);

            itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }
}
