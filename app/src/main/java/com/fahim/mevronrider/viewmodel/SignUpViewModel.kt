package com.fahim.mevronrider.viewmodel

import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    var state: SignUpState = SignUpState()
}


data class SignUpState(
    var driverEmail: String? = "",
    var password: String? = "",
    var rePassword: String? = "",
    var driverRegion: String? = "",
    var make: String? = "",
    var model: String? = "",
    var year: String? = "",
    var color: String? = "",
    var license: String? = "",
    var expDate: String? = "",
    var firstName: String? = "",
    var middleName: String? = "",
    var lastName: String? = "",
    var dob: String? = "",
    var address: String? = "",
    var state: String? = "",
    var ssn: String? = "",
    var residentialAddress: String? = "",
    var city: String? = "",
    var residentialState: String? = "",
    var zipCode: String? = "",
    var phone: String? = "",
    var userImage: String? = ""

)