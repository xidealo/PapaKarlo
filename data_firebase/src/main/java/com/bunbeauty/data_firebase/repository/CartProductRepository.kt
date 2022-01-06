package com.bunbeauty.data_firebase.repository

import com.bunbeauty.data_firebase.dao.CartProductDao
import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import com.example.domain_firebase.mapper.ICartProductMapper
import com.example.domain_firebase.model.entity.order.CartProductCountEntity
import com.example.domain_firebase.model.entity.product.CartProductEntity
import com.example.domain_firebase.model.entity.product.CartProductWithMenuProduct
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

    override suspend fun updateCartProductCount(cartProductUuid: String, count: Int): CartProduct? {
        val cartProductCountEntity = CartProductCountEntity(cartProductUuid, count)
        cartProductDao.updateCount(cartProductCountEntity)

        return null
    }

    override suspend fun deleteCartProduct(cartProduct: CartProduct) {
        cartProductDao.delete(cartProductMapper.toEntityModel(cartProduct))
    }

    override suspend fun deleteCartProductList(cartProductList: List<CartProduct>) {
        cartProductDao.delete(cartProductList.map(cartProductMapper::toEntityModel))
    }

    override suspend fun deleteAllCartProducts() {

    }

    private fun Flow<List<CartProductWithMenuProduct>>.mapCartProducts(): Flow<List<CartProduct>> {
        return this.flowOn(IO)
            .map { productList ->
                productList.filter { cartProduct ->
                    cartProduct.menuProductEntity.visible
                }.map(cartProductMapper::toUIModel)
                    .sortedBy { cartProduct ->
                        cartProduct.product.name
                    }
            }.flowOn(Default)
    }
}