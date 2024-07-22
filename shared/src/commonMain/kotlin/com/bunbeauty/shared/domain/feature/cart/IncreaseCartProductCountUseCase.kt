package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.domain.exeptions.CartProductLimitReachedException
import com.bunbeauty.shared.domain.exeptions.CartProductNotFoundException
import com.bunbeauty.shared.domain.repo.CartProductRepo

class IncreaseCartProductCountUseCase(
    private val getCartProductCountUseCase: GetCartProductCountUseCase,
    private val cartProductRepo: CartProductRepo
) {

    suspend operator fun invoke(cartProductUuid: String) {
        if (getCartProductCountUseCase() >= CART_PRODUCT_LIMIT) {
            throw CartProductLimitReachedException()
        }

        val cartProduct = cartProductRepo.getCartProduct(cartProductUuid) ?: throw CartProductNotFoundException()
        cartProductRepo.updateCartProductCount(
            cartProductUuid = cartProduct.uuid,
            count = cartProduct.count + 1
        )
    }
}
