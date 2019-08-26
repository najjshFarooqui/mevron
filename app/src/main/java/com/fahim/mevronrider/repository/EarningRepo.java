package com.fahim.mevronrider.repository;

import androidx.lifecycle.MutableLiveData;
import com.fahim.mevronrider.models.Earnings;

import java.util.ArrayList;
import java.util.List;

public class EarningRepo {

    private static EarningRepo instance;
    private ArrayList<Earnings> dataSet = new ArrayList<>();

    public static EarningRepo getInstance() {
        if (instance == null) {
            instance = new EarningRepo();
        }
        return instance;
    }

    public MutableLiveData<List<Earnings>> getRides() {
        setRides();
        MutableLiveData<List<Earnings>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setRides() {
        dataSet.add(
                new Earnings("09, may, Wednesday", "$120")
        );
        dataSet.add(
                new Earnings("10, may, Thursday", "$120")
        );

        dataSet.add(
                new Earnings("10, may, Thursday", "$120")
        );

        dataSet.add(
                new Earnings("10, may, Thursday", "$120")
        );

        dataSet.add(
                new Earnings("10, may, Thursday", "$120")
        );

    }
}