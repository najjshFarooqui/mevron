package com.fahim.mevronrider.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahim.mevronrider.R
import com.fahim.mevronrider.viewmodel.RidesViewModel
import com.fahim.mevronrider.views.adapters.RideAdapter
import kotlinx.android.synthetic.main.activity_ride_history.*
import kotlinx.android.synthetic.main.nav_header.*


class RideHistoryActivity : AppCompatActivity() {
    var viewModel: RidesViewModel? = null
    var ridesAdapter: RideAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_history)

        rvRideHistory.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProviders.of(this)
            .get(RidesViewModel::class.java)
        viewModel!!.init()
        ridesAdapter = RideAdapter(
            viewModel!!.getUSerRides()!!.value!!,
            this
        )
        rvRideHistory.adapter = ridesAdapter

        viewModel!!.getUSerRides()!!.observe(this, Observer {
            ridesAdapter!!.notifyDataSetChanged(it)
        })
        setUpViews()
    }

    fun setUpViews() {
        menu.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }

        tvAccountSettings.setOnClickListener {
            var intent =
                Intent(applicationContext, com.fahim.mevronrider.views.activity.ProfileActivity::class.java)
            startActivity(intent)

        }
        tvHome.setOnClickListener {
            var intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }



        navMyEarnings.setOnClickListener {
            var intent = Intent(applicationContext, EarningsActivity::class.java)
            startActivity(intent)
        }


    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.closeDrawer(Gravity.LEFT)
        } else {
            super.onBackPressed()
        }
    }

}
