package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.data.dao.cart_product.ICartProductDao
import com.bunbeauty.shared.data.mapper.cart_product.ICartProductMapper
import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartProductRepository(
    private val uuidGenerator: UuidGenerator,
    private val cartProductDao: ICartProductDao,
    private val cartProductMapper: ICartProductMapper,
) : CartProductRepo {

    override fun observeCartProductList(): Flow<List<CartProduct>> {
        return cartProductDao.observeCartProductList().map { cartProductList ->
            cartProductList.toCartProductList()
        }
    }

    override suspend fun getCartProductList(): List<CartProduct> {
        return cartProductDao.getCartProductList().toCartProductList()
    }

    override suspend fun getCartProduct(cartProductUuid: String): CartProduct? {
        return cartProductDao
            .getCartProductByUuid(cartProductUuid)
            .toCartProductList()
            .firstOrNull()
    }

    override suspend fun getCartProductListByMenuProductUuid(
        menuProductUuid: String,
    ): List<CartProduct> {
        return cartProductDao.getCartProductByMenuProductUuid(menuProductUuid)
            .toCartProductList()
    }

    override suspend fun saveAsCartProduct(menuProductUuid: String): String {
        val uuid = uuidGenerator.generateUuid()
        val cartProductEntity = CartProductEntity(
            uuid = uuid,
            count = 1,
            menuProductUuid = menuProductUuid
        )
        cartProductDao.insertCartProduct(cartProductEntity)

        return uuid
    }

    override suspend fun updateCartProductCount(cartProductUuid: String, count: Int) {
        cartProductDao.updateCartProductCountByUuid(cartProductUuid, count)
    }

    override suspend fun deleteCartProduct(cartProductUuid: String) {
        cartProductDao.deleteCartProductByUuid(cartProductUuid)
    }

    override suspend fun deleteAllCartProducts() {
        cartProductDao.deleteAllCartProducts()
    }

    private fun List<CartProductWithMenuProductEntity>.toCartProductList(): List<CartProduct> {
        return cartProductMapper.toCartProductList(
            this.filter { cartProductWithMenuProduct ->
                cartProductWithMenuProduct.visible
            }
        )
    }
}