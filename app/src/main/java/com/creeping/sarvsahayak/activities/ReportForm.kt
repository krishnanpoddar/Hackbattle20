
package com.creeping.sarvsahayak


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.creeping.sarvsahayak.activities.SuccessPage
//import androidx.core.content.getSystemService
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import kotlinx.android.synthetic.main.activity_report_form.*

import java.util.*


class ReportForm : AppCompatActivity() {

    lateinit var toolbarDomesticVoilence: Toolbar
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    private var permissionid = 1000

    lateinit var btdone:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_form)

        toolbarDomesticVoilence=findViewById(R.id.ToolbarDomesticVoilence)
        setToolbar()


        btdone=findViewById(R.id.done_btn)
        // DONE_BNT
        done_btn.setOnClickListener {
            val n= Intent(this, SuccessPage::class.java)
            startActivity(n)
        }

        //CODE_FOR_SEND_BUTTON
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        sendButton.setOnClickListener {
            getLastLocation()



        }


        btn_camera.setOnClickListener {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, 123)

        }


        btn_gallery.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    pickImageFromGallery()
                }
            } else {
                pickImageFromGallery()
            }


        }
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnable()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location = task.result
                    if (location == null) {

                        getNewLocation()
                    } else {
                        Locationtxt.text =
                            "Your current coordinates are:\n Lat:" + location.latitude + "; Long:" + location.longitude +
                                    "\nYour City: " + getCityName(
                                location.latitude,
                                location.longitude
                            ) + ", your country: " + getCountryName(
                                location.latitude,
                                location.longitude
                            )
                    }
                }
            } else {
                Toast.makeText(this, "Please enable your location service", Toast.LENGTH_SHORT)
                    .show()
            }

        } else {
            requestPermission()
        }
    }

    private fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation = p0.lastLocation
            Locationtxt.text =
                "Your current coordinates are:\n Lat: " + lastLocation.latitude + "; Long: " + lastLocation.longitude +
                        "\nYour City: " + getCityName(
                    lastLocation.latitude,
                    lastLocation.longitude
                ) + ", Your Country: " + getCountryName(
                    lastLocation.latitude,
                    lastLocation.longitude
                )
        }
    }

    private fun checkPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ), permissionid
        )
    }

    private fun isLocationEnable(): Boolean {

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getCityName(lat: Double, long: Double): String {
        var cityName = ""
        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(lat, long, 1)
        cityName = address.get(0).locality

        return cityName
    }

    private fun getCountryName(lat: Double, long: Double): String {
        var countryName = ""
        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(lat, long, 1)
        countryName = address.get(0).countryName

        return countryName
    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()  && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (requestCode == permissionid) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug:", "You have the permission")
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            val bmp = data?.extras?.get("data") as Bitmap
            iv_cam.setImageBitmap(bmp)
        } else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            gallery_img.setImageURI(data?.data)
        }
    }


    fun setToolbar(){
        setSupportActionBar(toolbarDomesticVoilence)
        supportActionBar?.title="Domestic Voilence"
        //to display back arrow button on toolbar=>below 2 lines
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //to add functionality to back button of toolbar

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}