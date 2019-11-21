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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypet.model.Pet;
import com.example.mypet.PetService;
import com.example.mypet.R;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

public class EditProfileFragment extends Fragment {

    private TextView tvLogo;
    private TextView tvPhoto;
    private TextView tvDescription;
    @NotEmpty(message = "Required field")
    @Length(max = 1000)
    private EditText etPhoto;
    @NotEmpty(message = "Required field")
    @Length(max = 280)
    private EditText etDescription;
    private Button btEditPet;
    private SharedPreferences sharedPreferences;
    private String token;
    private Retrofit retrofit;
    private Long petId;
    private String name;
    private String specie;
    private String genre;
    private Pet pet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_edit_profile_fragment, null);

        inicilizeComponents();

        sharedPreferences = getActivity().getSharedPreferences("MyPet", 0);
        token = sharedPreferences.getString("token", "erro");

        Bundle bundle = this.getArguments();
        petId = bundle.getLong("id");

        tvLogo = v.findViewById(R.id.tv_logo);
        tvPhoto = v.findViewById(R.id.tv_photo);
        tvDescription = v.findViewById(R.id.tv_description);
        etPhoto = v.findViewById(R.id.et_photo);
        etDescription = v.findViewById(R.id.et_description);
        btEditPet = v.findViewById(R.id.bt_edit_pet);



        PetService petService = retrofit.create(PetService.class);

        petService.find(petId, token).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                pet = response.body();
                if( pet!= null){
                    etPhoto.setText(pet.getPhoto());
                    etDescription.setText(pet.getDescription());
                    name = pet.getName();
                    specie = pet.getSpecie();
                    genre = pet.getGenre();

                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                Toast.makeText(getActivity(), "We had problems to loading the petfile, try again later!", Toast.LENGTH_SHORT).show();
            }
        });

        btEditPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pet pet = new Pet();
                pet.setName(name);
                pet.setDescription(etDescription.getText().toString());
                pet.setPhoto(etPhoto.getText().toString());
                pet.setGenre(genre);
                pet.setSpecie(specie);
                pet.setId(petId);

                PetService petService1 = retrofit.create(PetService.class);
                petService1.edit(pet, token).enqueue(new Callback<Pet>() {
                    @Override
                    public void onResponse(Call<Pet> call, Response<Pet> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getActivity(), "Pet successfully edited!", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getActivity(), "Sorry, we had problems to edit the pet. Try again later!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Pet> call, Throwable t) {
                        Toast.makeText(getActivity(), "Sorry, we had internal problems! Try again later.", Toast.LENGTH_SHORT).show();
                    }
                });

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
