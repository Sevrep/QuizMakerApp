package com.sevrep.quizmakerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sevrep.quizmakerapp.R;
import com.sevrep.quizmakerapp.model.AdminAppUser;

import java.util.List;

public class AdminAppUserAdapter extends RecyclerView.Adapter<AdminAppUserAdapter.AdminAppUserViewHolder> {

    private final Context mCtx;
    private final List<AdminAppUser> adminAppUserList;

    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdminAppUserAdapter(Context mCtx, List<AdminAppUser> adminAppUserList) {
        this.mCtx = mCtx;
        this.adminAppUserList = adminAppUserList;
    }

    @NonNull
    @Override
    public AdminAppUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.activity_admin_list_layout, parent, false);
        return new AdminAppUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAppUserViewHolder holder, int position) {
        AdminAppUser adminAppUser = adminAppUserList.get(position);

        holder.tv_fullname.setText(adminAppUser.getFullname());
        holder.tv_username.setText(adminAppUser.getUsername());
        holder.tv_password.setText(adminAppUser.getPassword());
        holder.tv_type.setText(adminAppUser.getType());

    }

    @Override
    public int getItemCount() {
        return adminAppUserList.size();
    }

    static class AdminAppUserViewHolder extends RecyclerView.ViewHolder {

        TextView tv_fullname, tv_username, tv_password, tv_type;

        public AdminAppUserViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_fullname = itemView.findViewById(R.id.tv_listlayout_fullname);
            tv_username = itemView.findViewById(R.id.tv_listlayout_username);
            tv_password = itemView.findViewById(R.id.tv_listlayout_password);
            tv_type = itemView.findViewById(R.id.tv_listlayout_type);

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
