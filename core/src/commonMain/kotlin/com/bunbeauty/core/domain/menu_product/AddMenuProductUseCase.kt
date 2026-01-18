package com.bunbeauty.core.domain.menu_product

import com.bunbeauty.core.Constants
import com.bunbeauty.core.domain.GetCartProductCountUseCase
import com.bunbeauty.core.domain.exeptions.CartProductLimitReachedException
import com.bunbeauty.core.domain.repo.CartProductRepo

class AddMenuProductUseCase(
    private val getCartProductCountUseCase: GetCartProductCountUseCase,
    private val cartProductRepo: CartProductRepo,
) {
    suspend operator fun invoke(menuProductUuid: String) {
        if (getCartProductCountUseCase() >= Constants.CART_PRODUCT_LIMIT) {
            throw CartProductLimitReachedException()
        }

        val cartProductList = cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid = menuProductUuid)
        if (cartProductList.isEmpty()) {
            cartProductRepo.saveAsCartProduct(menuProductUuid)
        } else {
            cartProductRepo.updateCartProductCount(
                cartProductUuid = cartProductList.first().uuid,
                count = cartProductList.first().count + 1,
            )
        }
    }
}