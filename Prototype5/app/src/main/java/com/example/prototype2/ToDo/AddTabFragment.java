package com.example.prototype2.ToDo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.prototype2.R;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String inputedValue;

    public AddTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTabFragment newInstance(String param1, String param2) {
        AddTabFragment fragment = new AddTabFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_tab, container, false);
        Button button = view.findViewById(R.id.addNewTab);
        EditText editText = view.findViewById(R.id.NewTabName);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputedValue=charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        System.out.println("inputedValue:"+inputedValue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("inputedValue:"+inputedValue);
                if(inputedValue.equals(null)){
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.editTabFragment);
                }else{
                    SharedPreferences data = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    ArrayList<String> arrayList = gson.fromJson(data.getString("category",""), ArrayList.class);
                    arrayList.add(arrayList.size()-1,inputedValue);
                    data.edit().putString("category",gson.toJson(arrayList)).apply();
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.editTabFragment);

                }


//                SharedPreferences data = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
//                Gson gson = new Gson();
//                ArrayList<String> arrayList = gson.fromJson(data.getString("category",""), ArrayList.class);
//
//                arrayList.add(arrayList.size()-1,inputedValue);
//                System.out.println("newArrayList:"+gson.fromJson(data.getString("category",""), ArrayList.class));
            }
        });
        //sharedPreferences.edit().putString("NewTagName",view.findViewById(R.id.NewTabName).toString()).apply();

        return view;
    }
}
