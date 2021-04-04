package com.example.data_api.repository

import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import com.example.data_api.dao.CartProductDao
import com.example.domain_api.mapper.ICartProductMapper
import com.example.domain_api.model.entity.product.CartProductCount
import com.example.domain_api.model.entity.product.CartProductEntity
import com.example.domain_api.model.entity.product.CartProductWithMenuProduct
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class CartProductRepository @Inject constructor(
    private val cartProductDao: CartProductDao,
    private val cartProductMapper: ICartProductMapper
) : CartProductRepo {

    override fun observeCartProductList(): Flow<List<CartProduct>> {
        return cartProductDao.observeCartProductList()
            .flowOn(IO)
            .map { cartProductList ->
                cartProductList.toCartProductList()
            }.flowOn(Default)
    }

    override suspend fun getCartProductList(): List<CartProduct> {
        return cartProductDao.getCartProductList().toCartProductList()
    }

    override suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): CartProduct? {
        return cartProductDao.getCartProductByMenuProductUuid(menuProductUuid).toCartProduct()
    }

    override suspend fun saveAsCartProduct(menuProductUuid: String): CartProduct? {
        val uuid = UUID.randomUUID().toString()
        val cartProductEntity = CartProductEntity(
            uuid = uuid,
            count = 1,
            menuProductUuid = menuProductUuid
        )
        cartProductDao.insert(cartProductEntity)

        return cartProductDao.getCartProductByUuid(uuid).toCartProduct()
    }

    override suspend fun updateCartProductCount(cartProductUuid: String, count: Int): CartProduct? {
        val cartProductCount = CartProductCount(
            uuid = cartProductUuid,
            count = count
        )
        cartProductDao.updateCartProductCount(cartProductCount)

        return cartProductDao.getCartProductByUuid(cartProductUuid)?.let { updatedCartProduct ->
            cartProductMapper.toModel(updatedCartProduct)
        }
    }

    override suspend fun deleteCartProduct(cartProduct: CartProduct) {
        cartProductDao.delete(cartProductMapper.toEntityModel(cartProduct))
    }

    override suspend fun deleteCartProductList(cartProductList: List<CartProduct>) {
        cartProductDao.delete(cartProductList.map(cartProductMapper::toEntityModel))
    }

    override suspend fun deleteAllCartProducts() {
        val cartProductList = cartProductDao.getCartProductList().map { cartProductWithMenuProduct ->
            cartProductWithMenuProduct.cartProductEntity
        }
        cartProductDao.delete(cartProductList)
    }

    private fun List<CartProductWithMenuProduct>.toCartProductList(): List<CartProduct> {
        return this.filter { cartProductWithMenuProduct ->
            cartProductWithMenuProduct.menuProductEntity.visible
        }.map(cartProductMapper::toModel)
    }

    private fun CartProductWithMenuProduct?.toCartProduct(): CartProduct? {
        return this?.let { cartProductWithMenuProduct ->
            if (cartProductWithMenuProduct.menuProductEntity.visible) {
                cartProductMapper.toModel(cartProductWithMenuProduct)
            } else {
                null
            }
        }
    }
}