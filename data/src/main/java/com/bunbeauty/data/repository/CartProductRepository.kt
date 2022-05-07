package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.cart_product.ICartProductDao
import com.bunbeauty.data.mapper.cart_product.ICartProductMapper
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo
import database.CartProductEntity
import database.CartProductWithMenuProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class CartProductRepository(
    private val cartProductDao: ICartProductDao,
    private val cartProductMapper: ICartProductMapper
) : CartProductRepo {

    override fun observeCartProductList(): Flow<List<CartProduct>> {
        return cartProductDao.observeCartProductList().map { cartProductList ->
            cartProductList.toCartProductList()
        }
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
        cartProductDao.insertCartProduct(cartProductEntity)

        return cartProductDao.getCartProductByUuid(uuid).toCartProduct()
    }

    override suspend fun updateCartProductCount(cartProductUuid: String, count: Int): CartProduct? {
        cartProductDao.updateCartProductCountByUuid(cartProductUuid, count)

        return cartProductDao.getCartProductByUuid(cartProductUuid)?.let { updatedCartProduct ->
            cartProductMapper.toModel(updatedCartProduct)
        }
    }

    override suspend fun deleteCartProduct(cartProduct: CartProduct) {
        cartProductDao.deleteCartProductByUuid(cartProduct.uuid)
    }

    override suspend fun deleteAllCartProducts() {
        cartProductDao.deleteAllCartProducts()
    }

    private fun List<CartProductWithMenuProductEntity>.toCartProductList(): List<CartProduct> {
        return this.filter { cartProductWithMenuProduct ->
            cartProductWithMenuProduct.visible
        }.map(cartProductMapper::toModel)
    }

    private fun CartProductWithMenuProductEntity?.toCartProduct(): CartProduct? {
        return this?.let { cartProductWithMenuProductEntity ->
            if (cartProductWithMenuProductEntity.visible) {
                cartProductMapper.toModel(cartProductWithMenuProductEntity)
            } else {
                null
            }
        }
    }
}