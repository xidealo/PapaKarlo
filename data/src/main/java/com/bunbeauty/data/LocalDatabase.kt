package com.bunbeauty.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.data.dao.*
import com.bunbeauty.data.model.*
import com.bunbeauty.data.model.address.CafeAddress
import com.bunbeauty.data.model.address.UserAddress
import com.bunbeauty.data.model.cafe.CafeEntity
import com.bunbeauty.data.model.order.OrderEntity
import com.bunbeauty.data.model.user.User

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
        User::class
    ], version = 65
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