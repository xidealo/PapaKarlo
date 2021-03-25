package com.bunbeauty.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.data.OrderStatusConverter
import com.bunbeauty.data.ProductCodeConverter
import com.bunbeauty.data.model.*
import com.bunbeauty.domain.repository.address.AddressDao
import com.bunbeauty.domain.repository.cafe.CafeDao
import com.bunbeauty.domain.repository.cart_product.CartProductDao
import com.bunbeauty.domain.repository.district.DistrictDao
import com.bunbeauty.domain.repository.menu_product.MenuProductDao
import com.bunbeauty.domain.repository.order.OrderDao
import com.bunbeauty.domain.repository.street.StreetDao
import com.bunbeauty.data.model.cafe.CafeEntity
import com.bunbeauty.data.model.order.OrderEntity

@Database(
    entities = [
        CartProduct::class,
        MenuProduct::class,
        OrderEntity::class,
        Address::class,
        CafeEntity::class,
        DistrictEntity::class,
        Street::class
    ], version = 49
)
@TypeConverters(ProductCodeConverter::class, OrderStatusConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getCartProductDao(): CartProductDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getMenuProductDao(): MenuProductDao
    abstract fun getAddressDao(): AddressDao
    abstract fun getCafeDao(): CafeDao
    abstract fun getDistrictDao(): DistrictDao
    abstract fun getStreetDao(): StreetDao
}