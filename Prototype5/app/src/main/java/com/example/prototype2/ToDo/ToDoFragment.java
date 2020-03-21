package com.example.prototype2.ToDo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prototype2.Calendar.PlanListAdapter;
import com.example.prototype2.R;
import com.example.prototype2.Room.Plan;
import com.example.prototype2.sharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToDoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoFragment extends Fragment implements ViewPager.OnPageChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String selectedTabName;
    private List<Plan> myDataset = new ArrayList<>();
    final PlanListAdapter mAdapter=new PlanListAdapter(myDataset);
    private sharedViewModel mPlanViewModel;

    private static Context context = null;
    private static View todoView;

    public ToDoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToDoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToDoFragment newInstance(String param1, String param2) {
        ToDoFragment fragment = new ToDoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //テスト用カテゴリを作成し保存
//        SharedPreferences data = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        data.edit().putString("category",gson.toJson(ToDoPlan.category)).apply();

        //データ読み出し
        //System.out.println(gson.fromJson(data.getString("category",""), ArrayList.class));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);
        context=getActivity();
        todoView=view;
        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager)view.findViewById(R.id.pager);
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                // 画面に表示するフラグメントの取得
                return ToDoContentsFragment.newInstance(position + 1);
            }

            @Override
            public CharSequence getPageTitle(int position){
                // カテゴリのデータ
                // カテゴリのデータ
                //return ToDoPlan.category.get(position);
                SharedPreferences data = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
                Gson gson = new Gson();
                ArrayList<String> arrayList = gson.fromJson(data.getString("category",""), ArrayList.class);
                return arrayList.get(position);
            }

            @Override
            public int getCount() {
                SharedPreferences data = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
                Gson gson = new Gson();
                ArrayList<String> arrayList = gson.fromJson(data.getString("category",""), ArrayList.class);
                return arrayList.size();
            }

            @Override
            public int getItemPosition(Object item) {
//                MyFragment fragment = (MyFragment)item;
//                String title = fragment.getTitle();
//                int position = titles.indexOf(title);
                System.out.println("notifyDataSetChangedが呼ばれるたびにこのメソッドが呼ばれている・・・？");
//                if (position >= 0) {
//                    return position;
//                } else {
//                    return POSITION_NONE;
//                }
                return POSITION_UNCHANGED;
                //return  POSITION_NONE;
            }


        };
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);


        // 以下現在表示されているタブを取ってくるコード
        selectedTabName =(String)tabLayout.getTabAt(0).getText();
        System.out.println("しののん"+selectedTabName);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTabName = (String)tab.getText();
                System.out.println("selectedTabName"+selectedTabName);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //floatingActionButtonの動作
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButtonT);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("access",1);
                bundle.putString("category",selectedTabName);
//                Navigation.findNavController(view).navigate(R.id.action_calendarDetailFragment_to_editPlan,bundle);
                Navigation.findNavController(view).navigate(R.id.action_toDoFragment_to_editPlan,bundle);
            }

        });
//        FloatingActionButton floatingActionButton=view.findViewById(R.id.floatingActionButtonT2);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
    }

    @Override
    public void onPageSelected(int position){
        Log.d("ToDoFragment","onPageSelected() position="+position);
    }

    @Override
    public void onPageScrollStateChanged(int state){}

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
