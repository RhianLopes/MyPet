package com.example.mypet.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet.model.Pet;
import com.example.mypet.PetService;
import com.example.mypet.R;
import com.mobsandgeeks.saripaar.Validator;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private ImageView ivPhoto;
    private TextView tvName;
    private Button btFollowing;
    private Button btFollowers;
    private TextView tvDescription;
    private Retrofit retrofit;
    private SharedPreferences sharedPreferences;
    private String token;
    private Long petId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile_fragment, null);

        inicilizeComponents();

        ivPhoto = v.findViewById(R.id.iv_photo);
        tvName = v.findViewById(R.id.pet_name);
        btFollowers = v.findViewById(R.id.bt_follower);
        btFollowing = v.findViewById(R.id.bt_following);
        tvDescription = v.findViewById(R.id.tv_description);

        Bundle bundle = this.getArguments();
        petId = bundle.getLong("id");

        sharedPreferences = getActivity().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");
        PetService petService = retrofit.create(PetService.class);

        petService.find(petId, token).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if(response.isSuccessful()){
                    Pet pet = response.body();
                            tvDescription.setText(pet.getDescription());
                            tvName.setText(pet.getName());
                            Picasso.with(ivPhoto.getContext()).load(pet.getPhoto()).into(ivPhoto);

                    }else{
                        Toast.makeText(getActivity(),"We had problems to loading the petfile, try again later!",Toast.LENGTH_SHORT).show();
                    }
                }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(getActivity(),"Sorry, we had internal problems! Try again later.",Toast.LENGTH_SHORT).show();
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
