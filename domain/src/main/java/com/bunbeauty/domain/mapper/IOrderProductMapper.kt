package com.bunbeauty.domain.mapper

import com.bunbeauty.domain.model.entity.product.OrderProductEntity
import com.bunbeauty.domain.model.firebase.OrderProductFirebase
import com.bunbeauty.domain.model.ui.product.CartProduct
import com.bunbeauty.domain.model.ui.product.OrderProduct

interface IOrderProductMapper {

    fun toFirebaseModel(orderProduct: OrderProduct): OrderProductFirebase
    fun toEntityModel(orderProduct: OrderProductFirebase, orderUuid: String): OrderProductEntity
    fun toUIModel(cartProduct: OrderProductEntity): OrderProduct
}