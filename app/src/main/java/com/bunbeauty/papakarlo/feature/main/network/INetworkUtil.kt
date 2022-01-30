package com.bunbeauty.papakarlo.feature.main.network

import kotlinx.coroutines.flow.Flow

interface INetworkUtil {

    fun observeIsOnline(): Flow<Boolean>
}