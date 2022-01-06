package com.bunbeauty.domain.interactor.cart

import com.bunbeauty.domain.model.product.CartProduct
import kotlinx.coroutines.flow.Flow

interface ICartProductInteractor {

    fun observeNewTotalCartCost(): Flow<Int>

    fun observeTotalCartCount(): Flow<Int>

    fun observeDeliveryCost(): Flow<Int>

    fun observeAmountToPay(): Flow<Int>

    suspend fun addProductToCart(menuProductUuid: String): CartProduct?

    suspend fun removeProductFromCart(menuProductUuid: String)

    suspend fun removeAllProductsFromCart()
}