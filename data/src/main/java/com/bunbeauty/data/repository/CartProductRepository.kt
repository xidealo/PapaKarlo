package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.CartProductDao
import com.bunbeauty.domain.mapper.ICartProductMapper
import com.bunbeauty.domain.model.entity.order.CartProductCountEntity
import com.bunbeauty.domain.model.entity.product.CartProductEntity
import com.bunbeauty.domain.model.entity.product.CartProductWithMenuProduct
import com.bunbeauty.domain.model.ui.product.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class CartProductRepository @Inject constructor(
    private val cartProductDao: CartProductDao,
    private val cartProductMapper: ICartProductMapper,
) : CartProductRepo {

    override fun observeCartProductList(): Flow<List<CartProduct>> {
        return cartProductDao.observeCartProductList().mapCartProducts()
    }

    override suspend fun getCartProductList(): List<CartProduct> {
        return cartProductDao.getCartProductList().map(cartProductMapper::toUIModel)
    }

    override suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): CartProduct? {
        return cartProductDao.getCartProductByMenuProductUuid(menuProductUuid)?.let { cartProduct ->
            cartProductMapper.toUIModel(cartProduct)
        }
    }

    override suspend fun saveAsCartProduct(menuProductUuid: String): CartProduct? {
        val cartProduct = CartProductEntity(
            uuid = UUID.randomUUID().toString(),
            count = 1,
            menuProductUuid = menuProductUuid
        )
        cartProductDao.insert(cartProduct)
        val insertedCartProduct = cartProductDao.getCartProductByMenuProductUuid(menuProductUuid)
        return insertedCartProduct?.let {
            cartProductMapper.toUIModel(insertedCartProduct)
        }
    }

    override suspend fun updateCount(cartProductUuid: String, count: Int) {
        val cartProductCountEntity = CartProductCountEntity(cartProductUuid, count)
        cartProductDao.updateCount(cartProductCountEntity)
    }

    override suspend fun deleteCartProduct(cartProduct: CartProduct) {
        cartProductDao.delete(cartProductMapper.toEntityModel(cartProduct))
    }

    override suspend fun deleteCartProductList(cartProductList: List<CartProduct>) {
        cartProductDao.delete(cartProductList.map(cartProductMapper::toEntityModel))
    }

    private fun Flow<List<CartProductWithMenuProduct>>.mapCartProducts(): Flow<List<CartProduct>> {
        return this.flowOn(IO)
            .map { productList ->
                productList.map(cartProductMapper::toUIModel)
                    .sortedBy { cartProduct ->
                        cartProduct.menuProduct.name
                    }
            }.flowOn(Default)
    }
}