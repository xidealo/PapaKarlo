package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.CartProductDao
import com.bunbeauty.domain.model.ui.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartProductRepository @Inject constructor(
    private val cartProductDao: CartProductDao
) : CartProductRepo {

    override val cartProductList: Flow<List<CartProduct>>
        get() = cartProductDao.getCartProductListLiveData()
            .flowOn(IO)
            .map { productList ->
                productList.sortedBy { cartProduct ->
                    cartProduct.menuProduct.name
                }
            }.flowOn(Default)

    override suspend fun getCartProductList(): List<CartProduct> = withContext(IO) {
        cartProductDao.getCartProductList()
    }

    override suspend fun getCartProduct(cartProductUuid: String): CartProduct? {
        return withContext(IO) {
            cartProductDao.getCartProduct(cartProductUuid)
        }
    }

    override suspend fun insert(cartProduct: CartProduct) {
        withContext(IO) {
            cartProductDao.insert(cartProduct)
        }
    }

    override suspend fun update(cartProduct: CartProduct) {
        withContext(IO) {
            cartProductDao.update(cartProduct)
        }
    }

    override suspend fun delete(cartProduct: CartProduct) {
        withContext(IO) {
            cartProductDao.delete(cartProduct)
        }
    }
}