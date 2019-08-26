package com.fahim.mevronrider.views.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.fahim.mevronrider.R
import com.fahim.mevronrider.databinding.ActivityHomeBinding
import com.fahim.mevronrider.models.CurrentRides
import com.fahim.mevronrider.models.LocationModel
import com.fahim.mevronrider.views.dialogs.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header.*


class HomeActivity : HomeBaseActivity(), HomeInterface, OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private lateinit var binding: ActivityHomeBinding


    lateinit var mGoogleMap: GoogleMap
    lateinit var mapFrag: SupportMapFragment
    lateinit var mLocationRequest: LocationRequest
    internal var mGoogleApiClient: GoogleApiClient? = null
    lateinit var mLastLocation: Location
    internal var mCurrLocationMarker: Marker? = null
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var cameraUpdate: CameraUpdate
    lateinit var latLng: LatLng
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    internal var userMarker: Marker? = null

    lateinit var mAuth: FirebaseAuth
    private var mDatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mRideReference: DatabaseReference? = null


    lateinit var dialogAwaitingRequest: DialogAwaitingRequest
    lateinit var dialogRequestFound: DialogRequestFound
    lateinit var dialogRequestAccepted: DialogRequestAccepted
    lateinit var dialogArrive: DialogDriverArrived
    lateinit var dialogRideStarted: RideStarted


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.baseHandler = this
        binding.handler = this
        mapFrag = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFrag.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()



        setUpViews()
        driverMode()


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style_json
            )
        )


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //Location Permission already granted
                buildGoogleApiClient()
                mGoogleMap.isMyLocationEnabled = true
            } else {
                //Request Location Permission
                checkLocationPermission()
            }
        } else {
            buildGoogleApiClient()
            mGoogleMap.isMyLocationEnabled = true
        }
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        mGoogleApiClient!!.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 5000
        mLocationRequest.fastestInterval = 5000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
        }
    }

    override fun onConnectionSuspended(i: Int) {}

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    override fun onLocationChanged(location: Location) {
        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }


        //Place current location marker
        latLng = LatLng(location.latitude, location.longitude)
        latitude = location.latitude
        longitude = location.longitude
        val markerOptions = MarkerOptions()
        markerOptions.title("Current Location")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
        mGoogleMap.animateCamera(cameraUpdate)


    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK") { dialogInterface, i ->
                        //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(
                            this@HomeActivity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            HomeActivity.MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }
                    .create()
                    .show()


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    HomeActivity.MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient()
                        }
                        mGoogleMap.isMyLocationEnabled = true
                    }

                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    companion object {

        const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
            drawer_layout.closeDrawer(Gravity.LEFT)
        } else {
            super.onBackPressed()
        }
    }


    fun setUpViews() {
        btnGoOnline.setOnClickListener {
            dialogAwaitingRequest = DialogAwaitingRequest(this)
            dialogAwaitingRequest.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogAwaitingRequest.show()
            dialogAwaitingRequest.setOnDismissListener {
                dialogRequestFound = DialogRequestFound(this)
                dialogRequestFound.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialogRequestFound.show()
                dialogRequestFound.setOnDismissListener {
                    hello()
                    dialogRequestAccepted = DialogRequestAccepted(this)
                    dialogRequestAccepted.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialogRequestAccepted.show()
                    dialogRequestAccepted.setOnDismissListener {
                        dialogArrive = DialogDriverArrived(this)
                        dialogArrive.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialogArrive.show()
                        dialogArrive.setOnDismissListener {
                            dialogRideStarted = RideStarted(this)
                            dialogRideStarted.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialogRideStarted.show()
                        }

                    }
                }
            }
        }

        menu.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }
        ivCenterLocation.setOnClickListener {
            Toast.makeText(applicationContext, "center", Toast.LENGTH_SHORT).show()
        }/*
        navMyEarnings.setOnClickListener {
            var intent = Intent(applicationContext, com.fahim.mevronrider.views.activity.EarningsActivity::class.java)
            startActivity(intent)
        }*/
        navRideHistory.setOnClickListener {
            var intent =
                Intent(applicationContext, com.fahim.mevronrider.views.activity.RideHistoryActivity::class.java)
            startActivity(intent)
        }



        editProfile.setOnClickListener {
            var intent =
                Intent(applicationContext, com.fahim.mevronrider.views.activity.ProfileActivity::class.java)
            startActivity(intent)
        }

        tvAccountSettings.setOnClickListener {
            var intent =
                Intent(applicationContext, com.fahim.mevronrider.views.activity.ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun openDrawer() {
        drawer_layout.openDrawer(Gravity.LEFT)
    }


    public override fun onPause() {
        super.onPause()

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)

        }
    }


    fun driverMode() {
        mDatabaseReference = mDatabase!!.reference.child("available")
        switchDriverMode.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                val userId = mAuth.currentUser!!.uid
                val currentUserDb = mDatabaseReference!!.child(userId)
                var locationModel = LocationModel()
                locationModel.latitude = latitude
                locationModel.longitude = longitude
                currentUserDb.setValue(locationModel)
                mDatabaseReference!!.addChildEventListener(
                    object : ChildEventListener {
                        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                            System.out.println("onChildChanged")
                        }

                        override fun onChildRemoved(p0: DataSnapshot) {
                            System.out.println("onChildRemoved")
                        }

                        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                            System.out.println("onChildMoved")
                        }

                        override fun onCancelled(p0: DatabaseError) {
                            System.out.println("onCancelled")
                            println(p0.message)
                        }


                        override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                            System.out.println("DRIVER_LATtitude")
                            val myLatlang = p0.getValue(LocationModel::class.java)
                            System.out.println("DRIVER_LAT " + myLatlang!!.latitude)
                            System.out.println("DRIVER_LAT " + myLatlang.longitude)
                            val driverLatLng =
                                LatLng(myLatlang.latitude.toDouble(), myLatlang.longitude.toDouble())
                            val markerOption = MarkerOptions().position(driverLatLng)
                                .title("Rider Location")
                            //  mGoogleMap.addMarker(markerOption)
                            // enter_place.text = location

                        }
                    }


                )

            }
        }

    }

    fun hello() {


        mDatabaseReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                mRideReference = mDatabase!!.reference.child("ride_request")
                mRideReference!!.addChildEventListener(object : ChildEventListener {
                    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                        System.out.println("onChildChanged")
                    }

                    override fun onChildRemoved(p0: DataSnapshot) {
                        System.out.println("onChildRemoved")
                    }

                    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                        System.out.println("onChildMoved")
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        System.out.println("onCancelled")
                        println(p0.message)
                    }


                    override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                        val rides = p0.getValue(CurrentRides::class.java)
                        System.out.println(rides!!.pickup_lat + "RIDE LATTITUDE")
                        System.out.println(rides.pickup_lng + "RIDE LONGITUDE")
                        val driverLatLng = LatLng(rides.pickup_lat!!.toDouble(), rides.pickup_lng!!.toDouble())
                        val markerOption = MarkerOptions().position(driverLatLng)
                            .title("Driver Location")
                        mGoogleMap.addMarker(markerOption)
                        // enter_place.text = location

                    }
                })

            }

        })


    }


}


interface HomeInterface {

    fun openDrawer()


}



