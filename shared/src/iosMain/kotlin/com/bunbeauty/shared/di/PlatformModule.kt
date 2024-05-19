package com.bunbeauty.shared.di

import com.bunbeauty.core.flavorQualifier
import com.bunbeauty.core.isDebugQualifier
import com.bunbeauty.core.targetName
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.DataStoreRepository
import com.bunbeauty.shared.data.DatabaseDriverFactory
import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import org.koin.dsl.module
import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual fun platformModule() = module {
    single {
        FoodDeliveryDatabase(DatabaseDriverFactory().createDriver())
    }
    single<DataStoreRepo> {
        DataStoreRepository()
    }
    single {
        UuidGenerator()
    }
    single(flavorQualifier) { targetName.toString() }
    single(isDebugQualifier) { Platform.isDebugBinary }
}
