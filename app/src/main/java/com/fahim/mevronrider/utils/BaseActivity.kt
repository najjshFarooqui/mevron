package com.fahim.mevronrider.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fahim.mevronrider.R
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

open class BaseActivity : AppCompatActivity() {


    val TAG = "tag"
    private lateinit var mSnackbar: Snackbar


    fun printlog(tag: String, message: String) {
        Log.e(TAG, tag + "\n" + message)
    }


    fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    fun clearAllActivitiesFromStack(activity: Activity) {
        /*  To use it
                clearAllActivitiesFromStack(new LoginActivity());
         */
        startActivity(
            Intent(
                this,
                activity.javaClass
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        finish()
    }

    fun startNewActivity(activity: Activity) {
        startActivity(Intent(this, activity.javaClass))
    }

    fun setActivityOnFullScreen() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }


    fun getCurrentActivityName(): String {
        return this.javaClass.simpleName
    }

    fun showSnackbar(linearLayout: View, message: String) {
        mSnackbar = Snackbar.make(linearLayout, message, Snackbar.LENGTH_LONG).setAction("") {
            @Override
            fun onClick(v: View) {
                mSnackbar.dismiss()
            }
        }
        val snackbarView = mSnackbar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        val textview = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textview.setTextColor(ContextCompat.getColor(this, R.color.yellow))
        textview.maxLines = 5
        mSnackbar.show()
    }

    fun isEmailValid(email: String): Boolean {

        if (email.trim().isEmpty()) return false

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

}
