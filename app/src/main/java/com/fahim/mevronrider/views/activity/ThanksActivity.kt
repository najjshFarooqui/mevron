package com.fahim.mevronrider.views.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fahim.mevronrider.R
import kotlinx.android.synthetic.main.activity_thanks.*

class ThanksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanks)

        btnClose.setOnClickListener {
            var intent = Intent(applicationContext, com.fahim.mevronrider.views.activity.ApprovedActivity::class.java)
            startActivity(intent)
        }
    }
}
