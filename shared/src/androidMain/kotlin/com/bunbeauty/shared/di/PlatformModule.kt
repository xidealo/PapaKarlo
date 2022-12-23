package com.bunbeauty.shared.di

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.DataStoreRepository
import com.bunbeauty.shared.data.DatabaseDriverFactory
import com.bunbeauty.shared.data.FirebaseAuthRepository
import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module

actual fun platformModule() = module {
    single {
        val driver = DatabaseDriverFactory(context = get()).createDriver()
        FoodDeliveryDatabase.Schema.migrate(
            driver,
            0,
            FoodDeliveryDatabase.Schema.version,
        )
        FoodDeliveryDatabase(driver)
    }
    single<DataStoreRepo> {
        DataStoreRepository()
    }
    single {
        UuidGenerator()
    }
    single {
        FirebaseAuth.getInstance()
    }
    single {
        FirebaseAuthRepository(firebaseAuth = get())
    }
}