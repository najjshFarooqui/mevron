package com.fahim.mevronrider.views.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.fahim.mevronrider.R
import com.fahim.mevronrider.utils.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : BaseActivity() {
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        firebaseAuth = FirebaseAuth.getInstance()
        btnGenerateLink.setOnClickListener {
            var email = etUserEmail.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Please fill e-mail", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Password reset link was sent your email address",
                        Toast.LENGTH_SHORT
                    ).show()
                    startNewActivity(com.fahim.mevronrider.views.activity.LoginActivity())
                    finishAffinity()

                } else {
                    Toast.makeText(applicationContext, "Mail sending error", Toast.LENGTH_SHORT).show()
                }

            }
        }


        ivBackArrow.setOnClickListener {
            startNewActivity(com.fahim.mevronrider.views.activity.LoginActivity())
            finishAffinity()
        }
    }
}
