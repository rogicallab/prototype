package com.example.prototype2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bottomNavigationViewのセッティング
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.calendarFragment, R.id.toDoFragment, R.id.settingFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // テスト
        //new ToDoPlan();
        // テスト用カテゴリを作成し保存
        sharedViewModel.category.add("カテゴリ１");
        sharedViewModel.category.add("カテゴリ2");
        sharedViewModel.category.add("カテゴリ3");

        SharedPreferences data = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        data.edit().putString("category",gson.toJson(sharedViewModel.category)).apply();

    }
}
