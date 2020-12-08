package com.bunbeauty.papakarlo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.papakarlo.data.local.converter.ProductCodeConverter
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct

@Database(
    entities = [
        CartProduct::class,
        MenuProduct::class
    ], version = 3
)
@TypeConverters(ProductCodeConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getCartDao(): CartDao
}