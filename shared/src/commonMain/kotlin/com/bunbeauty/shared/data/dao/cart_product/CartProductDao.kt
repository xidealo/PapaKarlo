package com.bunbeauty.shared.data.dao.cart_product

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class CartProductDao(
    foodDeliveryDatabase: FoodDeliveryDatabase,
) : ICartProductDao {
    private val cartProductEntityQueries = foodDeliveryDatabase.cartProductEntityQueries

    override suspend fun insertCartProduct(cartProductEntity: CartProductEntity) {
        cartProductEntityQueries.insertCartProduct(
            uuid = cartProductEntity.uuid,
            count = cartProductEntity.count,
            menuProductUuid = cartProductEntity.menuProductUuid,
        )
    }

    override fun observeCartProductList(): Flow<List<CartProductWithMenuProductEntity>> =
        cartProductEntityQueries
            .getCartProductList()
            .asFlow()
            .mapToList(Dispatchers.Default)

    override suspend fun getCartProductList(): List<CartProductWithMenuProductEntity> =
        cartProductEntityQueries.getCartProductList().awaitAsList()

    override suspend fun getCartProductByUuid(uuid: String): List<CartProductWithMenuProductEntity> =
        cartProductEntityQueries.getCartProductByUuid(uuid).awaitAsList()

    override suspend fun getCartProductByMenuProductUuid(menuProductUuid: String): List<CartProductWithMenuProductEntity> =
        cartProductEntityQueries
            .getCartProductByMenuProductUuid(menuProductUuid)
            .awaitAsList()

    override suspend fun updateCartProductCountByUuid(
        uuid: String,
        count: Int,
    ) {
        cartProductEntityQueries.updateCartProductCountByUuid(
            uuid = uuid,
            count = count,
        )
    }

    override suspend fun deleteCartProductByUuid(uuid: String) {
        cartProductEntityQueries.deleteCartProductByUuid(uuid)
    }

    override suspend fun deleteAllCartProducts() {
        cartProductEntityQueries.deleteAllCartProducts()
    }
}
