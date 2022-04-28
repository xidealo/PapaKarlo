package com.bunbeauty.domain.interactor.cart

import com.bunbeauty.domain.model.cart.CartProduct
import com.bunbeauty.domain.model.cart.CartTotal
import com.bunbeauty.domain.model.cart.ConsumerCart
import com.bunbeauty.domain.model.cart.LightCartProduct
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