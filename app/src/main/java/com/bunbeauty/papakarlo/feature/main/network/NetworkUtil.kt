package com.bunbeauty.papakarlo.feature.main.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NetworkUtil(
    private val connectivityManager: ConnectivityManager
) : INetworkUtil {

    override fun observeIsOnline(): Flow<Boolean> = callbackFlow {

        trySend(getInitialIsOnline())

        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        val networkCallback = object : ConnectivityManager.NetworkCallback() {

            private val activeNetworks: MutableList<Network> = mutableListOf()

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                if (activeNetworks.none { activeNetwork -> activeNetwork == network }) {
                    activeNetworks.add(network)
                }
                trySend(activeNetworks.isNotEmpty())
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                activeNetworks.removeAll { activeNetwork -> activeNetwork == network }
                trySend(activeNetworks.isNotEmpty())
            }
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        awaitClose { connectivityManager.unregisterNetworkCallback(networkCallback) }
    }

    private fun getInitialIsOnline(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    }
}
