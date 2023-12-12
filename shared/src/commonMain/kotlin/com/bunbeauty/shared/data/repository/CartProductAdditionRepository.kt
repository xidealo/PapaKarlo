package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.UuidGenerator
import com.bunbeauty.shared.data.dao.cart_product.ICartProductDao
import com.bunbeauty.shared.data.dao.cart_product_addition.ICartProductAdditionDao
import com.bunbeauty.shared.data.mapper.cart_product.ICartProductMapper
import com.bunbeauty.shared.db.CartProductAdditionEntity
import com.bunbeauty.shared.db.CartProductEntity
import com.bunbeauty.shared.db.CartProductWithMenuProductEntity
import com.bunbeauty.shared.domain.model.addition.Addition
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.CartProductAddition
import com.bunbeauty.shared.domain.repo.CartProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
        )
        cartProductAdditionDao.insertCartProductAddition(cartProductAdditionEntity)
    }
}