package com.fahim.mevronrider.viewmodel

import androidx.lifecycle.ViewModel

class RidesViewModelOne : ViewModel() {

    var state: RidesState = RidesState()
}


data class RidesState(
    var car_type: String? = "",
    var driver_arrive: String? = "",
    var driver_id: String? = "",
    var drop_lat: String? = "",
    var drop_lng: String? = "",
    var drop_location: String? = "",
    var najish: String? = "",
    var payment_type: String? = "",
    var pickup_lat: String? = "",
    var pickup_lng: String? = "",
    var pickup_location: String? = "",
    var ride_distance: String? = "",
    var ride_finish: String? = "",
    var ride_pirce: String? = "",
    var ride_start: String? = "",
    var ride_time: String? = "",
    var rq_status: String? = ""

)