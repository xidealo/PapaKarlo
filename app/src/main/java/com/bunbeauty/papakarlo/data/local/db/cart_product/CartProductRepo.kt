package com.bunbeauty.papakarlo.data.local.db.cart_product

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.model.CartProduct
import kotlinx.coroutines.Deferred

interface CartProductRepo {
    suspend fun insert(cartProduct: CartProduct): CartProduct

    fun getCartProductList(): LiveData<List<CartProduct>>
    suspend fun getCartProductListAsync(): Deferred<List<CartProduct>>

    suspend fun update(cartProduct: CartProduct)

    suspend fun delete(cartProduct: CartProduct)
}