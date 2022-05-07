package com.bunbeauty.shared.domain.model.product

data class OrderProduct(
    val uuid: String,
    override val count: Int,
    override val product: OrderMenuProduct,
) : ProductPosition()
