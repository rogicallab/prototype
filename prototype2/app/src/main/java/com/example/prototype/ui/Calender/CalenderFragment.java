package com.example.prototype.ui.Calender;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.prototype.R;

public class CalenderFragment extends Fragment {
    private CalenderViewModel calenderViewModel;
    private FragmentManager fragmentManager;
    private boolean isShowFragment=false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calenderViewModel =
                ViewModelProviders.of(this).get(CalenderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calender, container, false);

        fragmentManager=getChildFragmentManager();
        final Fragment newFragment=new CalenderDetailFragment();
        CalendarView.OnDateChangeListener listener = new CalendarView.OnDateChangeListener() {
//
//            /**
//             * 日付部分タップ時に実行される処理
//             * @param view 押下されたカレンダーのインスタンス
//             * @param year タップされた日付の「年」
//             * @param month タップされた日付の「月」※月は0月から始まるから、+1して調整が必要
//             * @param dayOfMonth タップされた日付の「日」
//             */
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                CalenderFragmentDirections.ActionNavigationHomeToCalenderDetailFragment action =
//                        CalenderFragmentDirections.actionNavigationHomeToCalenderDetailFragment(year,month,dayOfMonth);
//                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_calenderDetailFragment);

//                Bundle bundle = new Bundle();
//                bundle.putInt("YEAR", year);
//                bundle.putInt("MONTH",month+1);
//                bundle.putInt("DAY",dayOfMonth);
//                Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_calenderDetailFragment, bundle);

                CalenderFragmentDirections.ActionNavigationHomeToCalenderDetailFragment action=CalenderFragmentDirections.actionNavigationHomeToCalenderDetailFragment(year,month,dayOfMonth);
                Navigation.findNavController(root).navigate(action);

//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.container, CalenderDetailFragment.newInstance()).commit();
            }
        };
        // CalendarViewにリスナーを設定
        ((CalendarView) root.findViewById(R.id.calendarView)).setOnDateChangeListener(listener);
        return root;
    }

}