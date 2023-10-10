package com.bunbeauty.papakarlo.di

import com.bunbeauty.papakarlo.feature.main.network.INetworkUtil
import com.bunbeauty.papakarlo.feature.main.network.NetworkUtil
import com.bunbeauty.papakarlo.util.resources.IResourcesProvider
import com.bunbeauty.papakarlo.util.resources.ResourcesProvider
import com.bunbeauty.papakarlo.util.string.IStringUtil
import com.bunbeauty.papakarlo.util.string.StringUtil
import com.bunbeauty.papakarlo.util.textvalidator.ITextValidator
import com.bunbeauty.papakarlo.util.textvalidator.TextValidator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun appUtilModule() = module {
    single<IResourcesProvider> { ResourcesProvider(androidContext()) }
    single<ITextValidator> { TextValidator() }

    single<INetworkUtil> {
        NetworkUtil(
            connectivityManager = get()
        )
    }
    single<IStringUtil> {
        StringUtil(
            resourcesProvider = get()
        )
    }
}
