package com.example.prototype2.Calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.prototype2.Calendar.CalendarDetailFragment.*;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prototype2.R;
import com.example.prototype2.Room.Plan;

import java.util.List;

public class PlanListAdapter  extends RecyclerView.Adapter<PlanListAdapter.PlanViewHolder> {
    //private List<String> mDataArray;
    private List<Plan> mPlans;
    private CalendarDetailFragment calendarDetailFragment;
    private View.OnClickListener listener;
    private Plan current;
    private int id;
    // Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
    public static class PlanViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView planTitleView;
        public PlanViewHolder(View v) {
            super(v);
            planTitleView =(TextView)v.findViewById(R.id.title);
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

}
