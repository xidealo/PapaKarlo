package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.repo.CartProductRepo

class GetCartProductCountUseCase(
    private val cartProductRepo: CartProductRepo,
) {

    suspend operator fun invoke(): Int {
        return cartProductRepo.getCartProductList().sumOf { cartProduct ->
            cartProduct.count
        }
    }
}