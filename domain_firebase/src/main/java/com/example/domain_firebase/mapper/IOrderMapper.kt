package com.example.domain_firebase.mapper

import com.bunbeauty.domain.model.order.Order
import com.example.domain_firebase.model.entity.cafe.CafeEntity
import com.example.domain_firebase.model.entity.order.OrderEntity
import com.example.domain_firebase.model.entity.order.OrderWithProducts
import com.example.domain_firebase.model.firebase.order.OrderFirebase
import com.example.domain_firebase.model.firebase.order.UserOrderFirebase

interface IOrderMapper {

    fun toFirebaseModel(order: Order): OrderFirebase
    fun toEntityModel(order: Order): OrderEntity
    fun toEntityModel(
        order: OrderFirebase,
        userOrderFirebase: UserOrderFirebase,
        userUuid: String
    ): OrderWithProducts
    fun toUIModel(order: OrderWithProducts, cafe: CafeEntity): Order
}