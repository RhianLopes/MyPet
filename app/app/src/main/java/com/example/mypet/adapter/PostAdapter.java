package com.example.mypet.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet.EnjoyService;
import com.example.mypet.activity.ListLikesActivity;
import com.example.mypet.model.Enjoy;
import com.example.mypet.model.Pet;
import com.example.mypet.model.Post;
import com.example.mypet.R;
import com.example.mypet.activity.CommentActivity;
import com.example.mypet.activity.ProfileActivity;
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

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> postArrayList;
    private Context context;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;
    private Long idPet;

    public PostAdapter(ArrayList<Post> postArrayList, Context context, Long idPet) {
        this.postArrayList = postArrayList;
        this.context = context;
        this.idPet = idPet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.description.setText(postArrayList.get(position).getDescription());
        holder.petname.setText(postArrayList.get(position).getPet().getName());
        Picasso.with(context).load(postArrayList.get(position).getPhotos()).into(holder.link);
        holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itProfile = new Intent(context, ProfileActivity.class);
                Long idPet = postArrayList.get(position).getPet().getId();
                itProfile.putExtra("id", idPet);
                Bundle extras = itProfile.getExtras();
                extras.putLong("id", idPet);
                startActivity(context, itProfile, extras);
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itComment = new Intent(context, CommentActivity.class);
                Long idPet = postArrayList.get(position).getPet().getId();
                Long idPost = postArrayList.get(position).getId();
                itComment.putExtra("id", idPet);
                itComment.putExtra("idPost", idPost);
                Bundle extras = itComment.getExtras();
                extras.putLong("id", idPet);
                extras.putLong("idPost", idPost);
                startActivity(context, itComment, extras);
            }
        });
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
        holder.like.setOnClickListener(new View.OnClickListener() {
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
                Enjoy enjoy = new Enjoy();
                enjoy.setPet(new Pet());
                enjoy.getPet().setId(idPet);
                enjoy.setPost(new Post());
                enjoy.getPost().setId(idPost);

                EnjoyService enjoyService = retrofit.create(EnjoyService.class);

                enjoyService.register(enjoy).enqueue(new Callback<Enjoy>() {
                    @Override
                    public void onResponse(Call<Enjoy> call, retrofit2.Response<Enjoy> response) {
                        if(response.isSuccessful()){
                            holder.like.setText("Liked");
                            holder.numberLikes.setText(postArrayList.get(position).getAmountEnjoy()+ 1 + " likes");
                            holder.numberLikes.setText(postArrayList.get(position).getAmountEnjoy() + " likes");


                        } else {
                            Toast.makeText(context, "Sorry! We had problems, try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Enjoy> call, Throwable t) {
                        Toast.makeText(context, "Sorry, we had internal problems! Try again later.", Toast.LENGTH_SHORT).show();
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

        private TextView description = itemView.findViewById(R.id.description);
        private ImageView link = itemView.findViewById(R.id.photo);
        private TextView petname = itemView.findViewById(R.id.likes);
        private Button like = itemView.findViewById(R.id.delete_post);
        private TextView numberLikes = itemView.findViewById(R.id.number_likes);
        private Button comment = itemView.findViewById(R.id.comment);
        private Button profile = itemView.findViewById(R.id.profile);





        public ViewHolder(View view){
            super(view);
        }
    }


}
