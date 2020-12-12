package com.creeping.sarvsahayak.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionManager {

    fun checkConnectivity(context: Context):Boolean{

        //cekcing netowrk state
        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //cekcing active networks


        val activeNetwork: NetworkInfo?=connectivityManager.activeNetworkInfo


        if (activeNetwork?.isConnected!=null){
            //true or false
            return activeNetwork.isConnected
        }
        else{
            //if null
            return false
        }

    }
}