package com.fahim.mevronrider.views.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fahim.mevronrider.R
import kotlinx.android.synthetic.main.activity_bank_details.*

class BankDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_details)
        btnAddAccountDetails.setOnClickListener {
            val intent = Intent(applicationContext, com.fahim.mevronrider.views.activity.HomeActivity::class.java)
            startActivity(intent)
        }
        ivCancelDetails.setOnClickListener {
            val intent = Intent(applicationContext, com.fahim.mevronrider.views.activity.ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
