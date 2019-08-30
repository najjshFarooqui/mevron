package com.fahim.mevronrider.views.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import com.fahim.mevronrider.R
import com.fahim.mevronrider.models.CurrentRides
import com.fahim.mevronrider.setUserKey
import com.fahim.mevronrider.views.activity.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class DialogRequestFound(var c: HomeActivity) : Dialog(c) {
    var d: Dialog? = null
    lateinit var accept: Button
    lateinit var reject: Button
    private var mRideReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_request_found)
        mAuth = FirebaseAuth.getInstance()

        accept = findViewById<View>(R.id.btnAcceptReq) as Button
        reject = findViewById<View>(R.id.btnCancelReq) as Button
        accept.setOnClickListener {
            setRide()
        }
        getRides()
        c.dialogRequestFound.setOnDismissListener(null)
        reject.setOnClickListener {
            this.setOnDismissListener(null)
            cancel()
        }
    }


    private fun getRides() {
        mDatabase = FirebaseDatabase.getInstance()

        val reference = FirebaseDatabase.getInstance().reference
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.hasChild("ride_request")) {
                    reference.child("ride_request")
                        .addListenerForSingleValueEvent(object : ValueEventListener {

                            override fun onDataChange(dataSnapshot: DataSnapshot) {

                                dataSnapshot.children.forEach {

                                    val key: String = it.key.toString()
                                    setUserKey(c.applicationContext, key)

                                    val referenceOne =
                                        FirebaseDatabase.getInstance().reference.child("ride_request").child(key)
                                    referenceOne.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {


                                        }


                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            snapshot.children.forEach {
                                                var rides = snapshot.getValue(CurrentRides::class.java)
                                                //     tvLabel.text = ("passanger is ").plus(rides!!.ride_distance)
                                                //         .plus(" away from your location")
                                            }
                                        }

                                    })
                                }
                            }

                            override fun onCancelled(p0: DatabaseError) {

                            }
                        })
                }
            }


        })


    }


    private fun setRide() {
        mAuth = FirebaseAuth.getInstance()
        var driverId = mAuth.currentUser!!.uid

        mRideReference = mDatabase!!.reference.child("ride_request")
        if (mRideReference != null) {


            mRideReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val children = p0.children
                    children.forEach {


                        //                        println("najish farooqui mahidpur " + it.key)
//
//                        println("najish farooqui mahidpur " + it.child("rider_id").value)
//                        println("najish farooqui mahidpur " + it.child("rider_id").value)
//
//                        val myMap: Map<String, Any> = mapOf<String, Any>(
//                            it.child("rq_status").toString() to "accepted",
//                            it.child("driver_lat").toString() to c.latitude,
//                            it.child("driver_lng").toString() to c.longitude
//
//                        )

                        mRideReference!!.child(it.key!!).child("driver_lat").setValue(c.latitude.toString())
                        mRideReference!!.child(it.key!!).child("driver_lng").setValue(c.longitude.toString())
                        mRideReference!!.child(it.key!!).child("driver_id").setValue(driverId)
                        mRideReference!!.child(it.key!!).child("rq_status").setValue("accepted")

                        dismiss()

                    }
                }


            })

        }
    }

    override fun onBackPressed() {

    }
}
