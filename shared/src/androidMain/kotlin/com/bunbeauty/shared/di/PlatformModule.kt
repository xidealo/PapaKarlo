package com.bunbeauty.shared.di

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.DataStoreRepository
import com.bunbeauty.shared.data.DatabaseDriverFactory
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import org.koin.dsl.module

actual fun platformModule() = module  {
    single {
        FoodDeliveryDatabase(DatabaseDriverFactory(context = get()).createDriver())
    }
    single <DataStoreRepo>{
        DataStoreRepository()
    }
}