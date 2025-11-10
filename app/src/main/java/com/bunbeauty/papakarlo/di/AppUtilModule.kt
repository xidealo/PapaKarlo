package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.feature.main.network.INetworkUtil
import com.bunbeauty.papakarlo.feature.main.network.NetworkUtil
import org.koin.dsl.module

fun appUtilModule() =
    module {
        single<INetworkUtil> {
            NetworkUtil(
                connectivityManager = get(),
            )
        }
    }
