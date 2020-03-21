package com.example.prototype2.Calendar;

import android.icu.text.Edits;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prototype2.R;
import com.example.prototype2.Room.Plan;
import com.example.prototype2.sharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int year;
    private int month;
    private int day;

    private RecyclerView recyclerView;
    private List<Plan> myDataset = new ArrayList<>();
    final PlanListAdapter mAdapter=new PlanListAdapter(myDataset);
    private RecyclerView.LayoutManager layoutManager;
    private sharedViewModel mPlanViewModel;

    public CalendarDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarDetailFragment newInstance(String param1, String param2) {
        CalendarDetailFragment fragment = new CalendarDetailFragment();
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
        year=CalendarDetailFragmentArgs.fromBundle(getArguments()).getYear();
        month=CalendarDetailFragmentArgs.fromBundle(getArguments()).getMonth();
        day=CalendarDetailFragmentArgs.fromBundle(getArguments()).getDay();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_detail, container, false);

        TextView textView = view.findViewById(R.id.textView2);
        textView.setText(year + "年" + (1+month) + "月" + day + "日");

        //recycleView
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewC);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
//        mAdapter = new PlanListAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
        //クリックイベント
        mAdapter.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                Plan plan =mAdapter.getCurrent();
                bundle.putString("planName",plan.getPlanName());
                bundle.putString("category",plan.getCategory());
                bundle.putInt("year",plan.getYear());
                bundle.putInt("month",plan.getMonth());
                bundle.putInt("day",plan.getDay());
                bundle.putInt("hour",plan.getHours());
                bundle.putInt("minute",plan.getMinute());
                bundle.putString("notification",plan.getNotification());
                bundle.putString("memo",plan.getMemo());
                bundle.putInt("id",mAdapter.getCurrentId());
                System.out.println(mAdapter.getCurrentId()+" IDです");
                bundle.putInt("access",2);
                Navigation.findNavController(view).navigate(R.id.action_calendarDetailFragment_to_editPlan,bundle);
            }
        });


//        //ViewModelと対応づける
        mPlanViewModel = new ViewModelProvider(this).get(sharedViewModel.class);

//        mPlanViewModel.getPlanList().observe(getViewLifecycleOwner(), new Observer<List<Plan>>() {
//            @Override
//            public void onChanged(@Nullable final List<Plan> plans) {
//                // Update the cached copy of the words in the adapter.
//                //日付ごとの予定を表示
//                List<Plan> rPlans=plans;
//                Iterator it = rPlans.iterator();
//                while(it.hasNext()){
//                    Plan plan=(Plan)it.next();
//                    if (year == plan.getYear() && month == plan.getMonth() && day == plan.getDay()) {
//                        } else {
//                            System.out.println(plan.getPlanName() + " remove");
//                            it.remove();
//                        }
//                }
//                mAdapter.setPlans(rPlans);
//            }
//        });
        mPlanViewModel.getByDate(year,month,day).observe(getViewLifecycleOwner(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> plans) {
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
                Bundle bundle=new Bundle();
                bundle.putInt("year",year);
                bundle.putInt("month",month);
                bundle.putInt("day",day);
                bundle.putInt("access",0);
                Navigation.findNavController(view).navigate(R.id.action_calendarDetailFragment_to_editPlan,bundle);
            }

        });
        return view;
    }

}
