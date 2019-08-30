package com.fahim.mevronrider.views.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window
import com.fahim.mevronrider.R
import com.fahim.mevronrider.models.CurrentRides
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_riding.*


class DialogRideStarted(var c: Activity) : Dialog(c) {
    var d: Dialog? = null

    private var mRideReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    lateinit var riderKey: String
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_riding)
        getRides()
        btnArrivedAtDrop.setOnClickListener {
            setRide()

        }

    }


    private fun getRides() {
        mDatabase = FirebaseDatabase.getInstance()


        val rootRef = FirebaseDatabase.getInstance().reference
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {


                mDatabase = FirebaseDatabase.getInstance()
                mRideReference = mDatabase!!.reference.child("ride_request")
                if (mRideReference != null) {


                    mRideReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val children = p0.children
                            children.forEach {
                                val key: String = it.key.toString()
                                p0.children.forEach {
                                    var rides = it.getValue(CurrentRides::class.java)
                                    val distance = rides!!.ride_distance
                                    val time = rides.ride_time
                                    val price = rides.ride_pirce
                                    val riderId = rides.rider_id
                                    val destination = rides.drop_location
                                    tvDestinationLocation.text = destination
                                    dialogUserDistance.text = distance
                                    dialogUserEta.text = time
                                    dialogRidePrice.text = price

                                }
                            }
                        }
                    }
                    )
                }
            }
        })
    }

    private fun setRide() {
        mAuth = FirebaseAuth.getInstance()

        mDatabase = FirebaseDatabase.getInstance()
        mRideReference = mDatabase!!.reference.child("ride_request")
        if (mRideReference != null) {
            mRideReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach {
                        riderKey = it.key.toString()
                        mRideReference!!.child(riderKey).child("ride_finish").setValue("true")
                        dismiss()
                    }

                }

            })

        }
    }

    override fun onBackPressed() {

    }

}