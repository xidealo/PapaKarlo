package com.bunbeauty.data.mapper.order

import com.bunbeauty.domain.model.entity.order.OrderEntity
import com.bunbeauty.domain.model.firebase.order.OrderFirebase
import com.bunbeauty.domain.model.ui.order.OrderUI

interface IOrderMapper {

    fun toFirebaseModel(order: OrderUI): OrderFirebase
    fun toEntityModel(order: OrderUI): OrderEntity
}