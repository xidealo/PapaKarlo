package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.exeptions.CartProductNotFoundException
import com.bunbeauty.shared.domain.repo.CartProductRepo

class IncreaseCartProductCountUseCase(
    private val cartProductRepo: CartProductRepo
) {

    suspend operator fun invoke(cartProductUuid: String) {
        val cartProduct = cartProductRepo.getCartProduct(cartProductUuid) ?: throw CartProductNotFoundException()
        cartProductRepo.updateCartProductCount(
            cartProductUuid = cartProduct.uuid,
            count = cartProduct.count + 1
        )
    }

}