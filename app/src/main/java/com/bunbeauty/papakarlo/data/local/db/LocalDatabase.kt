package com.bunbeauty.papakarlo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.data.model.*
import com.bunbeauty.papakarlo.data.local.converter.OrderStatusConverter
import com.bunbeauty.papakarlo.data.local.converter.ProductCodeConverter
import com.bunbeauty.papakarlo.data.local.db.address.AddressDao
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeDao
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductDao
import com.bunbeauty.papakarlo.data.local.db.district.DistrictDao
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductDao
import com.bunbeauty.papakarlo.data.local.db.order.OrderDao
import com.bunbeauty.papakarlo.data.local.db.street.StreetDao
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
    ], version = 48
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