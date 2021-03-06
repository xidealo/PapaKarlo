package com.bunbeauty.papakarlo.utils.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkHelper @Inject constructor(private val context: Context): INetworkHelper {

    override fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected == true
    }
}