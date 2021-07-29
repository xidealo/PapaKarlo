package com.bunbeauty.data.mapper.firebase

import com.bunbeauty.data.mapper.Mapper
import com.bunbeauty.domain.model.firebase.order.OrderEntityFirebase
import com.bunbeauty.domain.model.ui.address.Address
import com.bunbeauty.domain.model.entity.order.OrderEntity
import javax.inject.Inject

class OrderEntityMapper @Inject constructor(private val addressMapper: AddressMapper) :
    Mapper<OrderEntityFirebase, OrderEntity> {

    override fun from(model: OrderEntityFirebase): OrderEntity {
        return OrderEntity(
            "empty uuid",
            comment = model.comment ?: "",
            phone = model.phone,
            time = model.time,
            orderStatus = model.orderStatus,
            isDelivery = model.isDelivery,
            code = model.code,
            deferredTime = model.deferredTime ?: "",
            userUuid = model.userUuid ?: ""
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(model: OrderEntity): OrderEntityFirebase {
        return OrderEntityFirebase(
           comment = checkEmptyString(model.comment ?: ""),
           phone = model.phone,
           time = model.time,
           orderStatus = model.orderStatus,
           isDelivery = model.isDelivery,
           code = model.code,
            deferredTime = checkEmptyString(model.deferredTime ?: ""),
           userUuid = checkEmptyString(model.userUuid)
        )
    }
}