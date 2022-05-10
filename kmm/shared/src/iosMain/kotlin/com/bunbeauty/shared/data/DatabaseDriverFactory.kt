package com.bunbeauty.shared.data

import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory{
    actual fun createDriver(): SqlDriver {
        TODO()
       //return Native(FoodDeliveryDatabase.Schema, "foodDelivery.db")
    }
}