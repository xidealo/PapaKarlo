package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.ConsumerCartDomain

interface ICartProductInteractor {

    fun observeConsumerCart(): CommonFlow<ConsumerCartDomain?>

    fun observeNewTotalCartCost(): CommonFlow<Int>

    fun observeTotalCartCount(): CommonFlow<Int>

    suspend fun addProductToCart(menuProductUuid: String): CartProduct?

    suspend fun removeAllProductsFromCart()
}