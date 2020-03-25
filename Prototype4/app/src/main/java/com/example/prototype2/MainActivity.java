package com.example.prototype2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.example.prototype2.Calendar.CalendarFragment;
import com.example.prototype2.Setting.ColorSetting;
import com.example.prototype2.ToDo.ToDoContentsFragment;
import com.example.prototype2.ToDo.ToDoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import static android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM;
import static android.view.MenuItem.SHOW_AS_ACTION_NEVER;

public class MainActivity extends AppCompatActivity implements ToDoFragment.OnFragmentInteractionListener, ToDoContentsFragment.OnFragmentInteractionListener{

    private int itemId ;
    private Fragment selectedFragment;
    private BottomNavigationView bnv;
    private Toolbar toolbar;




    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnv=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        switch (defaultSharedPreferences.getString("preference_theme", getString(R.string.default_value_preference_theme))) {
            case "light":
                setTheme(R.style.AppTheme);
                bnv.setBackgroundColor(R.color.colorAccent);
                toolbar.setBackgroundColor(R.color.colorAccent);
                break;
            case "dark":
                setTheme(R.style.AppThemeDark);
                bnv.setBackgroundColor(R.color.colorBlack);
                toolbar.setBackgroundColor(R.color.colorBlack);
                break;
        }

        // bottomNavigationのセッティング
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        View calendarItem = findViewById(R.id.calendarFragment);
        View toDoItem = findViewById(R.id.toDoFragment);
        View settingItem = findViewById(R.id.settingFragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                System.out.println("onDestinationChangedよばれてんねぇ！");
                System.out.println("destination"+destination);
                switch (destination.getId()){
                    case R.id.calendarFragment:
                        itemId=calendarItem.getId();
                        invalidateOptionsMenu();
                        break;
                    case R.id.toDoFragment:
                        itemId = toDoItem.getId();
                        invalidateOptionsMenu();
                        break;
                    case R.id.settingFragment:
                        itemId = settingItem.getId();
                        invalidateOptionsMenu();
                        break;

                }

            }
        });

        NavigationUI.setupWithNavController(navView, navController);

        // テスト用カテゴリを作成し保存
        sharedViewModel.category.add("カテゴリ１");
        sharedViewModel.category.add("カテゴリ2");
        sharedViewModel.category.add("カテゴリ3");
        sharedViewModel.category.add("カテゴリなし");

        SharedPreferences data = getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        data.edit().putString("category",gson.toJson(sharedViewModel.category)).apply();

        // toolbarのため
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


    }
    @Override
    public void onFragmentInteraction(Uri uri){
    }
    // オプションメニューを指定するためにonCreateOptionsMenuをオーバーライド
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("Menu:"+menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tab_menu, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(itemId == R.id.calendarFragment){
            System.out.println("更新します");
            menu.getItem(0).setShowAsAction(SHOW_AS_ACTION_NEVER);
        }
        switch (itemId){
            case R.id.calendarFragment:
            case R.id.settingFragment:
                menu.getItem(0).setShowAsAction(SHOW_AS_ACTION_NEVER);
                break;
            case R.id.toDoFragment:
                menu.getItem(0).setShowAsAction(SHOW_AS_ACTION_IF_ROOM);
                break;
        }
        return true;
    }
    // action barのため
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("呼ばれてるよ");
        System.out.println("item:");
        System.out.println(item);
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_add_tab:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                System.out.println("タブ追加ボタンが押された");
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.editTabFragment);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onStart(){
        super.onStart();
        // レイアウトルートの背景をテーマ設定の値によって変更
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        switch (defaultSharedPreferences.getString("preference_theme", getString(R.string.default_value_preference_theme))) {
            case "light":
                setTheme(R.style.AppTheme);
                bnv.setBackgroundColor(getColor(R.color.colorAccent));
                toolbar.setBackgroundColor(getColor(R.color.colorAccent));
                bnv.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
                break;
            case "dark":
                setTheme(R.style.AppThemeDark);
                bnv.setBackgroundColor(Color.BLACK);
                toolbar.setBackgroundColor(Color.BLACK);
                toolbar.setTitleTextColor(Color.WHITE);
                bnv.setItemTextColor(ColorStateList.valueOf(getColor(R.color.colorP)));
                break;
        }

    }



}
