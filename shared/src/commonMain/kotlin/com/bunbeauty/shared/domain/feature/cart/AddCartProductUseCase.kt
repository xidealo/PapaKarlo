package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.domain.repo.CartProductRepo

class AddCartProductUseCase(
    private val cartProductRepo: CartProductRepo,
) {

    suspend operator fun invoke(menuProductUuid: String): Boolean {
        val cartCount = cartProductRepo.getCartProductList().sumOf { cartProduct ->
            cartProduct.count
        }
        if (cartCount >= CART_PRODUCT_LIMIT) {
            return false
        }

        val cartProduct = cartProductRepo.getCartProductByMenuProductUuid(menuProductUuid)
        val cardProduct = if (cartProduct == null) {
            cartProductRepo.saveAsCartProduct(menuProductUuid)
        } else {
            cartProductRepo.updateCartProductCount(cartProduct.uuid, cartProduct.count + 1)
        }
        return cardProduct != null
    }
}