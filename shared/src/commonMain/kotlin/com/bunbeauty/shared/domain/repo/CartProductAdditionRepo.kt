package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.addition.Addition

interface CartProductAdditionRepo {
    suspend fun saveAsCartProductAddition(cartProductUuid: String, addition: Addition)
    suspend fun delete(cartProductAdditionUuid: String)
    suspend fun deleteAll()
}
