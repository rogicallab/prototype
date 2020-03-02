package com.example.prototype;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.prototype.Room.Plan;
import com.example.prototype.Room.PlanRepository;

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

    }


