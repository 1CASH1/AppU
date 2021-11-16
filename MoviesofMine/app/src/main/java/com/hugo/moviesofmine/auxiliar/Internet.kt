package com.hugo.moviesofmine.auxiliar

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class Internet(val context: Context?) {
    fun validate(): Boolean? {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting
    }
}