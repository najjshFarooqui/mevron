package com.fahim.mevronrider.views.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.fahim.mevronrider.R
import com.fahim.mevronrider.models.SignUpModel
import com.fahim.mevronrider.utils.BaseActivity
import com.fahim.mevronrider.viewmodel.SignUpViewModel
import com.fahim.mevronrider.views.fragments.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

class RegistratonActivity : BaseActivity() {
    private val viewPager: ViewPager? = null
    private var dotsLayout: LinearLayout? = null
    private var btnSkip: TextView? = null
    private var btnBack: TextView? = null
    private var btnFinish: TextView? = null
    private var counter = 1
    lateinit var viewModel: SignUpViewModel
    private var mProgressBar: ProgressBar? = null

    lateinit var mAuth: FirebaseAuth
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        mProgressBar = ProgressBar(this)
        dotsLayout = findViewById(R.id.pager_dots)
        btnSkip = findViewById(R.id.btn_skipp)
        btnBack = findViewById(R.id.back)
        btnFinish = findViewById(R.id.finish)
        btnBack!!.visibility = View.GONE




        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        /*viewModel.stateLiveData.observe(this, Observer {
            it?.let {
                //  Toast.makeText(applicationContext,  "$it", Toast.LENGTH_SHORT).show()
            }
        })*/

        btnFinish!!.setOnClickListener {
            Toast.makeText(applicationContext, viewModel.state.driverEmail, Toast.LENGTH_SHORT).show()
            sendToFirebase()


        }

        backArrow.setOnClickListener {
            startNewActivity(LoginActivity())
        }

        addFragment(FragmentEmail(), false, "email")






        btnSkip!!.setOnClickListener {
            if (counter == 1) {
                addFragment(FragmentVehicle(), false, "vehicle")
                btnBack!!.visibility = View.VISIBLE

            } else if (counter == 2) {
                addFragment(FragmentSecurity(), false, "security")
            } else if (counter == 3) {
                addFragment(FragmentAddress(), false, "address")
            } else if (counter == 4) {
                addFragment(FragmentPhone(), false, "phone")
            } else if (counter == 5) {
                btnSkip!!.visibility = View.GONE
                btnFinish!!.visibility = View.VISIBLE
                main.setBackgroundColor(resources.getColor(R.color.yellow))
                addFragment(FragmentOTP(), false, "otp")
            }
            println("MY_COUNTER $counter")
            counter++
        }

        btnBack!!.setOnClickListener {
            if (counter == 6) {
                btnSkip!!.visibility = View.VISIBLE
                btnFinish!!.visibility = View.GONE
                main.setBackgroundColor(resources.getColor(R.color.background))
                addFragment(FragmentPhone(), false, "phone")

            } else if (counter == 5) {
                addFragment(FragmentAddress(), false, "address")
            } else if (counter == 4) {
                addFragment(FragmentSecurity(), false, "security")
            } else if (counter == 3) {
                addFragment(FragmentVehicle(), false, "vehicle")
            } else if (counter == 2) {
                btnBack!!.visibility = View.GONE
                addFragment(FragmentEmail(), false, "email")
            }
            println("MY_COUNTER $counter")
            counter--
        }
    }


    private fun getItem(i: Int): Int {
        return viewPager!!.currentItem + i
    }


    private fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.replace(R.id.container_frame, fragment, tag)
        ft.commitAllowingStateLoss()
    }

    private fun sendToFirebase() {


        var signUpModel = SignUpModel()
        signUpModel.driverEmail = viewModel.state.driverEmail!!
        signUpModel.password = viewModel.state.password!!
        signUpModel.rePassword = viewModel.state.rePassword!!
        signUpModel.driverRegion = viewModel.state.driverRegion!!
        signUpModel.make = viewModel.state.make!!
        signUpModel.model = viewModel.state.model!!
        signUpModel.year = viewModel.state.year!!
        signUpModel.color = viewModel.state.color!!
        signUpModel.license = viewModel.state.license!!
        signUpModel.expDate = viewModel.state.expDate!!
        signUpModel.firstName = viewModel.state.firstName!!
        signUpModel.middleName = viewModel.state.middleName!!
        signUpModel.lastName = viewModel.state.lastName!!
        signUpModel.dob = viewModel.state.dob!!
        signUpModel.address = viewModel.state.address!!
        signUpModel.state = viewModel.state.state!!
        signUpModel.ssn = viewModel.state.ssn!!
        signUpModel.residentialAddress = viewModel.state.residentialAddress!!
        signUpModel.city = viewModel.state.city!!
        signUpModel.residentialState = viewModel.state.state!!
        signUpModel.zipCode = viewModel.state.zipCode!!
        signUpModel.phone = viewModel.state.phone!!
        signUpModel.userImage = viewModel.state.userImage!!


        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Drivers")

        mAuth.createUserWithEmailAndPassword(viewModel.state.driverEmail!!, viewModel.state.password!!)
            .addOnCompleteListener(this) { task ->
                mProgressBar!!.visibility = View.INVISIBLE
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val userId = mAuth.currentUser!!.uid
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    // currentUserDb.child("firstName").setValue(signUpModel.driverEmail)
                    // currentUserDb.child("lastName").setValue(signUpModel.password)
                    currentUserDb.setValue(signUpModel)
                    startNewActivity(HomeActivity())

                } else {
                    Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

}