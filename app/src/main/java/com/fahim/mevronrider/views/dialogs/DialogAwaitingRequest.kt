package com.fahim.mevronrider.views.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import com.fahim.mevronrider.R
import com.fahim.mevronrider.models.CurrentRides
import com.google.firebase.database.*


class DialogAwaitingRequest(var c: Activity) : Dialog(c), View.OnClickListener {
    var d: Dialog? = null
    lateinit var goOffline: Button
    private var mRideReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_awaiting_request)
        goOffline = findViewById<View>(R.id.btnGoOffline) as Button
        getRides()

    }



    fun getRides() {


        val rootRef = FirebaseDatabase.getInstance().reference



        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("ride_request")) {

                    mDatabase = FirebaseDatabase.getInstance()
                    mRideReference = mDatabase!!.reference.child("ride_request")
                    mRideReference!!.addListenerForSingleValueEvent(rideListener)

                }
            }
        })


    }

    val rideListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val ride = dataSnapshot.getValue(CurrentRides::class.java)
            Handler().postDelayed({
                dismiss()
            }, 3000)


        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.w("loadPost:onCancelled", databaseError.toException())

        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnGoOffline -> {
                dismiss()
            }
            else -> {
            }
        }

    }

    override fun onBackPressed() {

    }

}