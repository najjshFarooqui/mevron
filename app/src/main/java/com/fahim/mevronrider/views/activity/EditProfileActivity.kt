package com.fahim.mevronrider.views.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.fahim.mevronrider.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.tb_cut.*


class EditProfileActivity : AppCompatActivity() {


    lateinit var mAuth: FirebaseAuth
    private var mDDriverProfileReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mProgressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mAuth = FirebaseAuth.getInstance()
        mProgressBar = findViewById(R.id.progress_bar)

        mDatabase = FirebaseDatabase.getInstance()
        val driver = FirebaseAuth.getInstance().currentUser
        mDDriverProfileReference = mDatabase!!.reference.child("Drivers").child(driver!!.uid)

        val newName = etEditName.text.toString()
        val newLastName = etEditLastName.text.toString()
        val newEmail = etEditEmail.text.toString()
        val newSN = etEditSN.text.toString()






        mDDriverProfileReference!!.child("firstName").setValue(newName)
        mDDriverProfileReference!!.child("lastName").setValue(newLastName)
        mDDriverProfileReference!!.child("driverEmail").setValue(newEmail)
        mDDriverProfileReference!!.child("ssn").setValue(newSN)



        etEditName.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_RIGHT = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= etEditName.right - etEditName.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        etEditName.setText("")
                        return true
                    }
                }
                return false
            }
        })









        etEditLastName.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_RIGHT = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= etEditLastName.right - etEditLastName.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        etEditLastName.setText("")
                        return true
                    }
                }
                return false
            }
        })




        etEditEmail.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_RIGHT = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= etEditEmail.right - etEditEmail.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        etEditEmail.setText("")
                        return true
                    }
                }
                return false
            }
        })





        etEditSN.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_RIGHT = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= etEditSN.right - etEditSN.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        etEditSN.setText("")
                        return true
                    }
                }
                return false
            }
        })

        menuCut.setOnClickListener {
            val intent = Intent(
                applicationContext,
                com.fahim.mevronrider.views.activity.ProfileActivity::class.java
            )
            startActivity(intent)
        }

        ivUserImage.setOnClickListener {
            if (chooseImage.visibility == View.VISIBLE) {
                chooseImage.visibility = View.GONE
            } else if (chooseImage.visibility == View.GONE) {
                chooseImage.visibility = View.VISIBLE
            }


        }
        camera.setOnClickListener {
            var takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, 0)
        }
        gallery.setOnClickListener {
            var pickPhoto = Intent(
                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(pickPhoto, 1)
        }


    }


    override fun onBackPressed() {
        if (chooseImage.visibility == View.VISIBLE) {
            chooseImage.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check which request we're responding to
        when (requestCode) {
            0 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = data!!.data
                ivUserImage.setImageURI(selectedImage)
            }
            1 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = data!!.data
                ivUserImage.setImageURI(selectedImage)
            }
        }

    }

}






