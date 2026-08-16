package com.bunbeauty.shared.data

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.bunbeauty.shared.db.FoodDeliveryDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(
            schema = FoodDeliveryDatabase.Schema.synchronous(),
            name = "foodDelivery.db",
        )
}
