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
import kotlinx.android.synthetic.main.fragment_vehicle.view.*

class FragmentVehicle : Fragment() {
    lateinit var layoutView: View
    lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutView = inflater.inflate(R.layout.fragment_vehicle, container, false)

        return layoutView

    }

    override fun onDetach() {
        super.onDetach()
        activity?.let {
            signUpViewModel = ViewModelProviders.of(activity as RegistratonActivity).get(SignUpViewModel::class.java)

            signUpViewModel.state.make = layoutView.etMake?.text.toString()
            signUpViewModel.state.model = layoutView.etModel?.text.toString()
            signUpViewModel.state.year = layoutView.etYear?.text.toString()
            signUpViewModel.state.color = layoutView.etColor?.text.toString()

        }


    }
}



