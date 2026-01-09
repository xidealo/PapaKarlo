package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.core.model.cart.ConsumerCartDomain
import com.bunbeauty.shared.domain.CommonFlow

interface ICartProductInteractor {
    fun observeConsumerCart(): CommonFlow<ConsumerCartDomain?>

    suspend fun removeAllProductsFromCart()
}
