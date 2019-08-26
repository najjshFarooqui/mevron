package com.fahim.mevronrider.views.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fahim.mevronrider.R
import com.fahim.mevronrider.views.dialogs.DailogRateUser
import kotlinx.android.synthetic.main.activity_ride_finished.*


class RideFInishedActivity : AppCompatActivity() {
    lateinit var rateUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_finished)


        var view = layoutInflater.inflate(R.layout.dialog_rate, null)
        var dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
        dialog.setContentView(view)
        rateUser = view.findViewById(R.id.btnRatePassanger)


        btnCompleteRide.setOnClickListener {
            val cdd = DailogRateUser(this)
            cdd.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            cdd.show()
        }
    }
}
