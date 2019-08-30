package com.fahim.mevronrider.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.fahim.mevronrider.R
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*


fun Context.getAddress(lat: Double, lng: Double): String {
    val geocoder = Geocoder(this, Locale.getDefault())
    return try {
        val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
        val obj: Address = addresses.get(0)
        val add = obj.getAddressLine(0)
        add
    } catch (e: IOException) {
        ""
    }
}


fun Context.getDirectionsUrl(origin: LatLng, dest: LatLng): String {
    val str_origin = "origin=" + origin.latitude + "," + origin.longitude
    val str_dest = "destination=" + dest.latitude + "," + dest.longitude
    val sensor = "sensor=false"
    var placesApi = "key=" + getString(R.string.map_api_key)
    val parameters = "$str_origin&$str_dest&$sensor&$placesApi"
    return "https://maps.googleapis.com/maps/api/directions/json?$parameters"
}


fun Context.getDirections(
    driverCurrentLatLng: LatLng,
    userPickupLatLng: LatLng,
    userDestinationLatLang: LatLng
): String {
    val str_origin = "origin=" + driverCurrentLatLng.latitude + "," + driverCurrentLatLng.longitude

    val str_pickup = "pickup" + userPickupLatLng.latitude + "," + userPickupLatLng.longitude

    val str_dest = "destination=" + userDestinationLatLang.latitude + "," + userDestinationLatLang.longitude

    val sensor = "sensor=false"

    var placesApi = "key=" + getString(R.string.map_api_key)

    val parameters = "$str_origin&$str_pickup$str_dest&$sensor&$placesApi"

    return "https://maps.googleapis.com/maps/api/directions/json?$parameters"
}