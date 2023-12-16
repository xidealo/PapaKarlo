package com.bunbeauty.domain.feature.cart

import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.repo.CartProductRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class RemoveCartProductUseCaseTest {

    private val cartProductRepo: CartProductRepo = mockk()
    private val menuProductUuid = "menu_product_uuid"
    private lateinit var removeCartProduct: RemoveCartProductUseCase

    @BeforeTest
    fun setup() {
        removeCartProduct = RemoveCartProductUseCase(cartProductRepo)
    }

    @Test
    fun `return false when there is no product in cart`() = runTest {
        // Given
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns null

        // When
        val result = removeCartProduct(menuProductUuid)

        // Then
        coVerify(exactly = 1) {
            cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid)
        }
        assertFalse(result)
    }

    @Test
    fun `return true when product count more than 1`() = runTest {
        // Given
        val initialCartProduct = generateCartProduct(uuid = menuProductUuid, count = 2)
        val resultCartProduct = initialCartProduct.copy(count = 1)
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns initialCartProduct
        coEvery { cartProductRepo.updateCartProductCount(menuProductUuid, 1) } returns resultCartProduct

        // When
        val result = removeCartProduct(menuProductUuid)

        // Then
        coVerify(exactly = 1) {
            cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid)
        }
        coVerify(exactly = 1) {
            cartProductRepo.updateCartProductCount(menuProductUuid, 1)
        }
        assertTrue(result)
    }

    @Test
    fun `return true when product count equals 1`() = runTest {
        // Given
        val cartProduct = generateCartProduct(uuid = menuProductUuid, count = 1)
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns cartProduct
        coEvery { cartProductRepo.deleteCartProduct(menuProductUuid) } returns Unit

        // When
        val result = removeCartProduct(menuProductUuid)

        // Then
        coVerify(exactly = 1) {
            cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid)
        }
        coVerify(exactly = 1) {
            cartProductRepo.deleteCartProduct(menuProductUuid)
        }
        assertTrue(result)
    }

    companion object {
        private fun generateCartProduct(uuid: String = "cart_uuid", count: Int = 1): CartProduct {
            return CartProduct(uuid, count, mockk())
        }
    }
}
