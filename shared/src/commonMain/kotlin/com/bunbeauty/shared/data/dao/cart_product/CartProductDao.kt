package com.bunbeauty.shared.data.dao.cart_product

import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

class CartProductDao(foodDeliveryDatabase: FoodDeliveryDatabase) : ICartProductDao {

    private val cartProductEntityQueries = foodDeliveryDatabase.cartProductEntityQueries

    override suspend fun insertCartProduct(cartProductEntity: CartProductEntity) {
        cartProductEntityQueries.insertCartProduct(
            uuid = cartProductEntity.uuid,
            count = cartProductEntity.count,
            menuProductUuid = cartProductEntity.menuProductUuid,
        )
    }

    override fun observeCartProductList(): Flow<List<CartProductWithMenuProductEntity>> {
        return cartProductEntityQueries.getCartProductList().asFlow().mapToList()
    }

    override suspend fun getCartProductList(): List<CartProductWithMenuProductEntity> {
        return cartProductEntityQueries.getCartProductList().executeAsList()
    }

    override suspend fun getCartProductByUuid(uuid: String): List<CartProductWithMenuProductEntity> {
        return cartProductEntityQueries.getCartProductByUuid(uuid).executeAsList()
    }

    override suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): List<CartProductWithMenuProductEntity> {
        return cartProductEntityQueries
            .getCartProductByMenuProductUuid(menuProductUuid)
            .executeAsList()
    }

    override suspend fun updateCartProductCountByUuid(uuid: String, count: Int) {
        cartProductEntityQueries.updateCartProductCountByUuid(
            uuid = uuid,
            count = count
        )
    }

    override suspend fun deleteCartProductByUuid(uuid: String) {
        cartProductEntityQueries.deleteCartProductByUuid(uuid)
    }

    override suspend fun deleteAllCartProducts() {
        cartProductEntityQueries.deleteAllCartProducts()
    }

}