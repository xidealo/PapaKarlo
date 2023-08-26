package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.CartTotal
import com.bunbeauty.shared.domain.model.cart.ConsumerCart
import kotlinx.coroutines.flow.Flow

interface ICartProductInteractor {

    fun observeConsumerCart(): CommonFlow<ConsumerCart?>

    fun observeNewTotalCartCost(): CommonFlow<Int>

    fun observeTotalCartCount(): CommonFlow<Int>

    suspend fun addProductToCart(menuProductUuid: String): CartProduct?

    suspend fun removeAllProductsFromCart()
}