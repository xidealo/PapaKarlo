package com.bunbeauty.core.domain

import com.bunbeauty.core.domain.repo.CartProductRepo
import com.bunbeauty.core.model.cart.CartCostAndCount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveCartUseCase(
    private val cartProductRepo: CartProductRepo,
    private val getNewTotalCostUseCase: GetNewTotalCostUseCase,
) {
    operator fun invoke(): Flow<CartCostAndCount> =
        cartProductRepo
            .observeCartProductList()
            .map { cartProductList ->
                CartCostAndCount(
                    cost = getNewTotalCostUseCase(cartProductList).toString(),
                    count =
                        cartProductList
                            .sumOf { cartProduct ->
                                cartProduct.count
                            }.toString(),
                )
            }
}