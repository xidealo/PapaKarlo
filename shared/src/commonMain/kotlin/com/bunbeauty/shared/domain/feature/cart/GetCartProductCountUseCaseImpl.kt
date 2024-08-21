package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.repo.CartProductRepo

interface GetCartProductCountUseCase {
    suspend operator fun invoke(): Int
}

class GetCartProductCountUseCaseImpl(
    private val cartProductRepo: CartProductRepo
) : GetCartProductCountUseCase {
    override suspend operator fun invoke(): Int {
        return cartProductRepo.getCartProductList().sumOf { cartProduct ->
            cartProduct.count
        }
    }
}
