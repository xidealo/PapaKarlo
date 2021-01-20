package com.bunbeauty.papakarlo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.papakarlo.data.local.converter.OrderStatusConverter
import com.bunbeauty.papakarlo.data.local.converter.ProductCodeConverter
import com.bunbeauty.papakarlo.data.local.db.cart_product.CartProductDao
import com.bunbeauty.papakarlo.data.local.db.menu_product.MenuProductDao
import com.bunbeauty.papakarlo.data.local.db.order.OrderDao
import com.bunbeauty.papakarlo.data.model.CartProduct
import com.bunbeauty.papakarlo.data.model.MenuProduct
import com.bunbeauty.papakarlo.data.model.order.Order

@Database(
    entities = [
        CartProduct::class,
        MenuProduct::class,
        Order::class
    ], version = 8
)
@TypeConverters(ProductCodeConverter::class, OrderStatusConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getCartProductDao(): CartProductDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getMenuProductDao(): MenuProductDao
}