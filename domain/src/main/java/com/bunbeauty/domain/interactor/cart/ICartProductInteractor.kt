package com.bunbeauty.domain.interactor.cart

import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.CartTotal
import com.bunbeauty.shared.domain.model.cart.ConsumerCart
import kotlinx.coroutines.flow.Flow

interface ICartProductInteractor {

    suspend fun getConsumerCart(): ConsumerCart?

    fun observeConsumerCart(): Flow<ConsumerCart?>

    fun observeNewTotalCartCost(): Flow<Int>

    fun observeTotalCartCount(): Flow<Int>

    fun observeDeliveryCost(): Flow<Int>

    fun observeCartTotal(isDeliveryFlow: Flow<Boolean>): Flow<CartTotal>

    suspend fun addProductToCart(menuProductUuid: String): CartProduct?

    suspend fun removeProductFromCart(menuProductUuid: String)

    suspend fun removeAllProductsFromCart()
}