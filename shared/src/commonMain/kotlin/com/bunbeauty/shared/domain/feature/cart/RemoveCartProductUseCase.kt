package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.repo.CartProductRepo

class RemoveCartProductUseCase(
    private val cartProductRepo: CartProductRepo,
) {

    suspend operator fun invoke(menuProductUuid: String): Boolean {
        val cartProduct =
            cartProductRepo.getCartProductByMenuProductUuid(menuProductUuid) ?: return false
        if (cartProduct.count > 1) {
            cartProductRepo.updateCartProductCount(
                cartProduct.uuid,
                cartProduct.count - 1
            )
        } else {
            cartProductRepo.deleteCartProduct(cartProduct.uuid)
        }

        return true
    }
}