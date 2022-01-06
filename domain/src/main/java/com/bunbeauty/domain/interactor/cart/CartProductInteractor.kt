package com.bunbeauty.domain.interactor.cart

import com.bunbeauty.domain.interactor.product.IProductInteractor
import com.bunbeauty.domain.model.product.CartProduct
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CartProductRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartProductInteractor @Inject constructor(
    @Api private val cartProductRepo: CartProductRepo,
    private val productInteractor: IProductInteractor,
) : ICartProductInteractor {

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

    override fun observeAmountToPay(): Flow<Int> {
        return observeNewTotalCartCost().flatMapLatest { newTotalCost ->
            observeDeliveryCost().map { deliveryCost ->
                newTotalCost + deliveryCost
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