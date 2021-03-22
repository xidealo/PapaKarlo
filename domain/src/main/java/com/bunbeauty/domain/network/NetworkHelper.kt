package com.bunbeauty.domain.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkHelper @Inject constructor(private val context: Context) : INetworkHelper {

    override fun isNetworkConnected(): Boolean {
        return true
    }
}