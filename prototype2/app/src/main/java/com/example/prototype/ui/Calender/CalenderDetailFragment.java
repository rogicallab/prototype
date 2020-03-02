package com.example.prototype.ui.Calender;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prototype.R;
import com.example.prototype.Room.Plan;
import com.example.prototype.sharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalenderDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalenderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalenderDetailFragment extends Fragment {
    private List<Plan> myDataset = new ArrayList<>();
    private RecyclerView recyclerView;
    final PlanListAdapter mAdapter=new PlanListAdapter(myDataset);
    //private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private sharedViewModel mPlanViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;

    public CalenderDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalenderDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalenderDetailFragment newInstance() {
        CalenderDetailFragment fragment = new CalenderDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender_detail, container, false);
//        int year=CalenderDetailFragmentArgs.fromBundle(getArguments()).getYear();
//        int month=CalenderDetailFragmentArgs.fromBundle(getArguments()).getMonth();
//        int day=CalenderDetailFragmentArgs.fromBundle(getArguments()).getDay();
        //日付取得
        try {
//            int year = getArguments().getInt("YEAR");
//            int month = getArguments().getInt("MONTH");
//            int day = getArguments().getInt("DAY");
            int year=CalenderDetailFragmentArgs.fromBundle(getArguments()).getYear();
            int month=1+CalenderDetailFragmentArgs.fromBundle(getArguments()).getMonth();
            int day=CalenderDetailFragmentArgs.fromBundle(getArguments()).getDay();
            TextView textView = view.findViewById(R.id.textView);
            textView.setText(year + "年" + month + "月" + day + "日");
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        //recycleView
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

//        int i = 0;
//        while (i < 20) {
//            String str=String.format(Locale.ENGLISH, "Data_0%d", i);
//            myDataset.add(str);
//            i++;
//        }
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
//        mAdapter = new PlanListAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);

//        //ViewModelと対応づける
        mPlanViewModel = new ViewModelProvider(this).get(sharedViewModel.class);
        mPlanViewModel.getPlanList().observe(getViewLifecycleOwner(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(@Nullable final List<Plan> plans) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setPlans(plans);
            }
        });
        //スワイプなど
        ItemTouchHelper mIth  = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN ,
                        ItemTouchHelper.LEFT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        final int toPos = target.getAdapterPosition();
                        mAdapter.notifyItemMoved(fromPos, toPos);
                        return true;// true if moved, false otherwise
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        final int fromPos = viewHolder.getAdapterPosition();
                        Plan myPlan=mAdapter.getPlanAtPosition(fromPos);
                        try {
                            mPlanViewModel.deletePlan(myPlan);
                            myDataset.remove(fromPos);
                            mAdapter.notifyItemRemoved(fromPos);

                        }catch (IndexOutOfBoundsException e){
                        }
                    }
                });
        //floatingActionButtonの動作
        mIth.attachToRecyclerView(recyclerView);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_calenderDetailFragment_to_editPlan);
            }

        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event

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
        void onFragmentInteraction(Uri uri);
    }
}

//        final List<Plan> data=new ArrayList<Plan>();
//           PlanListAdapter adapter=new PlanListAdapter(getContext(),R.layout.list_plan,data);
//        // ListViewにArrayAdapterを設定する
//        ListView listView = (ListView)view.findViewById(R.id.listView);
//        listView.setAdapter(adapter);
//           String str="しののん";
//           Plan plan=new CalendarPlan();
//           plan.setPlanName(str);
//           data.add(plan);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (view.getId()) {
//                    case R.id.edit:
//                        Toast.makeText(getContext(), data.get(position) + "の編集ボタンが押されました", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.delete:
//                        Toast.makeText(getContext(),data.get(position) + "の削除ボタンが押されました", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });

//        ArrayList data = new ArrayList<>();
//        data.add("国語");
//        data.add("社会");
//        data.add("算数");
//        data.add("理科");
//        data.add("生活");
//        data.add("音楽");
//        data.add("図画工作");
//        data.add("家庭");
//        data.add("体育");
//
//        // リスト項目とListViewを対応付けるArrayAdapterを用意する
//        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
// 画面のタイトルに設定する
//        getActivity().setTitle(year+"年"+month+"月"+day+"日");
// Inflate the layout for this fragment
