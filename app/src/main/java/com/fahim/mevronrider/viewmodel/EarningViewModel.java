package com.fahim.mevronrider.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fahim.mevronrider.models.Earnings;
import com.fahim.mevronrider.repository.EarningRepo;

import java.util.List;

public class EarningViewModel extends ViewModel {
    private MutableLiveData<List<Earnings>> rides;
    private EarningRepo mRepo;

    public void init() {
        if (rides != null) {
            return;
        }
        mRepo = EarningRepo.getInstance();
        rides = mRepo.getRides();

    }

    public LiveData<List<Earnings>> getRides() {
        return rides;
    }

}
