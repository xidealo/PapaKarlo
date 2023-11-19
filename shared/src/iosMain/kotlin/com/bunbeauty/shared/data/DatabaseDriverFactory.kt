package com.bunbeauty.shared.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.bunbeauty.shared.db.FoodDeliveryDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(FoodDeliveryDatabase.Schema, "foodDelivery.db")
    }
}