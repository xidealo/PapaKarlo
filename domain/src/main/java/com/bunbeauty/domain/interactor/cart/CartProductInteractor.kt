package com.bunbeauty.domain.interactor.cart

import com.bunbeauty.domain.interactor.product.IProductInteractor
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.CartTotal
import com.bunbeauty.shared.domain.model.cart.ConsumerCart
import com.bunbeauty.shared.domain.model.cart.LightCartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.DataStoreRepo
import com.bunbeauty.shared.domain.repo.DeliveryRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class CartProductInteractor(
    private val cartProductRepo: CartProductRepo,
    private val deliveryRepo: DeliveryRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val productInteractor: IProductInteractor,
) : ICartProductInteractor {

    override suspend fun getConsumerCart(): ConsumerCart? {
        return getConsumerCart(cartProductRepo.getCartProductList())
    }

    override fun observeConsumerCart(): Flow<ConsumerCart?> {
        return  cartProductRepo.observeCartProductList().map { cartProductList ->
            getConsumerCart(cartProductList)
        }
    }

    override fun observeTotalCartCount(): Flow<Int> {
        return cartProductRepo.observeCartProductList().map { cartProductList ->
            getTotalCount(cartProductList)
        }
    }

    override fun observeNewTotalCartCost(): Flow<Int> {
        return cartProductRepo.observeCartProductList().map { cartProductList ->
            productInteractor.getNewTotalCost(cartProductList)
        }
    }

    override fun observeDeliveryCost(): Flow<Int> {
        return cartProductRepo.observeCartProductList().map { cartProductList ->
            productInteractor.getDeliveryCost(cartProductList)
        }
    }

    override fun observeCartTotal(isDeliveryFlow: Flow<Boolean>): Flow<CartTotal> {
        return cartProductRepo.observeCartProductList().flatMapLatest { cartProductList ->
            val newTotalCost = productInteractor.getNewTotalCost(cartProductList)
            observeDeliveryCost().flatMapLatest { deliveryCost ->
                isDeliveryFlow.map { isDelivery ->
                    val amountToPay = if (isDelivery) {
                        newTotalCost + deliveryCost
                    } else {
                        newTotalCost
                    }
                    CartTotal(
                        totalCost = newTotalCost,
                        deliveryCost = productInteractor.getDeliveryCost(cartProductList),
                        amountToPay = amountToPay
                    )
                }
            }
        }
    }

    override suspend fun addProductToCart(menuProductUuid: String): CartProduct? {
        if (getTotalCartCount() >= CART_PRODUCT_LIMIT) {
            return null
        }

        val cartProduct = cartProductRepo.getCartProductByMenuProductUuid(menuProductUuid)
        return if (cartProduct == null) {
            cartProductRepo.saveAsCartProduct(menuProductUuid)
        } else {
            cartProductRepo.updateCartProductCount(cartProduct.uuid, cartProduct.count + 1)
        }
    }

    override suspend fun removeProductFromCart(menuProductUuid: String) {
        val cartProduct = cartProductRepo.getCartProductByMenuProductUuid(menuProductUuid) ?: return
        if (cartProduct.count > 1) {
            cartProductRepo.updateCartProductCount(cartProduct.uuid, cartProduct.count - 1)
        } else {
            cartProductRepo.deleteCartProduct(cartProduct)
        }
    }

    override suspend fun removeAllProductsFromCart() {
        cartProductRepo.deleteAllCartProducts()
    }

    suspend fun getConsumerCart(cartProductList: List<CartProduct>): ConsumerCart? {
        return if (cartProductList.isEmpty()) {
            ConsumerCart.Empty
        } else {
            deliveryRepo.getDelivery()?.let { delivery ->
                ConsumerCart.WithProducts(
                    forFreeDelivery = delivery.forFree,
                    cartProductList = cartProductList.map(::toLightCartProduct),
                    oldTotalCost = productInteractor.getOldTotalCost(cartProductList),
                    newTotalCost = productInteractor.getNewTotalCost(cartProductList)
                )
            }
        }
    }

    fun toLightCartProduct(cartProduct: CartProduct): LightCartProduct {
        return LightCartProduct(
            uuid = cartProduct.uuid,
            name = cartProduct.product.name,
            newCost = productInteractor.getProductPositionNewCost(cartProduct),
            oldCost = productInteractor.getProductPositionOldCost(cartProduct),
            photoLink = cartProduct.product.photoLink,
            count = cartProduct.count,
            menuProductUuid = cartProduct.product.uuid,
        )
    }

    suspend fun getTotalCartCount(): Int {
        val cartProductList = cartProductRepo.getCartProductList()
        return getTotalCount(cartProductList)
    }

    fun getTotalCount(productList: List<CartProduct>): Int {
        return productList.sumOf { product ->
            product.count
        }
    }

    companion object {
        private const val CART_PRODUCT_LIMIT = 99
    }
}