package com.bun_beauty.papakarlo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bun_beauty.papakarlo.model.entity.Product

@Database(
    entities = [Product::class], version = 2
)
abstract class LocalDatabase : RoomDatabase() {

}