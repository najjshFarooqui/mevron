package com.fahim.mevronrider.viewmodel

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahim.mevronrider.utils.DirectionsJSONParser
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class HomeViewModel(val con: Application) : AndroidViewModel(con) {

    val routeLiveData: MutableLiveData<PolylineOptions> = MutableLiveData()
    val PATH_WIDTH = 5f

    fun getRoute(): LiveData<PolylineOptions> = routeLiveData

    fun getRoute(url: String) {
        doAsync {
            var data = ""
            try {
                data = downloadUrl(url)

                val jObject = JSONObject(data)
                val parser = DirectionsJSONParser()
                val routes = parser.parse(jObject)
                var polylineOptions: PolylineOptions? = null

                for (i in routes.indices) {
                    val points = ArrayList<LatLng>()
                    polylineOptions = PolylineOptions()
                    val path = routes[i]

                    for (j in path.indices) {
                        val point = path[j]
                        val lat = java.lang.Double.parseDouble(point["lat"]!!)
                        val lng = java.lang.Double.parseDouble(point["lng"]!!)
                        val position = LatLng(lat, lng)
                        points.add(position)
                    }

                    polylineOptions.addAll(points)
                    polylineOptions.width(PATH_WIDTH)
                    polylineOptions.color(Color.YELLOW)

                    uiThread {
                        routeLiveData.value = polylineOptions
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun downloadUrl(strUrl: String): String {
        var data = ""
        var iStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null
        try {
            val url = URL(strUrl)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()
            iStream = urlConnection.inputStream
            val br = BufferedReader(InputStreamReader(iStream!!))
            data = iStream.bufferedReader().use(BufferedReader::readText)
            br.close()
        } catch (e: Exception) {
            Log.d("abc123", e.toString())
        } finally {
            iStream!!.close()
            urlConnection!!.disconnect()
        }
        return data
    }

}