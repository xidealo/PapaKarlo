package com.bunbeauty.shared.data

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.bunbeauty.shared.db.FoodDeliveryDatabase

actual class DatabaseDriverFactory(
    private val context: Context,
) {
    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(
            schema = FoodDeliveryDatabase.Schema.synchronous(),
            context = context,
            name = "foodDelivery.db",
        )
}
