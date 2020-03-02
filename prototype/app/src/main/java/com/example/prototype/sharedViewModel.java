package com.example.prototype;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class sharedViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Plan>> PlanList;
    public MutableLiveData<List<Plan>> getPlanList(){
        if (PlanList == null){PlanList =new MutableLiveData<List<Plan>>();}
        return PlanList;
    }

}
