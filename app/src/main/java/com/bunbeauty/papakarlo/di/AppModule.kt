package com.bunbeauty.papakarlo.di

import android.content.Context
import android.net.ConnectivityManager
import coil.imageLoader
import com.bunbeauty.papakarlo.Router
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun appModule() = module {
    single { androidContext().resources }
    single { Router() }
    single { androidContext().imageLoader }
    single { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
}
