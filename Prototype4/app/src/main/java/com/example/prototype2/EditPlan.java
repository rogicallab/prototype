package com.example.prototype2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;


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
    private int cSpinnerPlace;
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
    private AlarmManager am;
    private PendingIntent pending;
    private int requestCode = 1;

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
        Gson gson = new Gson();
        SharedPreferences data = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        List<String> clist=gson.fromJson(data.getString("category",""), ArrayList.class);
        System.out.println("おおおおおおおおおおおお");
        try{
            judge=getArguments().getInt("access");//0:カレンダーからの作成、1:TODOからの作成、2:予定タップで予定変更

            if(judge==0||judge==2){
            year = getArguments().getInt("year");
            month = getArguments().getInt("month");
            day = getArguments().getInt("day");}
            if(judge==0){
                category="カテゴリなし";
                for(int i=0;i<clist.size();i++){
                    if(category.equals(clist.get(i))){
                        cSpinnerPlace=i;
                        break;
                    }
                }
            }
            if(judge==1||judge==2) {
                category = getArguments().getString("category");
                System.out.println(category + "  editPlanCategory");
                for(int i=0;i<clist.size();i++){
                    System.out.println(clist.get(i)+"　現在のカテゴリ");
                    if(category.equals(clist.get(i))){
                        cSpinnerPlace=i;
                        break;
                    }
                }
            }
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
/**以下カテゴリをjsonからとってきてスピナーで表示するものです
 *         //カテゴリーのスピナー
 *         cSpinner=(Spinner)view.findViewById(R.id.spinner);
 *　の部分を置き換えてください
 * それとprivate String cItemを追加しました
 *
 * arraylisr<String> category= gson.fromJson(data.getString("category",""), ArrayList.class)
 * category.size()
 *
 * */
        SharedPreferences data = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        ArrayAdapter<ArrayList<String>>c_adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, gson.fromJson(data.getString("category",""), ArrayList.class));
        c_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cSpinner=(Spinner)view.findViewById(R.id.spinner);
        cSpinner.setAdapter(c_adapter);
        cSpinner.setSelection(cSpinnerPlace);
        cSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //アイテムが選択された
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner)adapterView;
                category = (String)spinner.getSelectedItem();
                System.out.println("選択されたカテゴリ"+category);
            }
            //アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String>adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, notificationItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //通知のスピナー
        nSpinner=(Spinner)view.findViewById(R.id.notifications);
        nSpinner.setAdapter(adapter);
        nSpinner.setSelection(nSpinnerPlace);
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
                    hour=0;
                    minute=0;
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
        nSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                notification = (String)spinner.getSelectedItem();
                System.out.println(notification);
                if(position!=0){
                    switch1.setChecked(true);
                    switch2.setChecked(true);
                }
                //textView.setText(item);
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
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
                        Toast.makeText(getContext(),"タイトルが未入力です。", Toast.LENGTH_LONG).show();
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
                        if(nSpinner.getSelectedItemPosition()!=0&&!(switch1.isChecked()&&switch2.isChecked())){
                            Toast.makeText(getContext(),"日時を選択してください",Toast.LENGTH_LONG).show();
                            switch1.setChecked(true);
                            switch2.setChecked(true);
                        }else {
                            if (judge != 2) {
                                Plan plan = new Plan(plantitle, category, year, month, day, hour, minute, notification, memo);
                                mPlanViewModel.insert(plan);
                            } else {
                                currentPlan = new Plan(plantitle, category, year, month, day, hour, minute, notification, memo);
                                currentPlan.setId(id);
                                mPlanViewModel.updatePlan(currentPlan);
                            }
                            if(!notification.equals("選択してください")){
//                                hour+=1;
//                                minute-=1;
                                Calendar calendar = Calendar.getInstance();
//                                 calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.set(year,month,day,hour,(minute-1),0);
                                switch (notification){
                                    case "1時間前":
                                        calendar.add(Calendar.HOUR,-1);
                                        System.out.println(year+" "+month+" "+day+" "+hour+" "+(minute-1)+"の１時間前に通知");
                                        break;
                                    case "2時間前":
                                        calendar.add(Calendar.HOUR,-2);
                                        break;
                                    case "1日前":
                                        calendar.add(Calendar.DATE,-1);
                                        System.out.println(year+" "+month+" "+day+" "+hour+" "+(minute-1)+"の1日前に通知");
                                        break;
                                    case"2日前":
                                        calendar.add(Calendar.DATE,-2);
                                        break;
                                    case"1週間前":
                                        calendar.add(Calendar.DATE,-7);
                                        System.out.println(year+" "+month+" "+day+" "+hour+" "+(minute-1)+"の１週間前に通知");
                                        break;
                                    default:
                                        break;
                                }

                                Intent intent = new Intent(getActivity().getApplicationContext(), AlarmNotification.class);
                                intent.putExtra("RequestCode",requestCode);
                                intent.putExtra("title",plantitle);

                                pending = PendingIntent.getBroadcast(getActivity().getApplicationContext(),requestCode,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                                // アラームをセット
                                am = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);

                                if(am != null){
                                    am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending);
                                    // アラームが設定されたことを表示
                                    Toast.makeText(getActivity().getApplicationContext(),"alarm start",Toast.LENGTH_SHORT).show();

                                    Log.d("debug", "start");
                                }

                            }else{
                                if(judge==2){
                                Intent indent = new Intent(getActivity().getApplicationContext(), AlarmNotification.class);
                                PendingIntent pending = PendingIntent.getBroadcast(
                                        getActivity().getApplicationContext(), requestCode, indent, 0);

                                // アラームを解除する
                                AlarmManager am = (AlarmManager)getActivity().
                                        getSystemService(ALARM_SERVICE);
                                if (am != null) {
                                    am.cancel(pending);
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "alarm cancel", Toast.LENGTH_SHORT).show();
                                    Log.d("debug", "cancel");
                                }
                                else{
                                    Log.d("debug", "null");
                                }}
                            }
                            }

                            Navigation.findNavController(view).navigate(R.id.action_editPlan_to_calendarFragment);
                        }
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
