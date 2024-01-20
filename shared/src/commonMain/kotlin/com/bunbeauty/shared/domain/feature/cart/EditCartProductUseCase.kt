package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.repo.CartProductRepo

class EditCartProductUseCase(
    private val cartProductRepo: CartProductRepo,
    private val cartProductAdditionRepository: CartProductAdditionRepository,
    private val additionRepository: AdditionRepository,
    private val areAdditionsEqualUseCase: AreAdditionsEqualUseCase,
) {
    suspend operator fun invoke(
        cartProductUuid: String,
        additionUuidList: List<String>,
    ) {
        val cartProduct =
            cartProductRepo.getCartProduct(cartProductUuid = cartProductUuid) ?: return

        if (
            areAdditionsEqualUseCase(
                initialCartProduct = cartProduct,
                additionUuidList = additionUuidList
            )
        ) {
            return
        }

        cartProduct.cartProductAdditionList.forEach { cartProductAddition ->
            cartProductAdditionRepository.delete(cartProductAdditionUuid = cartProductAddition.uuid)
        }

        additionUuidList.forEach { additionUuid ->
            additionRepository.getAddition(uuid = additionUuid)?.let { addition ->
                cartProductAdditionRepository.saveAsCartProductAddition(
                    cartProductUuid = cartProductUuid,
                    addition = addition
                )
            }
        }
    }
}