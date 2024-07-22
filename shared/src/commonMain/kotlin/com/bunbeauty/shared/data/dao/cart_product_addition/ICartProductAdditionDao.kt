package com.bunbeauty.shared.data.dao.cart_product_addition

import com.bunbeauty.shared.db.CartProductAdditionEntity

interface ICartProductAdditionDao {

    suspend fun insertCartProductAddition(cartProductAdditionEntity: CartProductAdditionEntity)
    suspend fun delete(cartProductAdditionUuid: String)
    suspend fun deleteAll()
}
