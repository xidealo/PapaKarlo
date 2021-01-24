package com.bunbeauty.papakarlo.data.local.db.cart_product

import com.bunbeauty.papakarlo.data.model.CartProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartProductRepository @Inject constructor(private val cartProductDao: CartProductDao) :
    CartProductRepo {

    override suspend fun insert(cartProduct: CartProduct): CartProduct {
        cartProduct.id = cartProductDao.insert(cartProduct)
        return cartProduct
    }

    override fun getCartProductList() =
        cartProductDao.getCartProductListLiveData()

    override suspend fun getCartProductListAsync() =
        withContext(Dispatchers.IO) {
            async {
                cartProductDao.getCartProductList()
            }
        }

    override suspend fun update(cartProduct: CartProduct) {
        return cartProductDao.update(cartProduct)
    }

    override suspend fun delete(cartProduct: CartProduct) {
        cartProductDao.delete(cartProduct)
    }
}