package com.creeping.sarvsahayak.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.PinLockListener
import com.andrognito.pinlockview.PinLockView
import com.creeping.sarvsahayak.R
import kotlinx.android.synthetic.main.activity_verify_o_t_p.view.*

class AppLock : AppCompatActivity() {

    lateinit var appLock:PinLockView
     lateinit var indicatorDots:IndicatorDots
    lateinit var  code1: String

    lateinit var sharedPreferences:SharedPreferences
    lateinit var  sharedPreferences2:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_lock)
        appLock=findViewById<PinLockView>(R.id.AppLock)
        indicatorDots=findViewById(R.id.IndicatorDots)

        appLock.setPinLockListener(mPinLockListner)
        appLock.attachIndicatorDots(indicatorDots)


        sharedPreferences=getSharedPreferences(getString(R.string.shared_preferces_file),Context.MODE_PRIVATE)
        sharedPreferences2=getSharedPreferences(getString(R.string.shared_preferces_file),Context.MODE_PRIVATE)


    }

    private val mPinLockListner:PinLockListener=object :PinLockListener{
        override fun onEmpty() {
            Toast.makeText(this@AppLock, "pin empty", Toast.LENGTH_SHORT).show()
        }
        override fun onComplete(pin: String?) {
            Toast.makeText(this@AppLock, "pin complete", Toast.LENGTH_SHORT).show()
            val code=pin//2001
            sharedPreferences.edit().putString("App_lock_key",code).apply()
            val isloggedIn=sharedPreferences.getBoolean("isLoggedInAppLock",false)
            val code2=sharedPreferences2.getString("Applockcodeelse", 0.toString())//0
            if (isloggedIn){
                //2nd time
                println("codes are $code2 $code")
                if (code2==code){

                    println("codes are equal")
                    Toast.makeText(this@AppLock, "correct password ", Toast.LENGTH_SHORT).show()
                    val intent=Intent(this@AppLock,HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this@AppLock, "incorrect password", Toast.LENGTH_SHORT).show()
                }


            }else{
                if (pin != null) {
                    code1=pin//1st 2001
                }
                shared()//true
                sharedPreferences2.edit().putString("Applockcodeelse",code1).apply()
                val intent=Intent(this@AppLock,HomeScreen::class.java)
                startActivity(intent)
                finish()
            }
        }

        override fun onPinChange(pinLength: Int, intermediatePin: String?) {
//            Toast.makeText(this@AppLock, "Pin changed, new length $pinLength with intermediate pin $intermediatePin", Toast.LENGTH_SHORT).show()

        }
    }
   fun shared(){
       sharedPreferences.edit().putBoolean("isLoggedInAppLock",true).apply()
   }
    fun shared2(){
        sharedPreferences2.edit().putString("Applockcodeelse",code1).apply()
    }
}