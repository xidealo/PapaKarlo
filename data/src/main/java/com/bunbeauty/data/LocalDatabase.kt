package com.bunbeauty.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.data.dao.*
import com.bunbeauty.domain.model.ui.CartProduct
import com.bunbeauty.domain.model.ui.DistrictEntity
import com.bunbeauty.domain.model.ui.MenuProduct
import com.bunbeauty.domain.model.ui.Street
import com.bunbeauty.domain.model.ui.address.CafeAddress
import com.bunbeauty.domain.model.ui.address.UserAddress
import com.bunbeauty.domain.model.ui.cafe.CafeEntity
import com.bunbeauty.domain.model.entity.order.OrderEntity
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
    ], version = 77
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