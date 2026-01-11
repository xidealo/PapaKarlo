package com.bunbeauty.core.domain.cart

import com.bunbeauty.core.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.core.domain.GetCartProductCountUseCase
import com.bunbeauty.core.domain.exeptions.CartProductLimitReachedException
import com.bunbeauty.core.domain.exeptions.CartProductNotFoundException
import com.bunbeauty.core.domain.repo.CartProductRepo

class IncreaseCartProductCountUseCase(
    private val getCartProductCountUseCase: GetCartProductCountUseCase,
    private val cartProductRepo: CartProductRepo,
) {
    suspend operator fun invoke(cartProductUuid: String) {
        if (getCartProductCountUseCase() >= CART_PRODUCT_LIMIT) {
            throw CartProductLimitReachedException()
        }

        val cartProduct = cartProductRepo.getCartProduct(cartProductUuid) ?: throw CartProductNotFoundException()
        cartProductRepo.updateCartProductCount(
            cartProductUuid = cartProduct.uuid,
            count = cartProduct.count + 1,
        )
    }
}
