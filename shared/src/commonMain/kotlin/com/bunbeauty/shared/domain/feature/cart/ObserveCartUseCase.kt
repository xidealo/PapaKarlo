package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.shared.domain.interactor.cart.GetNewTotalCostUseCase
import com.bunbeauty.shared.domain.model.cart.CartCostAndCount
import com.bunbeauty.shared.domain.repo.CartProductRepo
import kotlinx.coroutines.flow.map

class ObserveCartUseCase(
    private val cartProductRepo: CartProductRepo,
    private val getNewTotalCostUseCase: GetNewTotalCostUseCase,
) {
    operator fun invoke(): CommonFlow<CartCostAndCount> {
        return cartProductRepo.observeCartProductList().map { cartProductList ->
            CartCostAndCount(
                cost = getNewTotalCostUseCase(cartProductList).toString(),
                count = cartProductList.sumOf { cartProduct ->
                    cartProduct.count
                }.toString(),
            )
        }.asCommonFlow()
    }
}