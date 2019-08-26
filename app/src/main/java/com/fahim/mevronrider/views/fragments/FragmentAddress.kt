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
import kotlinx.android.synthetic.main.fragment_address.view.*


class FragmentAddress : Fragment() {
    lateinit var vieww: View
    lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vieww = inflater.inflate(R.layout.fragment_address, container, false)
        return vieww
    }

    override fun onDetach() {
        super.onDetach()
        activity?.let {
            signUpViewModel = ViewModelProviders.of(activity as RegistratonActivity).get(SignUpViewModel::class.java)

            signUpViewModel.state.residentialAddress = vieww.etResidentialAddress?.text.toString()
            signUpViewModel.state.city = vieww.etCity?.text.toString()
            signUpViewModel.state.state = vieww.etState?.text.toString()
            signUpViewModel.state.zipCode = vieww.etZipCode?.text.toString()
        }
    }
}
