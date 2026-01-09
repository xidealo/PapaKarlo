package com.bunbeauty.core.domain

import com.bunbeauty.core.domain.repo.CartProductRepo

interface GetCartProductCountUseCase {
    suspend operator fun invoke(): Int
}

class GetCartProductCountUseCaseImpl(
    private val cartProductRepo: CartProductRepo,
) : GetCartProductCountUseCase {
    override suspend operator fun invoke(): Int =
        cartProductRepo.getCartProductList().sumOf { cartProduct ->
            cartProduct.count
        }
}
