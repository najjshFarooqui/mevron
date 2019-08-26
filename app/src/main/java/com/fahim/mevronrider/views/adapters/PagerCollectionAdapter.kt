package com.fahim.mevronrider.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fahim.mevronrider.views.fragments.*

class PagerCollectionAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentEmail()
            1 -> fragment = FragmentVehicle()
            2 -> fragment = FragmentSecurity()
            3 -> fragment = FragmentAddress()
            4 -> fragment = FragmentPhone()
            5 -> fragment = FragmentOTP()
        }

        return fragment
    }

    override fun getCount(): Int {
        return 6
    }


}
