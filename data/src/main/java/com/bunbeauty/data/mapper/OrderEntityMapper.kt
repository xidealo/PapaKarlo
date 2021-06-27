package com.bunbeauty.data.mapper

import com.bunbeauty.common.Mapper
import com.bunbeauty.domain.model.address.Address
import com.bunbeauty.domain.model.firebase.OrderEntityFirebase
import com.bunbeauty.domain.model.order.OrderEntity
import javax.inject.Inject

class OrderEntityMapper @Inject constructor(private val addressMapper: AddressMapper) :
    Mapper<OrderEntityFirebase, OrderEntity> {

    override fun from(e: OrderEntity): OrderEntityFirebase {
        return OrderEntityFirebase(
            addressMapper.from(e.address),
            checkEmptyString(e.comment),
            e.phone,
            e.time,
            e.orderStatus,
            e.isDelivery,
            e.code,
            e.email,
            checkEmptyString(e.deferredTime),
            checkEmptyInt(e.bonus),
            checkEmptyString(e.userId)
        )
    }

    /**
     * Set uuid after convert
     */
    override fun to(t: OrderEntityFirebase): OrderEntity {
        return OrderEntity(
            "empty uuid",
            address = Address().apply {
                street = t.address.street
                house = t.address.house ?: ""
                flat = t.address.flat ?: ""
                entrance = t.address.entrance ?: ""
                intercom = t.address.intercom ?: ""
                floor = t.address.floor ?: ""
            },
            comment = t.comment ?: "",
            phone = t.phone,
            time = t.time,
            orderStatus = t.orderStatus,
            isDelivery = t.isDelivery,
            code = t.code,
            email = t.email ?: "",
            deferredTime = t.deferredTime ?: "",
            bonus = t.bonus ?: 0,
            userId = t.userId ?: ""
        )
    }
}