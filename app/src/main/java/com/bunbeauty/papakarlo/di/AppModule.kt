package com.bunbeauty.papakarlo.di

import android.content.Context
import android.net.ConnectivityManager
import coil3.imageLoader
import com.bunbeauty.core.flavorQualifier
import com.bunbeauty.core.isDebugQualifier
import com.bunbeauty.papakarlo.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun appModule() = module {
    single { androidContext().resources }
    single { androidContext().imageLoader.newBuilder() }
    single { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    single(flavorQualifier) { BuildConfig.FLAVOR }
    single(isDebugQualifier) { BuildConfig.DEBUG }
}
