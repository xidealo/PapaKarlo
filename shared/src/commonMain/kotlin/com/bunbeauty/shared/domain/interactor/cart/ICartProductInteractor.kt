package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.CartTotal
import com.bunbeauty.shared.domain.model.cart.CartWithProducts
import com.bunbeauty.shared.domain.model.cart.ConsumerCart
import kotlinx.coroutines.flow.Flow

interface ICartProductInteractor {

    suspend fun getConsumerCart(): ConsumerCart?

    suspend fun getConsumerCartWithProducts(): CartWithProducts?

    fun observeConsumerCart(): Flow<ConsumerCart?>

    fun observeNewTotalCartCost(): Flow<Int>

    fun observeNewTotalCartCostForIos(): CommonFlow<Int>

    fun observeTotalCartCount(): Flow<Int>

    fun observeTotalCartCountForIos(): CommonFlow<Int>

    fun observeDeliveryCost(): Flow<Int>

    fun observeCartTotal(isDeliveryFlow: Flow<Boolean>): Flow<CartTotal>

    suspend fun addProductToCart(menuProductUuid: String): CartProduct?

    suspend fun removeProductFromCart(menuProductUuid: String)

    suspend fun removeAllProductsFromCart()
}