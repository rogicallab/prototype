package com.example.prototype2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.prototype2.Room.Plan;
import com.example.prototype2.Room.PlanRepository;

import java.util.List;

public class sharedViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private PlanRepository mRepository;
    private LiveData<List<Plan>> mAllPlans;

    public sharedViewModel (Application application) {
        super(application);
        mRepository = new PlanRepository(application);
        mAllPlans = mRepository.getAllPlans();
    }
    public LiveData<List<Plan>> getPlanList(){return mAllPlans; }
    public void insert(Plan plan){mRepository.insert(plan);}
    public void deletePlan(Plan plan){mRepository.deletePlan(plan);}
    public void updatePlan(Plan plan){mRepository.updatePlan(plan);}
    public Plan findById(int id){return mRepository.findById(id);}

}