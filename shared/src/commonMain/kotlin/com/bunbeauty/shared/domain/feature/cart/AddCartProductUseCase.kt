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

        val initialCartProduct = cartProductRepo.getCartProductByMenuProductUuid(menuProductUuid)
        val cartProduct = if (initialCartProduct == null) {
            cartProductRepo.saveAsCartProduct(menuProductUuid)
        } else {
            cartProductRepo.updateCartProductCount(
                cartProductUuid = initialCartProduct.uuid,
                count = initialCartProduct.count + 1
            )
        }
        return cartProduct != null
    }
}