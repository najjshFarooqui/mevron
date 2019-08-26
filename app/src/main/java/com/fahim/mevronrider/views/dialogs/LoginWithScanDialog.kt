package com.fahim.mevronrider.views.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import com.fahim.mevronrider.R

class LoginWithScanDialog(var c: Activity) : Dialog(c) {
    var d: Dialog? = null
    lateinit var cancel: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_fingerprint)
        cancel = findViewById(R.id.cancelScan)
        cancel.setOnClickListener {
            dismiss()
        }


    }


}