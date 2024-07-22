package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.repo.CartProductRepo

class RemoveCartProductUseCase(
    private val cartProductRepo: CartProductRepo,
    private val cartProductAdditionRepository: CartProductAdditionRepository
) {

    suspend operator fun invoke(cartProductUuid: String): Boolean {
        val cartProduct = cartProductRepo.getCartProduct(
            cartProductUuid = cartProductUuid
        ) ?: return false

        if (cartProduct.count > 1) {
            cartProductRepo.updateCartProductCount(
                cartProduct.uuid,
                cartProduct.count - 1
            )
        } else {
            cartProductRepo.deleteCartProduct(cartProduct.uuid)
            cartProduct.additionList.forEach { cartProductAddition ->
                cartProductAdditionRepository.delete(cartProductAdditionUuid = cartProductAddition.uuid)
            }
        }
        return true
    }
}
