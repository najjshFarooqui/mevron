package com.fahim.mevronrider.views.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import com.fahim.mevronrider.R
import com.fahim.mevronrider.RiderModel
import com.fahim.mevronrider.getUserKey
import com.fahim.mevronrider.models.CurrentRides
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_arrived.*


class DialogDriverArrived(var c: Activity) : Dialog(c) {
    var d: Dialog? = null
    var userKey: String? = null
    private var mRideReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    lateinit var riderKey: String
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_arrived)

        getRides()

        btnCancelRide.setOnClickListener {
            dismiss()
        }
        btnStartRide.setOnClickListener {
            setRide()
            dismiss()
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

                                    userKey = it.key.toString()

                                    val referenceOne =
                                        FirebaseDatabase.getInstance().reference.child("ride_request").child(userKey!!)
                                    referenceOne.addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {


                                        }


                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            Log.e("Count ", "" + snapshot.childrenCount)
                                            snapshot.children.forEach {
                                                var rides = snapshot.getValue(CurrentRides::class.java)
                                                val ride = snapshot.getValue(CurrentRides::class.java)
                                                val distance = rides!!.ride_distance
                                                val time = rides.ride_time
                                                val price = rides.ride_pirce
                                                dialogUserDistance.text = distance
                                                dialogUserEta.text = time
                                                dialogRidePrice.text = price

                                                var userKey = getUserKey(c.applicationContext)
                                                var userReference =
                                                    FirebaseDatabase.getInstance().reference.child("rider")
                                                        .child(userKey!!)
                                                userReference.addListenerForSingleValueEvent(object :
                                                    ValueEventListener {
                                                    override fun onDataChange(p0: DataSnapshot) {
                                                        var riderDetails = p0.getValue(RiderModel::class.java)
                                                        tvRiderName.text = riderDetails!!.user_name

                                                        ivCallUser.setOnClickListener {
                                                            val intent = Intent(Intent.ACTION_DIAL)
                                                            intent.data = Uri.parse("tel:0123456789")
                                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                                            c.applicationContext.startActivity(intent)
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
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach {
                        riderKey = it.key.toString()
                        Toast.makeText(c.applicationContext, riderKey, Toast.LENGTH_SHORT).show()
                        mRideReference!!.child(riderKey).child("ride_start").setValue("true")
                        dismiss()
                    }

                }

            })

        }
    }


}