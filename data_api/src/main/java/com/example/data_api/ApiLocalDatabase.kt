package com.example.data_api

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data_api.dao.MenuProductDao
import com.example.domain_api.model.entity.MenuProductEntity

@Database(
    entities = [
        MenuProductEntity::class,
    ], version = 1
)
abstract class ApiLocalDatabase : RoomDatabase() {
    abstract fun getMenuProductDao(): MenuProductDao
}