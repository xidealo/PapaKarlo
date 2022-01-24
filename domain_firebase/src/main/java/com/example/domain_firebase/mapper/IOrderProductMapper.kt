package com.example.domain_firebase.mapper

import com.bunbeauty.domain.model.product.OrderProduct
import com.example.domain_firebase.model.entity.product.OrderProductEntity
import com.example.domain_firebase.model.firebase.OrderProductFirebase

interface IOrderProductMapper {

    fun toFirebaseModel(orderProduct: OrderProduct): OrderProductFirebase
    fun toEntityModel(orderProduct: OrderProductFirebase, orderUuid: String): OrderProductEntity
    fun toUIModel(cartProduct: OrderProductEntity): OrderProduct
}