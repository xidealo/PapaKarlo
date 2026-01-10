package com.bunbeauty.core.domain.cart

import com.bunbeauty.core.model.cart.ConsumerCartDomain
import kotlinx.coroutines.flow.Flow

interface ICartProductInteractor {
    fun observeConsumerCart(): Flow<ConsumerCartDomain?>

    suspend fun removeAllProductsFromCart()
}
