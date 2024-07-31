package com.bunbeauty.shared.domain.model.product

import com.bunbeauty.shared.domain.model.addition.OrderAddition

data class OrderProduct(
    val uuid: String,
    override val count: Int,
    override val product: OrderMenuProduct,
    val orderAdditionList: List<OrderAddition>
) : ProductPosition()
