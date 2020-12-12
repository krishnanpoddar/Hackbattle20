package com.creeping.sarvsahayak.activities

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.creeping.sarvsahayak.R

class SuccessPage : AppCompatActivity() {
    lateinit var homebtn: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_page)

        homebtn=findViewById<ImageButton>(R.id.homeBtn)
        homebtn.setOnClickListener {
            val n= Intent(this, HomeScreen::class.java)
            startActivity(n)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}