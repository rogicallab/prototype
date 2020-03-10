package com.example.prototype2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.prototype2.ToDo.ToDoContentsFragment;
import com.example.prototype2.ToDo.ToDoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements ToDoFragment.OnFragmentInteractionListener, ToDoContentsFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //floatingボタンでtodoを表示することにした。ひとまずなので後で消す
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ToDoFragment fragment = new ToDoFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });

        // テスト
        //new ToDoPlan();
        // テスト用カテゴリを作成し保存
        sharedViewModel.category.add("カテゴリ１");
        sharedViewModel.category.add("カテゴリ2");
        //sharedViewModel.category.add("カテゴリ3");

        SharedPreferences data = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        data.edit().putString("category",gson.toJson(sharedViewModel.category)).apply();

    }
    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
