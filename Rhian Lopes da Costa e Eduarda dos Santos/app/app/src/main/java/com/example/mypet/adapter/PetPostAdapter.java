package com.example.mypet.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet.PostService;
import com.example.mypet.R;
import com.example.mypet.activity.ListLikesActivity;
import com.example.mypet.model.Post;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.core.content.ContextCompat.startActivity;

public class PetPostAdapter extends RecyclerView.Adapter<PetPostAdapter.ViewHolder>  {

    private ArrayList<Post> postArrayList;
    private Context context;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;
    private AdapterView.OnItemClickListener clickListener;


    public PetPostAdapter(ArrayList<Post> postArrayList, Context context) {
        this.postArrayList = postArrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_post_adapter, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.description.setText(postArrayList.get(position).getDescription());
        Picasso.with(context).load(postArrayList.get(position).getPhotos()).into(holder.link);
        holder.numberLikes.setText(postArrayList.get(position).getAmountEnjoy() + " likes");
        holder.numberLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itListLikes = new Intent(context, ListLikesActivity.class);
                Long idPost = postArrayList.get(position).getId();
                itListLikes.putExtra("idPost", idPost);
                Bundle extras = itListLikes.getExtras();
                extras.putLong("idPost", idPost);
                startActivity(context, itListLikes, extras);
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("Authorization", token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                }).build();
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl("http://10.0.2.2:8000/my-pet/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                sharedPreferences = context.getApplicationContext().getSharedPreferences("MyPet", 0);
                token = sharedPreferences.getString("token", "erro");

                Long idPost = postArrayList.get(position).getId();

                PostService postService = retrofit.create(PostService.class);

                postService.delete(idPost).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Post was delete successfully.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context, "Sorry! We had problems, try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.postArrayList.size();
    }









    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView description = itemView.findViewById(R.id.description);
        ImageView link = itemView.findViewById(R.id.photo);
        TextView numberLikes = itemView.findViewById(R.id.likes);
        Button btDelete = itemView.findViewById(R.id.delete_post);






        public ViewHolder(View view){
            super(view);
        }
    }


}