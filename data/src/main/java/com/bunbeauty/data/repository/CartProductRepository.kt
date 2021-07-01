package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.CartProductDao
import com.bunbeauty.domain.model.local.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CartProductRepository @Inject constructor(private val cartProductDao: CartProductDao) :
    CartProductRepo {

    override suspend fun insertToLocal(cartProduct: CartProduct): CartProduct {
        cartProduct.id = cartProductDao.insert(cartProduct)
        return cartProduct
    }

    override fun getCartProductListFlow() = cartProductDao.getCartProductListLiveData().flowOn(Dispatchers.IO)

    override suspend fun getCartProductList(): List<CartProduct> = cartProductDao.getCartProductList()

    override suspend fun update(cartProduct: CartProduct) {
        return cartProductDao.update(cartProduct)
    }

    override suspend fun delete(cartProduct: CartProduct) {
        cartProductDao.delete(cartProduct)
    }
}