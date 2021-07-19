package com.bunbeauty.data.mapper.firebase

import com.bunbeauty.data.mapper.Mapper
import com.bunbeauty.domain.model.firebase.OrderEntityFirebase
import com.bunbeauty.domain.model.local.address.Address
import com.bunbeauty.domain.model.local.order.OrderEntity
import javax.inject.Inject

class OrderEntityMapper @Inject constructor(private val addressMapper: AddressMapper) :
    Mapper<OrderEntityFirebase, OrderEntity> {

    override fun from(model: OrderEntityFirebase): OrderEntity {
        return OrderEntity(
            "empty uuid",
            address = Address().apply {
                street = model.address.street
                house = model.address.house ?: ""
                flat = model.address.flat ?: ""
                entrance = model.address.entrance ?: ""
                comment = model.address.comment ?: ""
                floor = model.address.floor ?: ""
            },
            comment = model.comment ?: "",
            phone = model.phone,
            time = model.time,
            orderStatus = model.orderStatus,
            isDelivery = model.isDelivery,
            code = model.code,
            email = model.email ?: "",
            deferredTime = model.deferredTime ?: "",
            bonus = model.bonus ?: 0,
            userId = model.userId ?: ""
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(model: OrderEntity): OrderEntityFirebase {
        return OrderEntityFirebase(
            addressMapper.to(model.address),
            checkEmptyString(model.comment),
            model.phone,
            model.time,
            model.orderStatus,
            model.isDelivery,
            model.code,
            model.email,
            checkEmptyString(model.deferredTime),
            checkEmptyInt(model.bonus),
            checkEmptyString(model.userId)
        )
    }
}