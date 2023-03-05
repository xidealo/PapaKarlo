package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.shared.domain.model.cart.CartCostAndCount
import com.bunbeauty.shared.domain.repo.CartProductRepo
import kotlinx.coroutines.flow.map

class ObserveCartUseCase(
    private val cartProductRepo: CartProductRepo,
) {

    operator fun invoke(): CommonFlow<CartCostAndCount> {
        return cartProductRepo.observeCartProductList().map { cartProductList ->
            CartCostAndCount(
                cost = cartProductList.sumOf { cartProduct ->
                    cartProduct.product.newPrice * cartProduct.count
                }.toString(),
                count = cartProductList.sumOf { cartProduct ->
                    cartProduct.count
                }.toString(),
            )
        }.asCommonFlow()
    }
}