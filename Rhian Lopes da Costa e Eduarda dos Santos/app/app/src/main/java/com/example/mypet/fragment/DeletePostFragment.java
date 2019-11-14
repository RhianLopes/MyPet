package com.example.mypet.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet.PetService;
import com.example.mypet.PostService;
import com.example.mypet.R;
import com.example.mypet.activity.TimelineActivity;
import com.example.mypet.adapter.PetPostAdapter;
import com.example.mypet.adapter.PostAdapter;
import com.example.mypet.model.Pet;
import com.example.mypet.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeletePostFragment extends Fragment {

    private TextView textInformation;
    private RecyclerView rvPost;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;
    private Long petId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_delete_post_fragment, null);

        inicilizeComponents();


        rvPost = v.findViewById(R.id.posts);

        Bundle bundle = this.getArguments();
        petId = bundle.getLong("id");

        sharedPreferences = getActivity().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        PostService postService = retrofit.create(PostService.class);

        postService.findAllByPetId(petId, token).enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if(response.isSuccessful()){
                    ArrayList<Post> postArrayList = response.body();

                    rvPost.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvPost.setAdapter(new PetPostAdapter(postArrayList, getActivity()));

                } else {
                    Toast.makeText(getActivity(), "We had problems to loading posts, try again later!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Toast.makeText(getActivity(), "try again later!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void inicilizeComponents(){

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/my-pet/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }





}
