package com.fahim.mevronrider.views.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.fahim.mevronrider.R
import com.fahim.mevronrider.viewmodel.SignUpViewModel
import com.fahim.mevronrider.views.activity.RegistratonActivity
import kotlinx.android.synthetic.main.fragment_security.view.*


class FragmentSecurity : Fragment() {
    lateinit var signUpViewModel: SignUpViewModel
    lateinit var vieww: View
    var dob: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        vieww = inflater.inflate(R.layout.fragment_security, container, false)

        return vieww
    }

    override fun onDetach() {
        super.onDetach()
        activity?.let {
            signUpViewModel = ViewModelProviders.of(activity as RegistratonActivity).get(SignUpViewModel::class.java)
            signUpViewModel.state.license = vieww.etDriverLicence?.text.toString()
            signUpViewModel.state.expDate = vieww.etLicenceExpiration?.text.toString()
            signUpViewModel.state.firstName = vieww.etFirstName?.text.toString()
            signUpViewModel.state.middleName = vieww.etMiddleName?.text.toString()
            signUpViewModel.state.lastName = vieww.etLastName?.text.toString()
            signUpViewModel.state.dob = " 03-05-1993"
            signUpViewModel.state.address = vieww.etAddress?.text.toString()
            signUpViewModel.state.state = vieww.etState?.text.toString()
            signUpViewModel.state.ssn = vieww.etnationalSecurityNumber?.text.toString()

        }
    }

}
