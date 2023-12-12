package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.ConsumerCartDomain

interface ICartProductInteractor {

    fun observeConsumerCart(): CommonFlow<ConsumerCartDomain?>

    suspend fun getCartProductList(): List<CartProduct>

    fun observeTotalCartCount(): CommonFlow<Int>

    suspend fun removeAllProductsFromCart()
}