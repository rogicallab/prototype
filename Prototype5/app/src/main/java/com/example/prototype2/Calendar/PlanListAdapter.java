package com.example.prototype2.Calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.prototype2.R;
import com.example.prototype2.Room.Plan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlanListAdapter  extends RecyclerView.Adapter<PlanListAdapter.PlanViewHolder> {
    //private List<String> mDataArray;
    private List<Plan> mPlans;
    private CalendarDetailFragment calendarDetailFragment;
    private View.OnClickListener listener;
    private Plan current;
    private int id;
    private List<Plan>splans;
    private List<Integer>positions=new ArrayList<>();
    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class PlanViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView planTitleView;
        public CheckBox checkBox;
        public PlanViewHolder(View v) {
            super(v);
            planTitleView =(TextView)v.findViewById(R.id.title);
            checkBox=(CheckBox) v.findViewById(R.id.delite);
        }
    }
    //private final LayoutInflater mInflater;
    //WordListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PlanListAdapter(List<Plan> myDataset) {
        mPlans = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override//多分変えなくても良い
    public PlanViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_plan, parent, false);
        return new PlanViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override//変える必要あり？
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(mPlans!=null){
            current=mPlans.get(position);
            holder.planTitleView.setText(current.getPlanName());
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int i=0;
                        System.out.println(current.getPlanName()+"削除要員 "+position);
                        Iterator it =positions.iterator();
                        while(it.hasNext()){
                            Integer posi=(Integer)it.next();
                            System.out.println(posi+"posi");
                            if(posi==position){
                                it.remove();
                                System.out.println(posi+"削除");
                                i++;
                            }
                        }
                        if(i==0){
                            positions.add((Integer)position);
                            System.out.println(positions.size()+"追加後");}

                    } catch (NullPointerException e) {
                        System.out.println(e);
                    }
                }
            });
            if(positions.size()==0){holder.checkBox.setChecked(false);}
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                }
            });
        }else{
            holder.planTitleView.setText("No Plan");
        }

    }

    public Plan getCurrent(){
        return current;
    }
    public int getCurrentId(){
        return current.getId();
    }
    public void setOnItemClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    public void setPlans(List<Plan> plans){
        mPlans=plans;
        notifyDataSetChanged();
    }
    public Plan getPlanAtPosition (int position) {
        return mPlans.get(position);
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPlans.size();
    }
    public List<Integer>getSelectedPosition(){
        return positions;
    }
    public void clearList(){
        positions.clear();
    }



}