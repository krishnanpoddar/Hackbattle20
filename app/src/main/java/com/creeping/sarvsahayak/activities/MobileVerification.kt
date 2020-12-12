package com.creeping.sarvsahayak.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.creeping.sarvsahayak.R
import com.google.firebase.auth.*

class MobileVerification : AppCompatActivity() {

    lateinit var auth:FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    lateinit var etMobileNumber:EditText
    lateinit var btNext: Button


    override fun onStart() {
        //overriding the onStart() func
        val currentUser = auth.currentUser?.phoneNumber
        if (currentUser!=null)
        {
            println("user comes 2nd time on mobile verification $currentUser")
            //If the user already exists in firebase
            val intent=Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_verification)
        auth=FirebaseAuth.getInstance()



        //HOOK

        etMobileNumber=findViewById(R.id.etMobileNumber)
        btNext = findViewById(R.id.btNext)
        btNext.setOnClickListener {
            Toast.makeText(this@MobileVerification, "callVerifyOTPScreen", Toast.LENGTH_SHORT).show()
            var phoneNumber:String=etMobileNumber.text.toString()
            var phone:String="+91$phoneNumber"

            if (phoneNumber.isEmpty()||phoneNumber.length<10){
                etMobileNumber.error="valid no is required"
                etMobileNumber.requestFocus()
                return@setOnClickListener
            }
            println("user comes 1st time on mobile verification")
            val intent=Intent(this, VerifyOTP::class.java)
            intent.putExtra("phone",phone)
            startActivity(intent)
        }

    }



}