package com.fahim.mevronrider.views.dialogs

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import com.fahim.mevronrider.R
import com.fahim.mevronrider.RiderModel
import com.fahim.mevronrider.models.CurrentRides
import com.fahim.mevronrider.views.activity.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_arrived.*


class DialogDriverArrived(var c: HomeActivity) : Dialog(c) {
    var d: Dialog? = null
    private var mRideReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    lateinit var riderKey: String
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_arrived)

        getRides()




        c.dialogArrive.setOnDismissListener(null)
        btnCancelRide.setOnClickListener {
            this.setOnDismissListener(null)
            cancel()
        }
        btnStartRide.setOnClickListener {
            setRide()

        }


    }


    private fun getRides() {
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
                            dialogUserDistance.text = distance
                            dialogUserEta.text = time
                            dialogRidePrice.text = price
                            var userReference =
                                FirebaseDatabase.getInstance().reference.child("rider")
                                    .child(riderId)
                            userReference.addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(p0: DataSnapshot) {
                                    var riderDetails = p0.getValue(RiderModel::class.java)
                                    tvRiderName.text = riderDetails!!.user_name
                                    val riderNumber = riderDetails.phone_number
                                    ivCallUser.setOnClickListener {
                                        val intent = Intent(Intent.ACTION_DIAL)
                                        intent.data = Uri.parse("tel:".plus(riderNumber))
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                        c.applicationContext.startActivity(intent)
                                    }


                                }

                                override fun onCancelled(p0: DatabaseError) {


                                }
                            })


                        }
                    }
                }

            })
        }
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
                        mRideReference!!.child(riderKey).child("ride_start").setValue("true")
                        dismiss()
                    }

                }

            })

        }
    }

    override fun onBackPressed() {

    }


}