package com.fahim.mevronrider.views.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fahim.mevronrider.R
import kotlinx.android.synthetic.main.activity_ride_details.*


class RideDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_details)
        var destination = intent.getStringExtra("destination")
        var distance = intent.getStringExtra("distance")
        var eta = intent.getStringExtra("eta")
        var earned = intent.getStringExtra("earned")



        tvRideDestination.text = destination
        tvRideDistance.text = distance
        tvRideEta.text = eta
        tvRidePrice.text = earned
        tvMevronTip.text = "2"
        tvRidePickup.text = "Ujjain India"

        backArrow.setOnClickListener {
            val intent =
                Intent(applicationContext, com.fahim.mevronrider.views.activity.RideHistoryActivity::class.java)
            startActivity(intent)
        }


    }


}
