package com.bunbeauty.data.mapper

import com.bunbeauty.domain.mapper.IMenuProductMapper
import com.bunbeauty.domain.mapper.IOrderProductMapper
import com.bunbeauty.domain.model.entity.product.OrderProductEntity
import com.bunbeauty.domain.model.firebase.OrderProductFirebase
import com.bunbeauty.domain.model.ui.product.OrderProduct
import java.util.*
import javax.inject.Inject

class OrderProductMapper @Inject constructor(
    private val menuProductMapper: IMenuProductMapper
) : IOrderProductMapper {

    override fun toFirebaseModel(orderProduct: OrderProduct): OrderProductFirebase {
        return OrderProductFirebase(
            count = orderProduct.count,
            menuProduct = menuProductMapper.toFirebaseModel(orderProduct.menuProduct)
        )
    }

    override fun toEntityModel(
        orderProduct: OrderProductFirebase,
        orderUuid: String
    ): OrderProductEntity {
        return OrderProductEntity(
            uuid = UUID.randomUUID().toString(),
            menuProduct = menuProductMapper.toEntityModel(orderProduct.menuProduct),
            count = orderProduct.count,
            orderUuid = orderUuid,
        )
    }

    override fun toUIModel(cartProduct: OrderProductEntity): OrderProduct {
        return OrderProduct(
            uuid = cartProduct.uuid,
            menuProduct = menuProductMapper.toUIModel(cartProduct.menuProduct),
            count = cartProduct.count
        )
    }
}