package com.example.data_api

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_api.dao.*
import com.example.domain_api.model.entity.CafeEntity
import com.example.domain_api.model.entity.product.CartProductEntity
import com.example.domain_api.model.entity.product.MenuProductEntity
import com.example.domain_api.model.entity.user.UserAddressEntity
import com.example.domain_api.model.entity.user.UserEntity

@Database(
    entities = [
        MenuProductEntity::class,
        CartProductEntity::class,
        CafeEntity::class,
        UserEntity::class,
        UserAddressEntity::class,
    ], version = 4
)
abstract class ApiLocalDatabase : RoomDatabase() {
    abstract fun getMenuProductDao(): MenuProductDao
    abstract fun getCartProductDao(): CartProductDao
    abstract fun getCafeDao(): CafeDao
    abstract fun getUserDao(): UserDao
    abstract fun getUserAddressDao(): UserAddressDao
}