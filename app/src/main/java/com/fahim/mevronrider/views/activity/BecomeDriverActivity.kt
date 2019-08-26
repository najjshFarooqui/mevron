package com.fahim.mevronrider.views.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fahim.mevronrider.R
import kotlinx.android.synthetic.main.fragment_email.*


class BecomeDriverActivity : AppCompatActivity() {
    var country = arrayOf("India", "USA", "China", "Japan", "Pakistan")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.become_driver)


    }


    private fun gender() {

        ArrayAdapter.createFromResource(
            this,
            R.array.country,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinnerRegion.adapter = adapter
        }

        spinnerRegion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (position == 0) {
                    Toast.makeText(applicationContext, "please select your region", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        parent!!.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}
