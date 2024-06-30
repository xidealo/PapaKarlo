package com.bunbeauty.shared.domain.model.cart

sealed class ConsumerCartDomain {
    data object Empty : ConsumerCartDomain()
    data class WithProducts(
        val cartProductList: List<LightCartProduct>,
        val oldTotalCost: Int?,
        val newTotalCost: Int,
        val discount: Int?,
    ) : ConsumerCartDomain()
}