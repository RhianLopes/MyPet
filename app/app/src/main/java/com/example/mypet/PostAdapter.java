package com.example.mypet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.startActivity;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> postArrayList;
    private Context context;

    public PostAdapter(ArrayList<Post> postArrayList, Context context) {
        this.postArrayList = postArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.description.setText(postArrayList.get(position).getDescription());
        holder.petname.setText(postArrayList.get(position).getPet().getName());
        Picasso.with(context).load(postArrayList.get(position).getPhotos()).into(holder.link);
        holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itProfile = new Intent(context, ProfileScreen.class);
                Long idPet = postArrayList.get(position).getPet().getId();
                itProfile.putExtra("id", idPet);
                Bundle extras = itProfile.getExtras();
                extras.putLong("id", idPet);




                startActivity(context, itProfile, extras);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.postArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView description = itemView.findViewById(R.id.description);
        private ImageView link = itemView.findViewById(R.id.photo);
        private TextView petname = itemView.findViewById(R.id.petName);
        private Button like = itemView.findViewById(R.id.like);
        private Button comment = itemView.findViewById(R.id.comment);
        private Button profile = itemView.findViewById(R.id.profile);


        public ViewHolder(View view){
            super(view);
        }
    }
}
