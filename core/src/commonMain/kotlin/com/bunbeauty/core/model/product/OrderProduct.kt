package com.bunbeauty.core.model.product

import com.bunbeauty.core.model.addition.OrderAddition

data class OrderProduct(
    val uuid: String,
    override val count: Int,
    override val product: OrderMenuProduct,
    val orderAdditionList: List<OrderAddition>,
) : ProductPosition()
