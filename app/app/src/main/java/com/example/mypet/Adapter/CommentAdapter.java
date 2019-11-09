package com.example.mypet.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mypet.Comment;
import com.example.mypet.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<Comment> commentArrayList;
    private Context context;

    public CommentAdapter(ArrayList<Comment> commentArrayList, Context context) {
        this.commentArrayList = commentArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.comment.setText(commentArrayList.get(position).getContent());
        holder.petname.setText(commentArrayList.get(position).getPet().getName());
    }

    @Override
    public int getItemCount() {
        return this.commentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView comment = itemView.findViewById(R.id.tv_comment);
        private TextView petname = itemView.findViewById(R.id.tv_pet_name);



        public ViewHolder(View view){
            super(view);
        }
    }
}
