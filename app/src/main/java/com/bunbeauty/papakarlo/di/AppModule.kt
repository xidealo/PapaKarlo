package com.bunbeauty.papakarlo.di

import android.content.Context
import android.net.ConnectivityManager
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.imageLoader
import coil3.memory.MemoryCache
import com.bunbeauty.core.flavorQualifier
import com.bunbeauty.core.isDebugQualifier
import com.bunbeauty.papakarlo.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun appModule() =
    module {
        single { androidContext().resources }
        single {
            androidContext()
                .imageLoader
                .newBuilder()
                .memoryCache {
                    MemoryCache
                        .Builder()
                        .maxSizePercent(context = get(), 0.25)
                        .build()
                }.diskCache {
                    DiskCache
                        .Builder()
                        .directory(directory = androidContext().cacheDir.resolve("image_cache"))
                        .maxSizePercent(0.02)
                        .build()
                }
        }
        single { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
        single(flavorQualifier) { BuildConfig.FLAVOR }
        single(isDebugQualifier) { BuildConfig.DEBUG }
    }
