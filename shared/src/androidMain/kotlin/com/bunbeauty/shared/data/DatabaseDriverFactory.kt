package com.bunbeauty.shared.data

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = FoodDeliveryDatabase.Schema,
            context = context,
            name = "foodDelivery.db",
            callback = object : AndroidSqliteDriver.Callback(FoodDeliveryDatabase.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            })
    }
}