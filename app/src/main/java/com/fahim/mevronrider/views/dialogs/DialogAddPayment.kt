package com.fahim.mevronrider.views.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.fahim.mevronrider.R
import com.fahim.mevronrider.views.activity.BankDetailsActivity

class DialogAddPayment(var c: Activity)// TODO Auto-generated constructor stub
    : Dialog(c), android.view.View.OnClickListener {
    var d: Dialog? = null
    lateinit var addNow: Button
    lateinit var addLater: TextView

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_payment_info)
        addNow = findViewById<View>(R.id.btnAddPaymentInfo) as Button
        addLater = findViewById<View>(R.id.btnAddInfoLater) as TextView
        addNow.setOnClickListener(this)
        addLater.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnAddPaymentInfo -> {
                val intent = Intent(c, BankDetailsActivity::class.java)
                c.startActivity(intent)
                c.finish()
            }
            R.id.btnAddInfoLater -> dismiss()
            else -> {
            }
        }
        dismiss()
    }
}