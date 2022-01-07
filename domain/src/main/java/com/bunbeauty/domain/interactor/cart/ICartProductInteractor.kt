package com.bunbeauty.domain.interactor.cart

import com.bunbeauty.domain.model.Delivery
import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.model.product.LightCartProduct
import kotlinx.coroutines.flow.Flow

interface ICartProductInteractor {

    fun observeCartProductList(): Flow<List<LightCartProduct>>

    fun observeNewTotalCartCost(): Flow<Int>

    fun observeOldTotalCartCost(): Flow<Int?>

    fun observeTotalCartCount(): Flow<Int>

    fun observeDeliveryCost(): Flow<Int>

    fun observeDelivery(): Flow<Delivery>

    fun observeAmountToPay(): Flow<Int>

    suspend fun addProductToCart(menuProductUuid: String): CartProduct?

    suspend fun removeProductFromCart(menuProductUuid: String)

    suspend fun removeAllProductsFromCart()
}