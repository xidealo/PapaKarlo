package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.feature.addition.GetIsAdditionsAreEqualUseCase
import com.bunbeauty.shared.domain.repo.CartProductRepo

//TODO (tests)
class EditCartProductUseCase(
    private val cartProductRepo: CartProductRepo,
    private val cartProductAdditionRepository: CartProductAdditionRepository,
    private val additionRepository: AdditionRepository,
    private val getIsAdditionsAreEqualUseCase: GetIsAdditionsAreEqualUseCase,
) {
    suspend operator fun invoke(
        cartProductUuid: String,
        additionUuidList: List<String>,
    ) {
        val cartProduct =
            cartProductRepo.getCartProduct(cartProductUuid = cartProductUuid) ?: return

        if (
            getIsAdditionsAreEqualUseCase(
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