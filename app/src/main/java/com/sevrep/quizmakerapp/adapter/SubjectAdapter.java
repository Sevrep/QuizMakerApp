package com.sevrep.quizmakerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.model.Subject;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>{

    private final Context mCtx;
    private final List<Subject> subjectList;
    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static void setOnItemClickListener(SubjectAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public SubjectAdapter(Context mCtx, List<Subject> subjectList) {
        this.mCtx = mCtx;
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_teacher_list_layout, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.SubjectViewHolder holder, int position) {

        Subject subject = subjectList.get(position);

        holder.tv_listlayout_subject_name.setText(subject.getSubjectname());

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {

        TextView tv_listlayout_subject_name;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_listlayout_subject_name = itemView.findViewById(R.id.tv_listlayout_subject_name);

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
