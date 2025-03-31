package com.bunbeauty.shared.data.di

import com.bunbeauty.shared.data.storage.CafeStorage
import org.koin.dsl.module

fun storageModule() = module {
    single {
        CafeStorage()
    }
}
