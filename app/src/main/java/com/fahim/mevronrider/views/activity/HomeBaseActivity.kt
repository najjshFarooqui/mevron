package com.fahim.mevronrider.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

open class HomeBaseActivity : AppCompatActivity(), HomeBaseInterface {


    override fun onHomeClicked() {
    }

    override fun onMyEarningClicked() {
        var intent = Intent(applicationContext, EarningsActivity::class.java)
        startActivity(intent)
    }

}


interface HomeBaseInterface {


    fun onHomeClicked()

    fun onMyEarningClicked()
}



