package com.bunbeauty.data.repository

import com.bunbeauty.data.database.dao.CartProductDao
import com.bunbeauty.data.database.entity.product.CartProductCount
import com.bunbeauty.data.database.entity.product.CartProductEntity
import com.bunbeauty.data.database.entity.product.CartProductWithMenuProduct
import com.bunbeauty.data.mapper.cart_product.ICartProductMapper
import com.bunbeauty.domain.model.cart.CartProduct
import com.bunbeauty.domain.repo.CartProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class CartProductRepository  constructor(
    private val cartProductDao: CartProductDao,
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
        val cartProductList =
            cartProductDao.getCartProductList().map { cartProductWithMenuProduct ->
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