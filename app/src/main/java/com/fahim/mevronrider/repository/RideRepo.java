package com.fahim.mevronrider.repository;

import androidx.lifecycle.MutableLiveData;
import com.fahim.mevronrider.models.RideHistory;

import java.util.ArrayList;
import java.util.List;

public class RideRepo {

    private static RideRepo instance;
    private ArrayList<RideHistory> dataSet = new ArrayList<>();

    public static RideRepo getInstance() {
        if (instance == null) {
            instance = new RideRepo();
        }
        return instance;
    }

    public MutableLiveData<List<RideHistory>> getRideHistory() {
        setRideHistory();
        MutableLiveData<List<RideHistory>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setRideHistory() {
        dataSet.add(
                new RideHistory("Hollywood USA", "5", "3", "5")
        );
        dataSet.add(
                new RideHistory("London England", "10", "30", "50")
        );

        dataSet.add(
                new RideHistory("Mumbai India", "15", "40", "60")
        );

        dataSet.add(
                new RideHistory("Karachi Pakistan", "20", "3", "60")
        );

        dataSet.add(
                new RideHistory("New York USA", "8", "3", "5")
        );

    }
}