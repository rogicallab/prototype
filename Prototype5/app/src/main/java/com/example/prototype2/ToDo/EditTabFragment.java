package com.example.prototype2.ToDo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prototype2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTabFragment extends Fragment implements TextWatcher {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public EditTabFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditTabFragment newInstance(String param1, String param2) {
        EditTabFragment fragment = new EditTabFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_tab, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);

        SharedPreferences data = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        ArrayList<String> arrayList = gson.fromJson(data.getString("category",""), ArrayList.class);

        View itemView = inflater.inflate(R.layout.row_item, container, false);
        EditText editText = (EditText)itemView.findViewById(R.id.text);
        editText.addTextChangedListener(this);


        final CustomAdapter adapter = new CustomAdapter(getContext(), R.layout.row_item, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(arrayList.get(position).equals("カテゴリなし")){
                    Toast.makeText(getContext(),"カテゴリなしは削除できません",Toast.LENGTH_LONG).show();
                }else{
                    switch (view.getId()) {
                        case R.id.delite:
                            System.out.println("parent:"+parent);
                            System.out.println("View");
                            // arrayList.add("addedTab");
                            arrayList.remove(position);
                            adapter.notifyDataSetChanged();
                            break;

                    }
                }


            }
        });

        FloatingActionButton floatingActionButton = view.findViewById(R.id.addTabButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // タブ作成フラグメントの追加
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.addTabFragment);

            }
        });

        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        System.out.println("文字が入力されてますね");
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

}
