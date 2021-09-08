package com.example.data_api

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_api.dao.CartProductDao
import com.example.data_api.dao.MenuProductDao
import com.example.domain_api.model.entity.CartProductEntity
import com.example.domain_api.model.entity.MenuProductEntity

@Database(
    entities = [
        MenuProductEntity::class,
        CartProductEntity::class,
    ], version = 2
)
abstract class ApiLocalDatabase : RoomDatabase() {
    abstract fun getMenuProductDao(): MenuProductDao
    abstract fun getCartProductDao(): CartProductDao
}