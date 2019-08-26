package com.fahim.mevronrider.views.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import com.fahim.mevronrider.R

class DailogRateUser(var c: Activity) : Dialog(c) {
    var d: Dialog? = null
    lateinit var rate: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_rate)
        rate = findViewById(R.id.btnRatePassanger)
        rate.setOnClickListener {
            dismiss()
        }

    }
}


