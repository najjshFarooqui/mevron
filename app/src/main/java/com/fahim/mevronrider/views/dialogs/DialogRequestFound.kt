package com.fahim.mevronrider.views.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.fahim.mevronrider.R
import com.fahim.mevronrider.models.CurrentRides
import com.fahim.mevronrider.setUserKey
import com.fahim.mevronrider.views.activity.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_request_found.*


class DialogRequestFound(var c: HomeActivity) : Dialog(c) {
    var d: Dialog? = null
    lateinit var accept: Button
    lateinit var reject: Button
    private var mRideReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    lateinit var riderKey: String
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_request_found)


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
                        .orderByChild("najish")
                        .equalTo("farooqui")
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
                                            Log.e("Count ", "" + snapshot.childrenCount)
                                            snapshot.children.forEach {
                                                var rides = snapshot.getValue(CurrentRides::class.java)
                                                System.out.print("ride distance is " + rides!!.ride_distance)
                                                tvLabel.text = ("passanger is ").plus(rides.ride_distance)
                                                    .plus(" away from your location")
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
        var uid = mAuth.uid
        mDatabase = FirebaseDatabase.getInstance()
        mRideReference = mDatabase!!.reference.child("ride_request")
        if (mRideReference != null) {
            mRideReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach {
                        riderKey = it.key.toString()
                        Toast.makeText(c.applicationContext, riderKey, Toast.LENGTH_SHORT).show()
                        mRideReference!!.child(riderKey).child("rq_status").setValue("accepted")
                        mRideReference!!.child(riderKey).child("driver_id").setValue(uid)
                        dismiss()
                    }

                }

            })

        }
    }


}