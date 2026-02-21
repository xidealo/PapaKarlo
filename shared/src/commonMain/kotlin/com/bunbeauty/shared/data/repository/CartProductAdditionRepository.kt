package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.repo.CartProductAdditionRepo
import com.bunbeauty.core.model.addition.Addition
import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.data.dao.cart_product_addition.ICartProductAdditionDao
import com.bunbeauty.shared.db.CartProductAdditionEntity

class CartProductAdditionRepository(
    private val uuidGenerator: UuidGenerator,
    private val cartProductAdditionDao: ICartProductAdditionDao,
) : CartProductAdditionRepo {
    override suspend fun saveAsCartProductAddition(
        cartProductUuid: String,
        addition: Addition,
    ) {
        val uuid = uuidGenerator.generateUuid()
        val cartProductAdditionEntity =
            CartProductAdditionEntity(
                uuid = uuid,
                name = addition.name,
                price = addition.price,
                additionUuid = addition.uuid,
                cartProductUuid = cartProductUuid,
                fullName = addition.fullName,
                priority = addition.priority,
            )
        cartProductAdditionDao.insertCartProductAddition(cartProductAdditionEntity)
    }

    override suspend fun delete(cartProductAdditionUuid: String) {
        cartProductAdditionDao.delete(cartProductAdditionUuid = cartProductAdditionUuid)
    }

    override suspend fun deleteAll() {
        cartProductAdditionDao.deleteAll()
    }
}
