package com.example.mypet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mypet.R;
import com.example.mypet.activity.FollowerActivity;
import com.example.mypet.model.Follower;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FollowerAdapter  extends RecyclerView.Adapter<FollowerAdapter.ViewHolder> {

    private ArrayList<Follower> followerArrayList;
    private Context context;

    public FollowerAdapter(ArrayList<Follower> followerArrayList, FollowerActivity context) {
        this.followerArrayList = followerArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FollowerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_follower_adapter, parent, false);
        return new FollowerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowerAdapter.ViewHolder holder, final int position) {

        holder.petname.setText(followerArrayList.get(position).getPetFollower().getName());



    }

    @Override
    public int getItemCount() {
        return this.followerArrayList.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView petname = itemView.findViewById(R.id.tv_pet_name);



        public ViewHolder(View view){
            super(view);
        }
    }
}
