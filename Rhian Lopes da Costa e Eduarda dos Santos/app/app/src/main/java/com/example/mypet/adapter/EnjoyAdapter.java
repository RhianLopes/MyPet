package com.example.mypet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mypet.R;
import com.example.mypet.model.Enjoy;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EnjoyAdapter extends RecyclerView.Adapter<EnjoyAdapter.ViewHolder> {

    private ArrayList<Enjoy> enjoyArrayList;
    private Context context;

    public EnjoyAdapter(ArrayList<Enjoy> enjoyArrayList, Context context) {
        this.enjoyArrayList = enjoyArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public EnjoyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_enjoy_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.petname.setText(enjoyArrayList.get(position).getPet().getName());
    }


    @Override
    public int getItemCount() {
        return this.enjoyArrayList.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {



        TextView petname = itemView.findViewById(R.id.pet_name);



        ViewHolder(View view){
            super(view);
        }
    }
}
