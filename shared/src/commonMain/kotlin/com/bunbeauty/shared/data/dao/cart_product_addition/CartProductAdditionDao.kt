package com.bunbeauty.shared.data.dao.cart_product_addition

import com.bunbeauty.shared.db.CartProductAdditionEntity
import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.db.FoodDeliveryDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

class CartProductAdditionDao(foodDeliveryDatabase: FoodDeliveryDatabase) : ICartProductAdditionDao {

    private val cartProductAdditionEntityQueries =
        foodDeliveryDatabase.cartProductAdditionEntityQueries

    override suspend fun insertCartProductAddition(cartProductAdditionEntity: CartProductAdditionEntity) {
        cartProductAdditionEntityQueries.insertCartProductAdditionEntity(cartProductAdditionEntity)
    }

    override suspend fun delete(cartProductAdditionUuid: String) {
        cartProductAdditionEntityQueries.deleteCartProductAdditionByUuid(cartProductAdditionUuid)
    }

    override suspend fun deleteAll() {
        cartProductAdditionEntityQueries.deleteAllCartProductAdditions()
    }

}