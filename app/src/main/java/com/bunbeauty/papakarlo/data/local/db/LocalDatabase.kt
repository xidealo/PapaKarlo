package com.bunbeauty.papakarlo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.papakarlo.data.local.converter.OrderStatusConverter
import com.bunbeauty.papakarlo.data.local.converter.ProductCodeConverter
import com.bunbeauty.papakarlo.data.local.db.address.AddressDao
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeDao
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductDao
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductDao
import com.bunbeauty.papakarlo.data.local.db.order.OrderDao
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.data.model.order.OrderEntity

@Database(
    entities = [
        CartProduct::class,
        MenuProduct::class,
        OrderEntity::class,
        Address::class,
        CafeEntity::class
    ], version = 19
)
@TypeConverters(ProductCodeConverter::class, OrderStatusConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getCartProductDao(): CartProductDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getMenuProductDao(): MenuProductDao
    abstract fun getAddressDao(): AddressDao
    abstract fun getCafeDao(): CafeDao
}