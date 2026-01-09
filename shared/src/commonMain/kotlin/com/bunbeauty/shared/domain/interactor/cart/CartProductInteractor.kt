package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.core.model.cart.CartProduct
import com.bunbeauty.core.model.cart.ConsumerCartDomain
import com.bunbeauty.core.model.cart.LightCartProduct
import com.bunbeauty.shared.domain.repo.CartProductAdditionRepo
import com.bunbeauty.core.domain.repo.CartProductRepo
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class CartProductInteractor(
    private val cartProductRepo: CartProductRepo,
    private val getCartTotalFlowUseCase: GetCartTotalFlowUseCase,
    private val cartProductAdditionRepository: CartProductAdditionRepo,
) : ICartProductInteractor {
    override fun observeConsumerCart(): CommonFlow<ConsumerCartDomain?> =
        cartProductRepo
            .observeCartProductList()
            .map { cartProductList ->
                getConsumerCart(cartProductList)
            }.asCommonFlow()

    override suspend fun removeAllProductsFromCart() {
        cartProductRepo.deleteAllCartProducts()
        cartProductAdditionRepository.deleteAll()
    }

    private suspend fun getConsumerCart(cartProductList: List<CartProduct>): ConsumerCartDomain? =
        if (cartProductList.isEmpty()) {
            ConsumerCartDomain.Empty
        } else {
            getCartTotalFlowUseCase(isDelivery = false)
                .firstOrNull()
                ?.let { cartTotal ->
                    ConsumerCartDomain.WithProducts(
                        cartProductList = cartProductList.map(::toLightCartProduct),
                        oldTotalCost = cartTotal.oldFinalCost,
                        newTotalCost = cartTotal.newFinalCost,
                        discount = cartTotal.discount,
                    )
                }
        }

    private fun toLightCartProduct(cartProduct: CartProduct): LightCartProduct =
        LightCartProduct(
            uuid = cartProduct.uuid,
            name = cartProduct.product.name,
            newCost = getNewTotalCost(cartProduct),
            oldCost = getOldTotalCost(cartProduct),
            photoLink = cartProduct.product.photoLink,
            count = cartProduct.count,
            menuProductUuid = cartProduct.product.uuid,
            cartProductAdditionList =
                cartProduct.additionList.sortedBy { cartProductAddition ->
                    cartProductAddition.priority
                },
        )

    private fun getNewTotalCost(cartProduct: CartProduct): Int =
        (
            cartProduct.product.newPrice +
                cartProduct.additionList.sumOf { addition ->
                    addition.price ?: 0
                }
        ) * cartProduct.count

    private fun getOldTotalCost(cartProduct: CartProduct): Int? {
        val oldPrice = cartProduct.product.oldPrice ?: return null

        return (
            oldPrice +
                cartProduct.additionList.sumOf { addition ->
                    addition.price ?: 0
                }
        ) * cartProduct.count
    }
}
