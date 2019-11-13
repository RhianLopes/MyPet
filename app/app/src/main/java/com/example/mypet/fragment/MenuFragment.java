package com.example.mypet.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mypet.R;

public class MenuFragment extends AppCompatActivity {

    private ListView lvMenu;
    private String[] vetMenu = {"Yor profile", "Edit your profile", "Delete post"};
    private FragmentManager fm = getSupportFragmentManager();
    private Long idPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_fragment);

        this.lvMenu = findViewById(R.id.lv_menu);

        idPet = getIntent().getLongExtra("id", 0);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_menu, R.id.textView, vetMenu);
        this.lvMenu.setAdapter(adapter);

        this.lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment frag;
                FragmentTransaction ft;
                Bundle b = new Bundle();
                switch (position) {
                    case 0:
                        frag = new ProfileFragment();
                        ft = fm.beginTransaction();
                        ft.addToBackStack(null);
                        b.putLong("id", idPet);
                        frag.setArguments(b);
                        ft.replace(R.id.linear_content, frag);
                        ft.commit();
                        break;
                    case 1:
                        frag = new EditProfileFragment();
                        ft = fm.beginTransaction();
                        ft.addToBackStack(null);
                        b.putLong("id", idPet);
                        frag.setArguments(b);
                        ft.replace(R.id.linear_content, frag);
                        ft.commit();
                        break;
                    case 2:
                        frag = new DeletePostFragment();
                        ft = fm.beginTransaction();
                        ft.addToBackStack(null);
                        b.putLong("id", idPet);
                        frag.setArguments(b);
                        ft.replace(R.id.linear_content, frag);
                        ft.commit();
                        break;



                }
            }
        });




    }
}
