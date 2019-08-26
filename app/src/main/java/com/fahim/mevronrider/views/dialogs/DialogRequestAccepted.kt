package com.fahim.mevronrider.views.dialogs

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.fahim.mevronrider.R
import com.fahim.mevronrider.models.CurrentRides
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_request_accepted.*


class DialogRequestAccepted(var c: Activity) : Dialog(c) {
    var d: Dialog? = null

    private var mRideReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    lateinit var riderKey: String
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_request_accepted)

        getRides()

        btnArrive.setOnClickListener {
            setRide()
            dismiss()
        }
        btnCancelRide.setOnClickListener {
            dismiss()
        }


    }


    //un getRides() {
    //   mDatabase = FirebaseDatabase.getInstance()


    //   val rootRef = FirebaseDatabase.getInstance().reference
    //   rootRef.addValueEventListener(object : ValueEventListener {
    //       override fun onCancelled(p0: DatabaseError) {
    //           TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    //       }

    //       override fun onDataChange(snapshot: DataSnapshot) {
    //           if (snapshot.hasChild("ride_request")) {
    //               val ride = snapshot.getValue(CurrentRides::class.java)
    //               val distance = ride!!.ride_distance
    //               val time = ride!!.ride_time
    //               val myCurrentLocation = "Your Current Location"
    //               val userCurrentLocation = ride!!.pickup_location
    //               dialogUserDistance.text = distance
    //               dialogUserEta.text = time
    //               tvDriverCurrentLocation.text = myCurrentLocation
    //               tvUserCurrentLocation.text = userCurrentLocation

    //           }
    //       }
    //   })


    //   }


    private fun setRide() {
        mAuth = FirebaseAuth.getInstance()
        var uid = mAuth.uid
        mDatabase = FirebaseDatabase.getInstance()
        mRideReference = mDatabase!!.reference.child("ride_request")
        if (mRideReference != null) {
            mRideReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach {
                        riderKey = it.key.toString()
                        Toast.makeText(c.applicationContext, riderKey, Toast.LENGTH_SHORT).show()
                        mRideReference!!.child(riderKey).child("driver_arrive").setValue("true")
                        dismiss()
                    }

                }

            })

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
                        .orderByChild("najish")
                        .equalTo("farooqui")
                        .addListenerForSingleValueEvent(object : ValueEventListener {

                            override fun onDataChange(dataSnapshot: DataSnapshot) {

                                dataSnapshot.children.forEach {

                                    val key: String = it.key.toString()

                                    val referenceOne =
                                        FirebaseDatabase.getInstance().reference.child("ride_request").child(key)
                                    referenceOne.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {


                                        }


                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            Log.e("Count ", "" + snapshot.childrenCount)
                                            snapshot.children.forEach {
                                                var rides = snapshot.getValue(CurrentRides::class.java)
                                                System.out.print("ride distance is " + rides!!.ride_distance)
                                                val distance = rides.ride_distance
                                                val time = rides.ride_time
                                                val myCurrentLocation = "Your Current Location"
                                                val userCurrentLocation = rides.pickup_location
                                                dialogUserDistance.text = distance
                                                dialogUserEta.text = time
                                                tvDriverCurrentLocation.text = myCurrentLocation
                                                tvUserCurrentLocation.text = userCurrentLocation
                                                dismiss()
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


}