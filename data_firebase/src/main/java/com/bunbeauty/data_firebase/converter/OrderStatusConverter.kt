package com.bunbeauty.data_firebase.converter

import androidx.room.TypeConverter
import com.bunbeauty.domain.enums.OrderStatus

class OrderStatusConverter {
    @TypeConverter
    fun convertFromOrderStatus(orderStatus: OrderStatus): String {
        return orderStatus.name
    }

    @TypeConverter
    fun convertToProductCode(name: String): OrderStatus {
        return OrderStatus.valueOf(name)
    }
}