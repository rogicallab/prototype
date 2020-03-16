package com.example.prototype2.ToDo;

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

import com.example.prototype2.R;
import com.example.prototype2.sharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);
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
                //return ToDoPlan.category.get(position);
                return sharedViewModel.category.get(position);
            }

            @Override
            public int getCount() {
                return sharedViewModel.category.size();
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);

        //floatingActionButtonの動作
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButtonT);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle=new Bundle();
//                bundle.putInt("year",year);
//                bundle.putInt("month",month);
//                bundle.putInt("day",day);
//                bundle.putInt("access",0);
//                Navigation.findNavController(view).navigate(R.id.action_calendarDetailFragment_to_editPlan,bundle);
                Navigation.findNavController(view).navigate(R.id.action_toDoFragment_to_editPlan);
            }

        });

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
