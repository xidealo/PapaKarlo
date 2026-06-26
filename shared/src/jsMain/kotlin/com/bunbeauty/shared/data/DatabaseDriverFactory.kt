package com.bunbeauty.shared.data

import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver =
        error("On web the SQL driver is created asynchronously in the app entry point")
}
