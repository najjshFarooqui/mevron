package com.fahim.mevronrider.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahim.mevronrider.R
import com.fahim.mevronrider.models.Earnings
import com.fahim.mevronrider.viewmodel.EarningViewModel
import com.fahim.mevronrider.views.adapters.EarningAdapter
import kotlinx.android.synthetic.main.activity_earning.*
import kotlinx.android.synthetic.main.nav_header.*
import java.util.*

class EarningsActivity : AppCompatActivity() {
    private var earningViewModel: EarningViewModel? = null
    private var earningAdapter: EarningAdapter? = null
    private val dataSet = ArrayList<Earnings>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earning)

        setUpViews()


        dataSet.add(
            Earnings("09, may, Wednesday", "$120")
        )
        dataSet.add(
            Earnings("10, may, Thursday", "$120")
        )

        dataSet.add(
            Earnings("10, may, Thursday", "$120")
        )

        dataSet.add(
            Earnings("10, may, Thursday", "$120")
        )

        dataSet.add(
            Earnings("10, may, Thursday", "$120")
        )



        rvEarnings.layoutManager = LinearLayoutManager(this)

        earningViewModel = ViewModelProviders.of(this)
            .get(EarningViewModel::class.java)
        earningViewModel!!.init()
        earningAdapter =
            EarningAdapter(dataSet, applicationContext)
        rvEarnings.adapter = earningAdapter


        earningViewModel!!.rides!!.observe(this, Observer {
            earningAdapter!!.notifyDataSetChanged()
        })


        menu.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)

        }

    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.closeDrawer(Gravity.LEFT)
        } else {
            super.onBackPressed()
        }
    }


    fun setUpViews() {

        menu.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }


        navRideHistory.setOnClickListener {
            var intent =
                Intent(applicationContext, com.fahim.mevronrider.views.activity.RideHistoryActivity::class.java)
            startActivity(intent)
        }



        editProfile.setOnClickListener {
            var intent = Intent(applicationContext, com.fahim.mevronrider.views.activity.ProfileActivity::class.java)
            startActivity(intent)
        }
        tvAccountSettings.setOnClickListener {
            var intent = Intent(applicationContext, com.fahim.mevronrider.views.activity.ProfileActivity::class.java)
            startActivity(intent)

        }
        tvHome.setOnClickListener {
            var intent = Intent(applicationContext, com.fahim.mevronrider.views.activity.HomeActivity::class.java)
            startActivity(intent)
        }


    }


}

