package com.fahim.mevronrider

import android.content.Context


//fun setUser(context: Context, userdata: UserData) {
//    setUserId(context, userdata.user_id)
//    setFullName(context, userdata.name)
//    setPhoneNo(context, userdata.phone)
//    setEmail(context, userdata.email)
//    setAddress(context, userdata.address)
//    setGender(context, userdata.gender)
//    setProfilePhoto(context, userdata.profile_photo)
//    setUserName(context, userdata.username)
//    setToken(context, userdata.token)
//
//
//}
//
//
//
//
//fun logout(context: Context) {
//    setLoggedIn(context, false)
//    setUserId(context, "")
//    setFullName(context, "")
//    setPhoneNo(context, "")
//    setEmail(context, "")
//    setProfilePhoto(context, "")
//    setToken(context,"")
//
//}
//
//fun getUser(context: Context): UserData {
//    val user = UserData()
//    user.user_id = getUserId(context)
//    user.username = getUserName(context)
//    user.phone = getPhoneNo(context)
//    user.email = getEmail(context)
//    user.profile_photo
//    user.gender = getGender(context)
//    user.name = getFullName(context)
//    user.token= getToken(context)!!
//
//    return user
//}
//
//


fun setLoggedIn(context: Context, loggedIn: Boolean) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("login_flag", loggedIn).apply()
}

fun isLoggedIn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("login_flag", false)
}

fun setFullName(context: Context, fullName: String) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("full_name", fullName).apply()
}

fun getFullName(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("full_name", "")
}

fun setPhoneNo(context: Context, phoneNo: String) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("phone_no", phoneNo).apply()
}

fun getPhoneNo(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("phone_no", "")
}

fun setEmail(context: Context, email: String) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("email", email).apply()
}

fun getEmail(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("email", "")
}


fun setProfilePhoto(context: Context, photo: String?) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("profile_photo", photo).apply()
}

fun getProfilePhoto(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("profile_photo", "")
}


fun setGender(context: Context, gender: String) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("gender", gender).apply()
}

fun getGender(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("gender", "")
}


fun setUserName(context: Context, userName: String) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("userName", userName).apply()
}

fun getUserName(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("userName", "")
}


fun setUserId(context: Context, userId: String) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("id", "").apply()
}

fun getUserId(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("id", "")
}


fun setAddress(context: Context, address: String) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("address", address).apply()
}

fun getAddress(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("address", "")
}


fun setToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("token", token).apply()
}

fun getToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("token", "")
}


fun setPasswordResetToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("passwordResetToken", token).apply()
}

fun getPasswordResetToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("passwordResetToken", "")
}


fun setUserKey(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("userKey", token).apply()
}

fun getUserKey(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("taxipref", Context.MODE_PRIVATE)
    return sharedPreferences.getString("userKey", "")
}













