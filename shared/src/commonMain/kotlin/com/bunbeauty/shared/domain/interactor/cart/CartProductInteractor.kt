package com.bunbeauty.shared.domain.interactor.cart

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.ConsumerCartDomain
import com.bunbeauty.shared.domain.model.cart.LightCartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.DeliveryRepo
import kotlinx.coroutines.flow.map

class CartProductInteractor(
    private val cartProductRepo: CartProductRepo,
    private val deliveryRepo: DeliveryRepo,
    private val getCartTotal: GetCartTotalUseCase,
) : ICartProductInteractor {

    override fun observeConsumerCart(): CommonFlow<ConsumerCartDomain?> {
        return cartProductRepo.observeCartProductList().map { cartProductList ->
            getConsumerCart(cartProductList)
        }.asCommonFlow()
    }

    override suspend fun getCartProductList(): List<CartProduct> {
        return cartProductRepo.getCartProductList()
    }

    override fun observeTotalCartCount(): CommonFlow<Int> {
        return cartProductRepo.observeCartProductList().map { cartProductList ->
            getTotalCount(cartProductList)
        }.asCommonFlow()
    }

    override suspend fun removeAllProductsFromCart() {
        cartProductRepo.deleteAllCartProducts()
    }

    private suspend fun getConsumerCart(cartProductList: List<CartProduct>): ConsumerCartDomain? {
        return if (cartProductList.isEmpty()) {
            ConsumerCartDomain.Empty
        } else {
            deliveryRepo.getDelivery()?.let { delivery ->
                val cartTotal = getCartTotal(isDelivery = false)
                ConsumerCartDomain.WithProducts(
                    forFreeDelivery = delivery.forFree,
                    cartProductList = cartProductList.map(::toLightCartProduct),
                    oldTotalCost = cartTotal.oldFinalCost,
                    newTotalCost = cartTotal.newFinalCost,
                    discount = cartTotal.discount
                )
            }
        }
    }

    fun toLightCartProduct(cartProduct: CartProduct): LightCartProduct {
        return LightCartProduct(
            uuid = cartProduct.uuid,
            name = cartProduct.product.name,
            newCost = getNewTotalCost(cartProduct),
            oldCost = getOldTotalCost(cartProduct),
            photoLink = cartProduct.product.photoLink,
            count = cartProduct.count,
            menuProductUuid = cartProduct.product.uuid,
            cartProductAdditionList = cartProduct.cartProductAdditionList
        )
    }

    fun getNewTotalCost(cartProduct: CartProduct): Int {
        return (cartProduct.product.newPrice + cartProduct.cartProductAdditionList.sumOf { addition ->
            addition.price ?: 0
        }) * cartProduct.count
    }

    fun getOldTotalCost(cartProduct: CartProduct): Int? {
        val oldPrice = cartProduct.product.oldPrice ?: return null

        return (oldPrice + cartProduct.cartProductAdditionList.sumOf { addition ->
            addition.price ?: 0
        }) * cartProduct.count
    }

    fun getTotalCount(productList: List<CartProduct>): Int {
        return productList.sumOf { product ->
            product.count
        }
    }
}