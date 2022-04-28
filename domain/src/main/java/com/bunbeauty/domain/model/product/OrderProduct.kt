package com.bunbeauty.domain.model.product

data class OrderProduct(
    val uuid: String,
    override val count: Int,
    override val product: OrderMenuProduct,
) : ProductPosition()
