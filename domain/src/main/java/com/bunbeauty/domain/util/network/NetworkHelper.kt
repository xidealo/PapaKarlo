package com.bunbeauty.domain.util.network

import android.content.Context
import javax.inject.Inject

class NetworkHelper @Inject constructor(private val context: Context) : INetworkHelper {

    override fun isNetworkConnected(): Boolean {
        return true
    }
}