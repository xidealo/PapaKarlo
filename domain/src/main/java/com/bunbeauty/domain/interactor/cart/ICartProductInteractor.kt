package com.bunbeauty.domain.interactor.cart

import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.model.cart.CartProduct
import com.bunbeauty.domain.model.cart.CartTotal
import com.bunbeauty.domain.model.cart.ConsumerCart
import com.bunbeauty.domain.model.cart.LightCartProduct
import kotlinx.coroutines.flow.Flow

interface ICartProductInteractor {

    suspend fun getConsumerCart(): ConsumerCart?

    fun observeConsumerCart(): Flow<ConsumerCart?>

    fun observeCartProductList(): Flow<List<LightCartProduct>>

    fun observeNewTotalCartCost(): Flow<Int>

    fun observeOldTotalCartCost(): Flow<Int?>

    fun observeTotalCartCount(): Flow<Int>

    fun observeDeliveryCost(): Flow<Int>

    fun observeCartTotal(isDeliveryFlow: Flow<Boolean>): Flow<CartTotal>

    fun observeAmountToPay(isDeliveryFlow: Flow<Boolean>): Flow<Int>

    suspend fun addProductToCart(menuProductUuid: String): CartProduct?

    suspend fun removeProductFromCart(menuProductUuid: String)

    suspend fun removeAllProductsFromCart()
}