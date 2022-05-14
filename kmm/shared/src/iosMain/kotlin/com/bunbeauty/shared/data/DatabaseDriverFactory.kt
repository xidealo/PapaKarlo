package com.bunbeauty.shared.data

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory{
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(FoodDeliveryDatabase.Schema, "foodDelivery.db")
    }
}