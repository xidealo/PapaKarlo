package com.example.data_api.repository

import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartProductRepository @Inject constructor(): CartProductRepo {

    override fun observeCartProductList(): Flow<List<CartProduct>> {
        //TODO("Not yet implemented")

        return Any() as Flow<List<CartProduct>>
    }

    override suspend fun getCartProductList(): List<CartProduct> {
        //TODO("Not yet implemented")

        return Any() as List<CartProduct>
    }

    override suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): CartProduct? {
        //TODO("Not yet implemented")

        return Any() as CartProduct?
    }

    override suspend fun saveAsCartProduct(menuProductUuid: String): CartProduct? {
        //TODO("Not yet implemented")

        return Any() as CartProduct?
    }

    override suspend fun updateCount(cartProductUuid: String, count: Int) {
        //TODO("Not yet implemented")
    }

    override suspend fun deleteCartProduct(cartProduct: CartProduct) {
        //TODO("Not yet implemented")
    }

    override suspend fun deleteCartProductList(cartProductList: List<CartProduct>) {
        //TODO("Not yet implemented")
    }
}