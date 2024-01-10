package com.bunbeauty.shared.data.mapper.order_product

import com.bunbeauty.shared.data.network.model.order.get.OrderProductServer
import com.bunbeauty.shared.db.OrderProductEntity

val mapOrderProductServerToOrderProductEntity: OrderProductServer.(String) -> OrderProductEntity =
    { orderUuidServer ->
        OrderProductEntity(
            uuid = uuid,
            count = count,
            name = name,
            newPrice = newPrice,
            oldPrice = oldPrice,
            utils = utils,
            nutrition = nutrition,
            description = description,
            comboDescription = comboDescription,
            photoLink = photoLink,
            barcode = barcode,
            newCommonPrice = newCommonPrice,
            oldCommonPrice = oldCommonPrice,
            newTotalCost = newTotalCost,
            oldTotalCost = oldTotalCost,
            orderUuid = orderUuidServer,
        )
    }
