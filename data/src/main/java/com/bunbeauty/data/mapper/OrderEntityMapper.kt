package com.bunbeauty.data.mapper

import com.bunbeauty.common.Mapper
import com.bunbeauty.data.model.address.Address
import com.bunbeauty.data.model.address.CafeAddress
import com.bunbeauty.data.model.firebase.OrderEntityFirebase
import com.bunbeauty.data.model.order.OrderEntity
import javax.inject.Inject

class OrderEntityMapper @Inject constructor(private val addressMapper: AddressMapper) :
    Mapper<OrderEntityFirebase, OrderEntity> {

    override fun from(e: OrderEntity): OrderEntityFirebase {
        return OrderEntityFirebase(
            addressMapper.from(e.address),
            e.comment,
            e.phone,
            e.time,
            e.orderStatus,
            e.isDelivery,
            e.code,
            e.email,
            checkEmptyString(e.deferred),
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
            deferred = t.deferred ?: "",
        )
    }
}