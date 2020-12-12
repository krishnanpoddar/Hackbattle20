package com.creeping.sarvsahayak.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.chaos.view.PinView
import com.creeping.sarvsahayak.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class VerifyOTP : AppCompatActivity() {
//    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId:String

    lateinit var auth: FirebaseAuth
    lateinit var PinView: PinView
    lateinit var btVerify: Button

    lateinit var progressBar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_o_t_p)
        auth=FirebaseAuth.getInstance()
        PinView = findViewById(R.id.PinView)
        btVerify=findViewById(R.id.btVerify)

        val mobile = intent.getStringExtra("phone")
        println("mobile verify is $mobile")
        sendVerificationCode(mobile)

        progressBar=findViewById(R.id.progressBar)
        btVerify.setOnClickListener {

            val code:String=PinView.text.toString()
           if (!code.isEmpty()){
               verifyCode(code)
           }

        }
    }

    private fun sendVerificationCode(mobile: String?) {
        println("verify fun 1 called")
        val mobileNumber = mobile!!
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(mobileNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private  var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks=object:PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            println("verify fun 2 called")
            val code=p0.smsCode
            if (code!=null){
                PinView.setText(code)

                verifyCode(code)
            }

        }

        override fun onVerificationFailed(p0: FirebaseException) {
            println("verify fun 3 called")
            Toast.makeText(this@VerifyOTP, "onVerificationFailed", Toast.LENGTH_SHORT).show()

        }
        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.

            // Save verification ID and resending token so we can use them later
            //stroring the verification code
            super.onCodeSent(verificationId, token)
            storedVerificationId = verificationId

            // ...
        }

    }

    private fun verifyCode(code:String){

        val credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
        signInWithPhoneAuthCredential(credential)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
         val mAuth:FirebaseAuth=FirebaseAuth.getInstance()
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "verification completed", Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,AppLock::class.java)
                    intent.flags=(Intent.FLAG_ACTIVITY_NEW_TASK and  Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.flags=(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()



                    // ...
                } else {
                    // Sign in failed, display a message and update the UI

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this, "Verification not completed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    fun callNextScreenFromOtp(view: View) {

        val code:String=PinView.text.toString()
        if (!code.isEmpty()){
            verifyCode(code)
        }

    }
}