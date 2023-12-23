package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.data.dao.cart_product_addition.ICartProductAdditionDao
import com.bunbeauty.shared.db.CartProductAdditionEntity
import com.bunbeauty.shared.domain.model.addition.Addition

class CartProductAdditionRepository(
    private val uuidGenerator: UuidGenerator,
    private val cartProductAdditionDao: ICartProductAdditionDao,
) {
    suspend fun saveAsCartProductAddition(cartProductUuid: String, addition: Addition) {
        val uuid = uuidGenerator.generateUuid()
        val cartProductAdditionEntity = CartProductAdditionEntity(
            uuid = uuid,
            name = addition.name,
            price = addition.price,
            additionUuid = addition.uuid,
            cartProductUuid = cartProductUuid,
            fullName = addition.fullName
        )
        cartProductAdditionDao.insertCartProductAddition(cartProductAdditionEntity)
    }

    suspend fun delete(cartProductAdditionUuid: String) {
        cartProductAdditionDao.delete(cartProductAdditionUuid = cartProductAdditionUuid)
    }
}