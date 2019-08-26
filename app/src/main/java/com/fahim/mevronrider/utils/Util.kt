package com.applligent.taxi.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Patterns
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.fahim.mevronrider.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


fun Context.bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(this, vectorResId)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap =
        Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}


fun ActionBar.setUp(title: String) {
    this.title = title
    this.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material)
    this.setDisplayShowTitleEnabled(true)
    this.setDisplayHomeAsUpEnabled(true)
}

fun String.isValidName(): Boolean {
    if (this.isEmpty()) return false
    return this.matches("""[a-zA-Z ]+""".toRegex())
}

fun String.isValidPassword(): Boolean {
    if (this.isEmpty()) return false
    return this.matches("""[0-9]+""".toRegex())
}

fun String.isValidEmail() = !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null
}


