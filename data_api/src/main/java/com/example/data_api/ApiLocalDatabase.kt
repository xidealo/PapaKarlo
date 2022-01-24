package com.example.data_api

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data_api.dao.*
import com.example.domain_api.model.entity.CategoryEntity
import com.example.domain_api.model.entity.CityEntity
import com.example.domain_api.model.entity.StreetEntity
import com.example.domain_api.model.entity.cafe.CafeEntity
import com.example.domain_api.model.entity.cafe.SelectedCafeUuidEntity
import com.example.domain_api.model.entity.product.CartProductEntity
import com.example.domain_api.model.entity.product.MenuProductEntity
import com.example.domain_api.model.entity.product_with_category.MenuProductCategoryReference
import com.example.domain_api.model.entity.user.SelectedUserAddressUuidEntity
import com.example.domain_api.model.entity.user.UserAddressEntity
import com.example.domain_api.model.entity.user.UserEntity
import com.example.domain_api.model.entity.user.order.OrderEntity
import com.example.domain_api.model.entity.user.order.OrderProductEntity

@Database(
    entities = [
        CategoryEntity::class,
        MenuProductEntity::class,
        CartProductEntity::class,
        CityEntity::class,
        CafeEntity::class,
        StreetEntity::class,
        UserEntity::class,
        UserAddressEntity::class,
        OrderEntity::class,
        OrderProductEntity::class,
        SelectedUserAddressUuidEntity::class,
        SelectedCafeUuidEntity::class,
        MenuProductCategoryReference::class,
    ], version = 16
)
abstract class ApiLocalDatabase : RoomDatabase() {
    abstract fun getMenuProductDao(): MenuProductDao
    abstract fun getCartProductDao(): CartProductDao
    abstract fun getCafeDao(): CafeDao
    abstract fun getStreetDao(): StreetDao
    abstract fun getUserDao(): UserDao
    abstract fun getUserAddressDao(): UserAddressDao
    abstract fun getCityDao(): CityDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getCategoryDao(): CategoryDao
}