package com.example.prototype2.ToDo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prototype2.Calendar.PlanListAdapter;
import com.example.prototype2.R;
import com.example.prototype2.Room.Plan;
import com.example.prototype2.sharedViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToDoContentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoContentsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private List<Plan> myDataset = new ArrayList<>();
    final PlanListAdapter mAdapter=new PlanListAdapter(myDataset);
    private RecyclerView.LayoutManager layoutManager;
    private sharedViewModel mPlanViewModel;

    public ToDoContentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToDoContentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToDoContentsFragment newInstance(String param1, String param2) {
        ToDoContentsFragment fragment = new ToDoContentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ToDoContentsFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt("page",page);
        ToDoContentsFragment fragment = new ToDoContentsFragment();
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
        int page = getArguments().getInt("page",0);
        View view = inflater.inflate(R.layout.fragment_to_do_contents, container, false);

        // 画面に表示する文字
        //((TextView)view.findViewById(R.id.page_text)).setText("Page"+page);

        //recycleView
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewT);

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
                //Navigation.findNavController(view).navigate(R.id.action_toDoFragment_to_editPlan);
                SharedPreferences data = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                Gson gson = new Gson();
                Bundle bundle=new Bundle();
                bundle.putString("category",(String)gson.fromJson(data.getString("category",""), ArrayList.class).get(page-1));
                Navigation.findNavController(view).navigate(R.id.action_toDoFragment_to_editPlan,bundle);
            }
        });

        //ViewModelと対応づける
        mPlanViewModel = new ViewModelProvider(this).get(sharedViewModel.class);
        SharedPreferences data = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        mPlanViewModel.getPlanList().observe(getViewLifecycleOwner(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(@Nullable final List<Plan> plans) {
                // Update the cached copy of the words in the adapter.
                //カテゴリごとの予定を表示
                List<Plan> rPlans=plans;
                Iterator it = rPlans.iterator();
                while(it.hasNext()){
                    Plan plan=(Plan)it.next();
                    System.out.println("対象の予定:"+plan.getPlanName());
                    System.out.println("対象の予定のカテゴリ:"+plan.getCategory());
                    System.out.println("そのページのカテゴリ:"+gson.fromJson(data.getString("category",""), ArrayList.class).get(page-1));
                    System.out.println(plan.getCategory().equals(gson.fromJson(data.getString("category",""), ArrayList.class).get(page-1)));
                    if (plan.getCategory().equals(gson.fromJson(data.getString("category",""), ArrayList.class).get(page-1))) {

                    } else {
                        System.out.println(plan.getPlanName() + " remove");
                        it.remove();
                    }
                }
                mAdapter.setPlans(rPlans);
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
        mIth.attachToRecyclerView(recyclerView);

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
