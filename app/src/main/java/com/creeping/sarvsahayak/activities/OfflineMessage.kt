package com.creeping.sarvsahayak.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.creeping.sarvsahayak.R

class OfflineMessage : AppCompatActivity() {

    lateinit var etMobileNumber: EditText
    lateinit var etProblem: EditText
    lateinit var btsend: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_message)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etProblem = findViewById(R.id.etProblem)
        btsend = findViewById(R.id.btsendToMeassanger)

        val getDesc:String?=intent.getStringExtra("MessageDescription")
        etProblem.setText(getDesc)

        btsend.setOnClickListener {
            val permissionCheck: Int =
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                myMessage()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 0)
            }
        }


    }

    private fun myMessage() {
        val mobile =etMobileNumber.text.toString()
        val message = etProblem.text.toString()
        val smsManager: SmsManager = SmsManager.getDefault()

        smsManager.sendTextMessage(mobile, null, message, null, null)
        Toast.makeText(this, "Message send", Toast.LENGTH_SHORT).show()
        val intent=Intent(this,SuccessPage::class.java)
        startActivity(intent)
        finish()


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            0 -> {
                if (grantResults.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myMessage()
                } else {
                    Toast.makeText(this, "you didn't have the permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
