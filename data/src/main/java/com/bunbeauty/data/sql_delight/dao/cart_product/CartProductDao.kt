package com.bunbeauty.data.sql_delight.dao.cart_product

import com.bunbeauty.data.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import database.CartProductEntity
import database.CartProductWithMenuProductEntity
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

    override suspend fun getCartProductByUuid(uuid: String): CartProductWithMenuProductEntity? {
        return cartProductEntityQueries.getCartProductByUuid(uuid).executeAsOneOrNull()
    }

    override suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): CartProductWithMenuProductEntity? {
        return cartProductEntityQueries.getCartProductByMenuProductUuid(menuProductUuid)
            .executeAsOneOrNull()
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