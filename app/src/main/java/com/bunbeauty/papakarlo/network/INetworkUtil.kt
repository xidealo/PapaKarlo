package com.bunbeauty.papakarlo.network

import kotlinx.coroutines.flow.Flow

interface INetworkUtil {

    //fun getInitialIsOnline(): Boolean
    fun observeIsOnline(): Flow<Boolean>
}