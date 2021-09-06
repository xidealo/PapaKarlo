package com.bunbeauty.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.data.dao.*
import com.bunbeauty.domain.model.entity.product.CartProductEntity
import com.bunbeauty.domain.model.entity.address.DistrictEntity
import com.bunbeauty.domain.model.entity.product.MenuProductEntity
import com.bunbeauty.domain.model.entity.cafe.CafeEntity
import com.bunbeauty.domain.model.entity.order.OrderEntity
import com.bunbeauty.domain.model.entity.user.UserEntity
import com.bunbeauty.domain.model.entity.address.StreetEntity
import com.bunbeauty.domain.model.entity.address.UserAddressEntity
import com.bunbeauty.domain.model.entity.product.OrderProductEntity

@Database(
    entities = [
        MenuProductEntity::class,
        CartProductEntity::class,
        OrderEntity::class,
        OrderProductEntity::class,
        UserAddressEntity::class,
        CafeEntity::class,
        DistrictEntity::class,
        StreetEntity::class,
        UserEntity::class,
    ], version = 83
)
@TypeConverters(ProductCodeConverter::class, OrderStatusConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getMenuProductDao(): MenuProductDao
    abstract fun getCartProductDao(): CartProductDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getUserAddressDao(): UserAddressDao
    abstract fun getCafeDao(): CafeDao
    abstract fun getDistrictDao(): DistrictDao
    abstract fun getStreetDao(): StreetDao
    abstract fun getUserDao(): UserDao
}