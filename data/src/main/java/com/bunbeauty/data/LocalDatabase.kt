package com.bunbeauty.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.data.dao.*
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.model.local.DistrictEntity
import com.bunbeauty.domain.model.local.MenuProduct
import com.bunbeauty.domain.model.local.Street
import com.bunbeauty.domain.model.local.address.CafeAddress
import com.bunbeauty.domain.model.local.address.UserAddress
import com.bunbeauty.domain.model.local.cafe.CafeEntity
import com.bunbeauty.domain.model.local.order.OrderEntity
import com.bunbeauty.domain.model.entity.UserEntity

@Database(
    entities = [
        CartProduct::class,
        MenuProduct::class,
        OrderEntity::class,
        CafeAddress::class,
        UserAddress::class,
        CafeEntity::class,
        DistrictEntity::class,
        Street::class,
        UserEntity::class
    ], version = 74
)
@TypeConverters(ProductCodeConverter::class, OrderStatusConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getCartProductDao(): CartProductDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getMenuProductDao(): MenuProductDao
    abstract fun getCafeAddressDao(): CafeAddressDao
    abstract fun getUserAddressDao(): UserAddressDao
    abstract fun getCafeDao(): CafeDao
    abstract fun getDistrictDao(): DistrictDao
    abstract fun getStreetDao(): StreetDao
    abstract fun getUserDao(): UserDao
}