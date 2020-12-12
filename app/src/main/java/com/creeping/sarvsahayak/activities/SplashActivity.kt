package com.creeping.sarvsahayak.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.creeping.sarvsahayak.R

class SplashActivity : AppCompatActivity() {
    lateinit var hanlder:Handler

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences=getSharedPreferences(getString(R.string.shared_preferces_file),Context.MODE_PRIVATE)
        val isLoged=sharedPreferences.getBoolean("isLoggedIn",false)
        if (isLoged){
            val intent=Intent(this,AppLock::class.java)
            startActivity(intent)
            finish()
        }else{
            //splash screen
            hanlder=Handler()
            hanlder.postDelayed({

                val intent= Intent(this, LoginActivity::class.java)
                shared()
                startActivity(intent)
                finish()

            },2000)
        }

    }

    fun shared(){
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()
    }


}