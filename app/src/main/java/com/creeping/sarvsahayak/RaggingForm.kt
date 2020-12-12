package com.creeping.sarvsahayak

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.creeping.sarvsahayak.activities.OfflineMessage
import com.creeping.sarvsahayak.activities.SuccessPage
import com.creeping.sarvsahayak.util.ConnectionManager
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_ragging_form.*


class RaggingForm : AppCompatActivity() {

    lateinit var ToolbarRagging:Toolbar
    lateinit var etRaggingDesc:TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ragging_form)

        ToolbarRagging=findViewById(R.id.ToolbarRagging)
        etRaggingDesc=findViewById(R.id.etRaggingDesc)

        setToolbar()

        rag_cam.setOnClickListener {
            val i = Intent(ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, 345)
        }

        btn_filePicker.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, RaggingForm.PERMISSION_CODE)
                } else {
                    pickVideoFromGallery()
                }
            } else {
                pickVideoFromGallery()
            }


        }

        rag_snd_btn.setOnClickListener {
            Toast.makeText(this, "send", Toast.LENGTH_SHORT).show()
            if(ConnectionManager().checkConnectivity(this)){
                //internet connection found

               val intent=Intent(this,SuccessPage::class.java)
                startActivity(intent)
                finish()

            }
            else{

                //interbet connection not found
                val dialog=AlertDialog.Builder(this)
                dialog.setTitle("Internet Connection not found")
                dialog.setMessage("Click ok to Send Alert Message")
                //set ok button
                dialog.setPositiveButton("Send Offline",DialogInterface.OnClickListener {

                    dialog, which ->
                    val desc=etRaggingDesc.text.toString()
                    val intent=Intent(this,OfflineMessage::class.java)
                    intent.putExtra("MessageDescription",desc)
                    startActivity(intent)
                })
                dialog.create()
                dialog.show()
            }
        }
    }

    private fun pickVideoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*"
        startActivityForResult(intent, RaggingForm.IMAGE_PICK_CODE)
    }

    companion object {
        private val IMAGE_PICK_CODE = 1002
        private val PERMISSION_CODE = 1003

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        println("ragging res $requestCode")
        println( "ragging res"+grantResults.isNotEmpty())
        println( "ragging res"+grantResults[0])
        println("ragging res"+PackageManager.PERMISSION_GRANTED)
        when (requestCode) {
            RaggingForm.PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickVideoFromGallery()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 345) {
            val bmp = data?.extras?.get("data") as Bitmap
            rag_cam_img.setImageBitmap(bmp) }
        else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val path = data?.data?.path
            txt_pathShow.text = path
        }


    }


    fun setToolbar() {
        setSupportActionBar(ToolbarRagging)
        supportActionBar?.title = "Ragging"
        //to display back arrow button on toolbar
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //to add functionality to back button of toolbar

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}