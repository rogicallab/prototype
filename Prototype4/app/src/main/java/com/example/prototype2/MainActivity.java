package com.example.prototype2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.example.prototype2.ToDo.ToDoContentsFragment;
import com.example.prototype2.ToDo.ToDoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity  implements ToDoFragment.OnFragmentInteractionListener, ToDoContentsFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
//        setSupportActionBar(myToolbar);

        // bottomNavigationViewのセッティング
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.calendarFragment, R.id.toDoFragment, R.id.settingFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // テスト用カテゴリを作成し保存
        sharedViewModel.category.add("カテゴリ１");
        sharedViewModel.category.add("カテゴリ2");
        sharedViewModel.category.add("カテゴリ3");

        SharedPreferences data = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        data.edit().putString("category",gson.toJson(sharedViewModel.category)).apply();

    }
    @Override
    public void onFragmentInteraction(Uri uri){

    }
}
