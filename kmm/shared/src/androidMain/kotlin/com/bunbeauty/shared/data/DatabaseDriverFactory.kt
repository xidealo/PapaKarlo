package com.bunbeauty.shared.data

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        TODO()
       //return AndroidSqliteDriver(FoodDeliveryDatabase.Schema, context(), "foodDelivery.db")
    }
}