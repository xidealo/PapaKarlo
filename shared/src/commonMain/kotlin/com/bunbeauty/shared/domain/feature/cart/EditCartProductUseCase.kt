package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.model.addition.Addition
import com.bunbeauty.shared.domain.repo.CartProductAdditionRepo
import com.bunbeauty.shared.domain.repo.CartProductRepo

class EditCartProductUseCase(
    private val cartProductRepo: CartProductRepo,
    private val cartProductAdditionRepository: CartProductAdditionRepo,
    private val areAdditionsEqualUseCase: AreAdditionsEqualUseCase
) {
    suspend operator fun invoke(
        cartProductUuid: String,
        additionList: List<Addition>
    ) {
        val cartProduct =
            cartProductRepo.getCartProduct(cartProductUuid = cartProductUuid) ?: return

        if (
            areAdditionsEqualUseCase(
                cartProduct = cartProduct,
                additionUuidList = additionList.map { addition ->
                    addition.uuid
                }
            )
        ) {
            return
        }

        cartProduct.additionList.forEach { cartProductAddition ->
            cartProductAdditionRepository.delete(cartProductAdditionUuid = cartProductAddition.uuid)
        }

        additionList.forEach { addition ->
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = cartProductUuid,
                addition = addition
            )
        }
    }
}
