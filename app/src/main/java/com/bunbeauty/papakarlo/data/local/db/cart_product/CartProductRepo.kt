package com.bunbeauty.papakarlo.data.local.db.cart_product

import androidx.lifecycle.LiveData
import com.bunbeauty.papakarlo.data.model.CartProduct

interface CartProductRepo {
    suspend fun insert(cartProduct: CartProduct): CartProduct

    fun getCartProductListLiveData(): LiveData<List<CartProduct>>

    suspend fun getCartProductList(): List<CartProduct>

    suspend fun update(cartProduct: CartProduct)

    suspend fun delete(cartProduct: CartProduct)
}