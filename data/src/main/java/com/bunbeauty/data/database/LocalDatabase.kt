package com.bunbeauty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bunbeauty.data.database.dao.*
import com.bunbeauty.data.database.entity.CategoryEntity
import com.bunbeauty.data.database.entity.CityEntity
import com.bunbeauty.data.database.entity.StreetEntity
import com.bunbeauty.data.database.entity.cafe.CafeEntity
import com.bunbeauty.data.database.entity.cafe.SelectedCafeUuidEntity
import com.bunbeauty.data.database.entity.product.CartProductEntity
import com.bunbeauty.data.database.entity.product.MenuProductEntity
import com.bunbeauty.data.database.entity.product_with_category.MenuProductCategoryReference
import com.bunbeauty.data.database.entity.user.SelectedUserAddressUuidEntity
import com.bunbeauty.data.database.entity.user.UserAddressEntity
import com.bunbeauty.data.database.entity.user.UserEntity
import com.bunbeauty.data.database.entity.user.order.OrderEntity
import com.bunbeauty.data.database.entity.user.order.OrderProductEntity

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
abstract class LocalDatabase : RoomDatabase() {
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