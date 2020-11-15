package com.sevrep.quizmakerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.model.Subject;

import java.util.List;

public class StudentSubjectAdapter extends RecyclerView.Adapter<StudentSubjectAdapter.StudentSubjectViewHolder>{

    private final Context mCtx;
    private final List<Subject> subjectList;
    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onHighScoreClick(int position);
        void onItemClick(int position);
    }

    public static void setOnItemClickListener(StudentSubjectAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public StudentSubjectAdapter(Context mCtx, List<Subject> subjectList) {
        this.mCtx = mCtx;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public StudentSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.activity_student_list_layout, parent, false);
        return new StudentSubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentSubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.tv_listlayout_subject_name.setText(subject.getSubjectname());
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class StudentSubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tv_listlayout_subject_name;
        ImageView iv_high_score;
        public StudentSubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_listlayout_subject_name = itemView.findViewById(R.id.tv_listlayout_subject_name);
            iv_high_score = itemView.findViewById(R.id.iv_high_score);
            iv_high_score.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onHighScoreClick(position);
                    }
                }
            });
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
