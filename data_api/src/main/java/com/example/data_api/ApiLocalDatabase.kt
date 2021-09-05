package com.example.data_api

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_api.dao.CafeDao
import com.example.data_api.dao.CartProductDao
import com.example.data_api.dao.MenuProductDao
import com.example.data_api.dao.UserDao
import com.example.domain_api.model.entity.CafeEntity
import com.example.domain_api.model.entity.CartProductEntity
import com.example.domain_api.model.entity.MenuProductEntity
import com.example.domain_api.model.entity.user.UserEntity

@Database(
    entities = [
        MenuProductEntity::class,
        CartProductEntity::class,
        CafeEntity::class,
        UserEntity::class
    ], version = 3
)
abstract class ApiLocalDatabase : RoomDatabase() {
    abstract fun getMenuProductDao(): MenuProductDao
    abstract fun getCartProductDao(): CartProductDao
    abstract fun getCafeDao(): CafeDao
    abstract fun getUserDao(): UserDao
}