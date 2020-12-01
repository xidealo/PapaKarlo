package com.bunbeauty.papakarlo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.papakarlo.data.local.converter.ProductCodeConverter
import com.bunbeauty.papakarlo.data.model.Product

@Database(
    entities = [Product::class], version = 2
)
@TypeConverters(ProductCodeConverter::class)
abstract class LocalDatabase : RoomDatabase() {

}