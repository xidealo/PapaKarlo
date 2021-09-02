package com.bunbeauty.domain.mapper

import com.bunbeauty.domain.model.entity.cafe.CafeEntity
import com.bunbeauty.domain.model.entity.order.OrderEntity
import com.bunbeauty.domain.model.entity.order.OrderWithProducts
import com.bunbeauty.domain.model.firebase.order.OrderFirebase
import com.bunbeauty.domain.model.firebase.order.UserOrderFirebase
import com.bunbeauty.domain.model.ui.OrderUI

interface IOrderMapper {

    fun toFirebaseModel(order: OrderUI): OrderFirebase
    fun toEntityModel(order: OrderUI): OrderEntity
    fun toEntityModel(
        order: OrderFirebase,
        userOrderFirebase: UserOrderFirebase,
        userUuid: String
    ): OrderWithProducts
    fun toUIModel(order: OrderWithProducts, cafe: CafeEntity): OrderUI
}