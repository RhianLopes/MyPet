package com.example.mypet.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mypet.model.Comment;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_comment_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.comment.setText(commentArrayList.get(position).getContent());
        holder.petname.setText(commentArrayList.get(position).getPet().getName());
    }

    @Override
    public int getItemCount() {
        return this.commentArrayList.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView comment = itemView.findViewById(R.id.tv_content);
        private TextView petname = itemView.findViewById(R.id.tv_pet_name);



        public ViewHolder(View view){
            super(view);
        }
    }
}
