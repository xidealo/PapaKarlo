package com.bunbeauty.domain.feature.cart

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class AddCartProductUseCaseTest {

    private val cartProductRepo: CartProductRepo = mockk()
    private val menuProductUuid = "menu_product_uuid"
    private lateinit var addCartProduct: AddCartProductUseCase

    @BeforeTest
    fun setup() {
        addCartProduct = AddCartProductUseCase(cartProductRepo)
    }

    @Test
    fun `return false when cart is full`() = runTest {
        // Given
        coEvery { cartProductRepo.getCartProductList() } returns generateCartProducts(
            CART_PRODUCT_LIMIT
        )

        // When
        val result = addCartProduct(menuProductUuid, emptyList())

        // Then
        assertFalse(result)
    }

    @Test
    fun `save new cart product when product is not in cart`() = runTest {
        // Given
        coEvery { cartProductRepo.getCartProductList() } returns emptyList()
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns null
        coEvery { cartProductRepo.saveAsCartProduct(menuProductUuid) } returns generateCartProduct()

        // When
        val result = addCartProduct(menuProductUuid, emptyList())

        // Then
        assertTrue(result)
    }

    @Test
    fun `update cart product count when product is in cart`() = runTest {
        // Given
        val initialCount = 2
        val initialCartProduct = generateCartProduct(count = initialCount)
        coEvery { cartProductRepo.getCartProductList() } returns listOf(initialCartProduct)
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns initialCartProduct
        coEvery {
            cartProductRepo.updateCartProductCount(
                initialCartProduct.uuid,
                initialCount + 1
            )
        } returns initialCartProduct.copy(count = initialCount + 1)

        // When
        val result = addCartProduct(menuProductUuid, emptyList())

        // Then
        assertTrue(result)
    }

    companion object {

        private fun generateCartProducts(count: Int): List<CartProduct> {
            return List(count) {
                generateCartProduct(uuid = "cart_uuid_$it")
            }
        }

        private fun generateCartProduct(uuid: String = "cart_uuid", count: Int = 1): CartProduct {
            return CartProduct(uuid, count, mockk())
        }
    }
}
