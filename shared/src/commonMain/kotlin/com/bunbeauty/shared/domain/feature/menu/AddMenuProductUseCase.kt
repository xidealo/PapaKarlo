package com.bunbeauty.shared.domain.feature.menu

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.domain.exeptions.CartProductLimitReachedException
import com.bunbeauty.shared.domain.feature.cart.GetCartProductCountUseCase
import com.bunbeauty.shared.domain.repo.CartProductRepo

class AddMenuProductUseCase(
    private val getCartProductCountUseCase: GetCartProductCountUseCase,
    private val cartProductRepo: CartProductRepo
) {

    suspend operator fun invoke(menuProductUuid: String) {
        if (getCartProductCountUseCase() >= CART_PRODUCT_LIMIT) {
            throw CartProductLimitReachedException()
        }

        val cartProductList = cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid = menuProductUuid)
        if (cartProductList.isEmpty()) {
            cartProductRepo.saveAsCartProduct(menuProductUuid)
        } else {
            cartProductRepo.updateCartProductCount(
                cartProductUuid = cartProductList.first().uuid,
                count = cartProductList.first().count + 1
            )
        }
    }
}
