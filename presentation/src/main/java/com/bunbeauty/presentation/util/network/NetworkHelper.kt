package com.bunbeauty.presentation.util.network

import javax.inject.Inject

class NetworkHelper @Inject constructor() : INetworkHelper {

    override fun isNetworkConnected(): Boolean {
        return true
    }
}