package com.example.prototype;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.prototype.Room.Plan;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditPlan.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditPlan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPlan extends Fragment {
    private sharedViewModel mPlanViewModel;
    EditText mTitle;
    EditText mMemo;
    Spinner spinner;
    DatePicker datePicker;
    int year;
    int month;
    int day;
    TimePicker timePicker;
    int hour;
    int minute;
    int currentApiVersion = Build.VERSION.SDK_INT;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPlanViewModel = new ViewModelProvider(this).get(sharedViewModel.class);
        View view=inflater.inflate(R.layout.fragment_edit_plan, container, false);
        mTitle=view.findViewById(R.id.titleInput);
        mMemo=view.findViewById(R.id.memoInput);

        //スピナー
        spinner=(Spinner)view.findViewById(R.id.spinner);


        //DatePicker
        datePicker=view.findViewById(R.id.datePicker);
        datePicker.setVisibility(View.GONE);//カレンダーからのアクセスとTodoからのアクセスをif文で表示非表示分ける

        //DatePicker表示非表示
        Switch switch1=(Switch)view.findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    datePicker.setVisibility(View.VISIBLE);
                    year=datePicker.getYear();
                    month=datePicker.getMonth();
                    day=datePicker.getDayOfMonth();
                }else{
                    datePicker.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Timepicker
        timePicker=view.findViewById(R.id.timePicker);
        timePicker.setVisibility(View.GONE);
        //TimePicker表示非表示
        Switch switch2=(Switch)view.findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    timePicker.setVisibility(View.VISIBLE);
                    if(currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1){
                        hour=timePicker.getHour();
                        minute=timePicker.getMinute();
                    }else{
                        hour=timePicker.getCurrentHour();
                        minute=timePicker.getCurrentMinute();
                    }
                }else{
                    timePicker.setVisibility(View.INVISIBLE);
                }
            }
        });

        //予定作成ボタン
        try {
            Button button = view.findViewById(R.id.createPlan);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(mTitle.getText())) {//NullPointerException
                        Toast.makeText(getContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
                    } else {
                        String plantitle = mTitle.getText().toString();
                        Plan plan = new Plan(plantitle);
                        mPlanViewModel.insert(plan);
                    }
                    getActivity().getSupportFragmentManager().popBackStack();
                }

            });
        }catch (NullPointerException e){
            getActivity().getSupportFragmentManager().popBackStack();
        }
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionEdit(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteractionEdit(Uri uri);
    }
}
