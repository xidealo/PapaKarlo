package com.bunbeauty.shared.domain.feature.menu

import com.bunbeauty.shared.domain.repo.CartProductRepo

class AddMenuProductUseCase(
    private val cartProductRepo: CartProductRepo,
) {

    suspend operator fun invoke(menuProductUuid: String) {
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