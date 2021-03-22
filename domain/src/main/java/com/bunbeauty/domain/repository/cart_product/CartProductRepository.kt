package com.bunbeauty.domain.repository.cart_product

import com.bunbeauty.data.model.CartProduct
import javax.inject.Inject

class CartProductRepository @Inject constructor(private val cartProductDao: CartProductDao) :
    CartProductRepo {

    override suspend fun insert(cartProduct: CartProduct): CartProduct {
        cartProduct.id = cartProductDao.insert(cartProduct)
        return cartProduct
    }

    override fun getCartProductListLiveData() = cartProductDao.getCartProductListLiveData()

    override suspend fun getCartProductList(): List<CartProduct> = cartProductDao.getCartProductList()

    override suspend fun update(cartProduct: CartProduct) {
        return cartProductDao.update(cartProduct)
    }

    override suspend fun delete(cartProduct: CartProduct) {
        cartProductDao.delete(cartProduct)
    }
}