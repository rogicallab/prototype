package com.example.prototype2;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.prototype2.Room.Plan;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPlan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPlan extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Integer id=null;
    private int judge;
    private sharedViewModel mPlanViewModel;
    private String category="";
    //カレンダーから渡された日付

    private EditText mTitle;
    private EditText mMemo;
    private Spinner cSpinner;

    private Spinner nSpinner;
    private String[] notificationItems = {
            "選択してください",
            "1時間前",
            "2時間前",
            "1日前",
            "2日前",
            "1週間前"
};
    private int nSpinnerPlace=0;
    //設定する日付
    private DatePicker datePicker;
    private Integer year=-1;
    private Integer month=-1;
    private Integer day=-1;
    //時間
    private TimePicker timePicker;
    private Integer hour=-1;
    private Integer minute=-1;

    private String plantitle="";
    private String memo="";
    int currentApiVersion=Build.VERSION.SDK_INT;
    private String notification="";
    private Plan currentPlan;

    public EditPlan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPlan.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPlan newInstance(String param1, String param2) {
        EditPlan fragment = new EditPlan();
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
        }System.out.println(hour+" "+minute);
        try{
            judge=getArguments().getInt("access");//0:カレンダーからの作成、1:TODOからの作成、2:予定タップで予定変更
            year = getArguments().getInt("year");
            month = getArguments().getInt("month");
            day = getArguments().getInt("day");
            if(judge==2) {
                plantitle = getArguments().getString("planName");
                hour = getArguments().getInt("hour");
                minute = getArguments().getInt("minute");
                notification = getArguments().getString("notification");
                memo = getArguments().getString("memo");
                id = getArguments().getInt("id");
                System.out.println(id + " IDdesu");
                System.out.println(hour + " " + minute);
//
                for (int i = 0; i < notificationItems.length; i++) {
                    if (notification.equals(notificationItems[i])) {
                        nSpinnerPlace = i;
                    }
                }
            }
            System.out.println(year+" "+month+" "+day);
        System.out.println(plantitle);
        }catch (NullPointerException e){
            System.out.println(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit_plan, container, false);
        mPlanViewModel = new ViewModelProvider(this).get(sharedViewModel.class);
//        if(judge==2) {
//            mPlanViewModel.findById(id).observe(getViewLifecycleOwner(), new Observer<Plan>() {
//                @Override
//                public void onChanged(Plan plan) {
//                    setAll(plan, plan.getCategory(), plan.getYear(), plan.getMonth(), plan.getDay(), plan.getHours(), plan.getMinute(), plan.getNotification(), plan.getMemo());
//                }
//            });
//        }

        //タイトル
        mTitle=view.findViewById(R.id.titleInput);
        mTitle.setText(plantitle);
        //メモ
        mMemo=view.findViewById(R.id.memoInput);
        mMemo.setText(memo);

        //カテゴリーのスピナー
        cSpinner=(Spinner)view.findViewById(R.id.spinner);

        ArrayAdapter<String>adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, notificationItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //通知のスピナー
        nSpinner=(Spinner)view.findViewById(R.id.notifications);
        nSpinner.setAdapter(adapter);
        nSpinner.setSelection(nSpinnerPlace);
        nSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                notification = (String)spinner.getSelectedItem();
                System.out.println(notification);
                //textView.setText(item);
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        //DatePicker
        datePicker=view.findViewById(R.id.datePicker);

        //DatePicker
        Switch switch1=(Switch)view.findViewById(R.id.switch1);

        //カレンダーからのアクセスとTodoからのアクセスをif文で表示非表示分ける
        if(year==-1&&month==-1&&day==-1){
            datePicker.setVisibility(View.GONE);
        }else{
            datePicker.setVisibility(View.VISIBLE);
            datePicker.updateDate(year,month,day);
            switch1.setChecked(isAdded());
        }
        //DatePicker表示非表示
        switch1.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            datePicker.setVisibility(View.VISIBLE);
                        }else{
                            datePicker.setVisibility(View.INVISIBLE);
                        }
                    }
                });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int cyear, int monthOfYear, int dayOfMonth) {
                    year=cyear;
                    month=monthOfYear;
                    day=dayOfMonth-1;
                }
            });
        }
        System.out.println(hour+" "+minute);
        //Timepicker
        timePicker=view.findViewById(R.id.timePicker);
        //TimePicker表示非表示
        Switch switch2=(Switch)view.findViewById(R.id.switch2);
        if(hour==-1&&minute==-1){
            timePicker.setVisibility(View.GONE);
        }else{
            timePicker.setVisibility(View.VISIBLE);
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
            switch2.setChecked(isAdded());
        }
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    timePicker.setVisibility(View.VISIBLE);
                    timePicker.setHour(0);
                    timePicker.setMinute(0);
                }else{
                    timePicker.setVisibility(View.INVISIBLE);
                    hour=-1;
                    minute=-1;

                }
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int cminute) {
                hour=hourOfDay-1;
                minute=cminute;
            }
        });
        
        //予定作成ボタン
        try {
            Button button = view.findViewById(R.id.createPlan);
            if(judge!=0&&judge!=1){
                button.setText("予定変更");
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(mTitle.getText())) {//NullPointerException
                        Toast.makeText(getContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
                    } else {
                        plantitle = mTitle.getText().toString();
//                        if(datePicker.isShown()){
//                        year=datePicker.getYear();
//                        month=datePicker.getMonth();
//                        day=datePicker.getDayOfMonth();}
                        System.out.println(year+" "+month+" "+day);
//                        if(timePicker.isShown()){
//                        if(currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1){
//                            hour=timePicker.getHour();
//                            minute=timePicker.getMinute();
//                        }else{
//                            hour=timePicker.getCurrentHour();
//                            minute=timePicker.getCurrentMinute();
//                            }
//                        }
                        memo=mMemo.getText().toString();
                        if(judge!=2) {
                            Plan plan = new Plan(plantitle, category, year, month, day, hour, minute, notification, memo);
                            mPlanViewModel.insert(plan);
                        }else{
                            currentPlan=new Plan(plantitle, category, year, month, day, hour, minute, notification, memo);
                            currentPlan.setId(id);
                            mPlanViewModel.updatePlan(currentPlan);
                        }
                    }
                    Navigation.findNavController(view).navigate(R.id.action_editPlan_to_calendarFragment);
                }

            });
        }catch (NullPointerException e){
            System.out.println(e);
        }
        // Inflate the layout for this fragment
        return view;
    }
//    public void setAll(Plan plan,String category,int year,int month,int day,int hour,int minute,String notification,String memo){
//            this.currentPlan=plan;
//            this.category=category;
//            this.year=year;
//            this.month=month;
//            this.day=day;
//            this.hour=hour;
//            this.minute=minute;
//            this.notification=notification;
//            this.memo=memo;
//    }
}
