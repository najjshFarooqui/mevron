package com.fahim.mevronrider.views.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.fahim.mevronrider.R
import com.fahim.mevronrider.models.Driver
import com.fahim.mevronrider.models.SignUpModel
import com.fahim.mevronrider.utils.BaseActivity
import com.fahim.mevronrider.viewmodel.SignUpViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity() {
    lateinit var cancel: ImageView
    lateinit var callbackManager: CallbackManager
    lateinit var viewModel: SignUpViewModel

    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FirebaseApp.initializeApp(applicationContext)
        firebaseAuth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)


        configureGoogleSignIn()
        setupUI()


        var view = layoutInflater.inflate(R.layout.dialog_fingerprint, null)
        var dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
        dialog.setContentView(view)
        cancel = view.findViewById(R.id.cancelScan)

        btnFirebaseLogin.setOnClickListener {
            firebaseAuth.signInWithEmailAndPassword(etLoginEmail.text.toString(), etLoginPassword.text.toString())
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        // there was an error
                        if (etLoginPassword.text.toString().length < 6) {
                            etLoginPassword.error = "6 characters required"

                        } else {
                            Toast.makeText(applicationContext, "something went wrong", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        startNewActivity(com.fahim.mevronrider.views.activity.HomeActivity())
                        var uid = firebaseAuth.currentUser!!.uid
                        Toast.makeText(
                            applicationContext, uid,
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    }
                }
        }
        ivFacebookLogin.setOnClickListener {
            facebookLogin()
        }

        btnDriverSignUp.setOnClickListener {
            startNewActivity(com.fahim.mevronrider.views.activity.RegistratonActivity())
        }

    }


    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    private fun setupUI() {

        btnScanFinger.setOnClickListener {
            startNewActivity(FingerAuthActivity())
        }

        ivGoogleLogin.setOnClickListener {
            signIn()
        }
        forgotPasswordTv.setOnClickListener {
            startNewActivity(com.fahim.mevronrider.views.activity.ResetPasswordActivity())
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {


                val driver = FirebaseAuth.getInstance().currentUser
                var driverName = driver!!.displayName
                var driverEmail = driver.email
                var driverPhone = driver.phoneNumber
                var driverPhoto = driver.photoUrl
                viewModel.state.driverEmail = driverEmail
                viewModel.state.firstName = driverName
                viewModel.state.phone = driverPhone
                viewModel.state.userImage = driverPhoto.toString()


                val signUpModel = SignUpModel()

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
                signUpModel.phone = driverPhone
                signUpModel.userImage = viewModel.state.userImage!!














                reference = FirebaseDatabase.getInstance().reference.child("Drivers")
                val currentUserDb = reference.child(driver.uid)

                currentUserDb.setValue(signUpModel)


            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startNewActivity(com.fahim.mevronrider.views.activity.HomeActivity())

        }
    }

    fun facebookLogin() {
        login_button.setPermissions(
            "user_photos", "email",
            "user_birthday", "public_profile", "AccessToken"
        )
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
                // ...
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
                // ...
            }
        })

    }


    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    val userFbDetails = Driver()
                    userFbDetails.email = user!!.email
                    userFbDetails.name = user.displayName
                    userFbDetails.phone = user.phoneNumber

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
    }
}