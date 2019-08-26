package com.fahim.mevronrider.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahim.mevronrider.models.RideHistory
import com.fahim.mevronrider.repository.RideRepo

class RidesViewModel : ViewModel() {
    private var rides: MutableLiveData<List<RideHistory>>? = null
    private var mRepo: RideRepo? = null

    fun init() {
        if (rides != null) {
            return
        }
        mRepo = RideRepo.getInstance()
        rides = mRepo!!.rideHistory

    }

    fun getUSerRides(): LiveData<List<RideHistory>>? {
        return rides
    }

}
