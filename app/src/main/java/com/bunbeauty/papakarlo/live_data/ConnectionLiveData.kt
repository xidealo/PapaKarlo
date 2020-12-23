package com.bunbeauty.papakarlo.live_data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.*
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import javax.inject.Inject

class ConnectionLiveData @Inject constructor(context: Context) : LiveData<Boolean>() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()

        updateConnection()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val requestBuilder = NetworkRequest.Builder()
                .addTransportType(TRANSPORT_CELLULAR)
                .addTransportType(TRANSPORT_WIFI)
                .addTransportType(TRANSPORT_ETHERNET)
            connectivityManager.registerNetworkCallback(requestBuilder.build(), networkCallback)
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun updateConnection() {
        val activeNetwork = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }
}