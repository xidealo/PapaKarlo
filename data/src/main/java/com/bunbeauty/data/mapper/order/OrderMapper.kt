package com.bunbeauty.data.mapper.order

import com.bunbeauty.data.mapper.cart_product.ICartProductMapper
import com.bunbeauty.data.mapper.user_address.IUserAddressMapper
import com.bunbeauty.domain.enums.OrderStatus
import com.bunbeauty.domain.model.entity.order.OrderEntity
import com.bunbeauty.domain.model.firebase.order.OrderEntityFirebase
import com.bunbeauty.domain.model.firebase.order.OrderFirebase
import com.bunbeauty.domain.model.ui.order.OrderUI
import javax.inject.Inject

class OrderMapper @Inject constructor(
    private val userAddressMapper: IUserAddressMapper,
    private val cartProductMapper: ICartProductMapper
) : IOrderMapper {

    override fun toFirebaseModel(order: OrderUI): OrderFirebase {
        return OrderFirebase(
            orderEntity = OrderEntityFirebase(
                isDelivery = order.isDelivery,
                phone = order.phone,
                address = userAddressMapper.toFirebaseModel(order.userAddress),
                comment = order.comment,
                deferredTime = order.deferredTime,
                spentBonuses = 0,
                accruedBonuses = 0,
                orderStatus = OrderStatus.NOT_ACCEPTED,
                code = order.code,
                time = order.time,
                userUuid = order.userUuid,
            ),
            cartProducts = order.cartProducts.map(cartProductMapper::toFirebaseModel)
        )
    }

    override fun toEntityModel(order: OrderUI): OrderEntity {
        return OrderEntity(
            uuid = order.uuid,
            isDelivery = order.isDelivery,
            userUuid = order.userUuid,
            phone = order.phone,
            userAddressUuid = order.userAddress?.uuid,
            cafeUuid = order.cafeUuid,
            comment = order.comment,
            deferredTime = order.deferredTime,
            time = order.time,
            code = order.code,
            orderStatus = order.orderStatus,
        )
    }


}