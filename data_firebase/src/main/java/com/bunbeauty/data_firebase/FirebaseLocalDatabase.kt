package com.bunbeauty.data_firebase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bunbeauty.data_firebase.converter.OrderStatusConverter
import com.bunbeauty.data_firebase.converter.ProductCodeConverter
import com.bunbeauty.data_firebase.dao.*
import com.example.domain_firebase.model.entity.product.CartProductEntity
import com.example.domain_firebase.model.entity.address.DistrictEntity
import com.example.domain_firebase.model.entity.product.MenuProductEntity
import com.example.domain_firebase.model.entity.cafe.CafeEntity
import com.example.domain_firebase.model.entity.order.OrderEntity
import com.example.domain_firebase.model.entity.user.UserEntity
import com.example.domain_firebase.model.entity.address.StreetEntity
import com.example.domain_firebase.model.entity.address.UserAddressEntity
import com.example.domain_firebase.model.entity.product.OrderProductEntity

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
    ], version = 1
)
@TypeConverters(ProductCodeConverter::class, OrderStatusConverter::class)
abstract class FirebaseLocalDatabase : RoomDatabase() {
    abstract fun getMenuProductDao(): MenuProductDao
    abstract fun getCartProductDao(): CartProductDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getUserAddressDao(): UserAddressDao
    abstract fun getCafeDao(): CafeDao
    abstract fun getDistrictDao(): DistrictDao
    abstract fun getStreetDao(): StreetDao
    abstract fun getUserDao(): UserDao
}