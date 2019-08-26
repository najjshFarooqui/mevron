package com.fahim.mevronrider.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import com.bumptech.glide.Glide
import com.fahim.mevronrider.R
import com.fahim.mevronrider.utils.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.nav_header.*

class ProfileActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val driver = FirebaseAuth.getInstance().currentUser
        var driverName = driver!!.displayName
        var driverEmail = driver.email
        var driverPhoto = driver.photoUrl


        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startNewActivity(LoginActivity())
        }

        tvDriverEmail.text = driverEmail
        tvDriverName.text = driverName
        var imageUrl = driverPhoto

        Glide.with(this).load(imageUrl).into(ivDriverProfile)
        menuu.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }
        ivEditProfile.setOnClickListener {
            val intent =
                Intent(applicationContext, com.fahim.mevronrider.views.activity.EditProfileActivity::class.java)
            startActivity(intent)
        }

        setUpViews()
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.closeDrawer(Gravity.LEFT)
        } else {
            super.onBackPressed()
        }
    }


    fun setUpViews() {


        tvHome.setOnClickListener {
            var intent = Intent(applicationContext, com.fahim.mevronrider.views.activity.HomeActivity::class.java)
            startActivity(intent)
        }



        navMyEarnings.setOnClickListener {
            var intent = Intent(applicationContext, com.fahim.mevronrider.views.activity.EarningsActivity::class.java)
            startActivity(intent)
        }
        navRideHistory.setOnClickListener {
            var intent =
                Intent(applicationContext, com.fahim.mevronrider.views.activity.RideHistoryActivity::class.java)
            startActivity(intent)
        }


        editBankInfo.setOnClickListener {
            var intent =
                Intent(applicationContext, com.fahim.mevronrider.views.activity.BankDetailsActivity::class.java)
            startActivity(intent)

        }


    }


}


