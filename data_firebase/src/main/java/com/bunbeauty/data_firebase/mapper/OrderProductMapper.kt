package com.bunbeauty.data_firebase.mapper

import com.bunbeauty.domain.model.product.OrderProduct
import com.example.domain_firebase.mapper.IMenuProductMapper
import com.example.domain_firebase.mapper.IOrderProductMapper
import com.example.domain_firebase.model.entity.product.OrderProductEntity
import com.example.domain_firebase.model.firebase.OrderProductFirebase
import java.util.*
import javax.inject.Inject

class OrderProductMapper @Inject constructor(
    private val menuProductMapper: IMenuProductMapper
) : IOrderProductMapper {

    override fun toFirebaseModel(orderProduct: OrderProduct): OrderProductFirebase {
        return OrderProductFirebase(
            count = orderProduct.count,
            menuProduct = menuProductMapper.toFirebaseModel(orderProduct.product)
        )
    }

    override fun toEntityModel(
        orderProduct: OrderProductFirebase,
        orderUuid: String
    ): OrderProductEntity {
        return OrderProductEntity(
            uuid = UUID.randomUUID().toString(),
            menuProduct = menuProductMapper.toEntityModel("", orderProduct.menuProduct),
            count = orderProduct.count,
            orderUuid = orderUuid,
        )
    }

    override fun toUIModel(cartProduct: OrderProductEntity): OrderProduct {
        return OrderProduct(
            uuid = cartProduct.uuid,
            product = menuProductMapper.toOrderMenuProduct(cartProduct.menuProduct),
            count = cartProduct.count
        )
    }
}