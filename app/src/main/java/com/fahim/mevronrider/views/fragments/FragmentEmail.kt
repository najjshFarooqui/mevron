package com.fahim.mevronrider.views.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.fahim.mevronrider.R
import com.fahim.mevronrider.viewmodel.SignUpViewModel
import com.fahim.mevronrider.views.activity.RegistratonActivity
import kotlinx.android.synthetic.main.fragment_email.view.*


class FragmentEmail : Fragment() {
    lateinit var vieww: View
    lateinit var signUpViewModel: SignUpViewModel
    lateinit var selectedRegion: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vieww = inflater.inflate(R.layout.fragment_email, container, false)
        var regions = arrayOf("Madhya Pradesh", "Rajasthan", "Gujrat", "Maharastra")
        vieww.spinnerRegion.adapter =
            ArrayAdapter(activity!!.applicationContext, android.R.layout.simple_spinner_dropdown_item, regions)
        vieww.spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(activity!!.applicationContext, regions[p2], Toast.LENGTH_SHORT).show()
                selectedRegion = regions[p2]
            }


        }
        return vieww
    }


    override fun onDetach() {
        super.onDetach()
        activity?.let {
            signUpViewModel = ViewModelProviders.of(activity as RegistratonActivity).get(SignUpViewModel::class.java)
            signUpViewModel.state.driverEmail = vieww.etDriverEmail?.text.toString()
            signUpViewModel.state.password = vieww.etPassword?.text.toString()
            signUpViewModel.state.rePassword = vieww.etReenterPassword?.text.toString()
            signUpViewModel.state.driverRegion = selectedRegion
        }
    }

}