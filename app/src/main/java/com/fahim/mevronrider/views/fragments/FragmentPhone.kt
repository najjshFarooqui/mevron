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
import kotlinx.android.synthetic.main.fragment_phone.view.*


class FragmentPhone : Fragment() {
    lateinit var signUpViewModel: SignUpViewModel
    lateinit var vieww: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        vieww = inflater.inflate(R.layout.fragment_phone, container, false)
        return vieww
    }

    override fun onDetach() {
        super.onDetach()
        activity?.let {
            signUpViewModel = ViewModelProviders.of(activity as RegistratonActivity).get(SignUpViewModel::class.java)
            signUpViewModel.state.phone = vieww.etPhoneNumber?.text.toString()


        }
    }
}
