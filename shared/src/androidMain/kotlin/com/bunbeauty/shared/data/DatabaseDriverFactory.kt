package com.bunbeauty.shared.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.bunbeauty.shared.db.FoodDeliveryDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(FoodDeliveryDatabase.Schema, context, "foodDelivery.db")
    }
}