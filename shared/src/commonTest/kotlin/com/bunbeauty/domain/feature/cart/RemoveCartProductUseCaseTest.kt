package com.bunbeauty.domain.feature.cart

import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.CartProductAddition
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
    private val cartProductAdditionRepository: CartProductAdditionRepository = mockk()
    private val cartProductUuid = "cart_uuid"
    private lateinit var removeCartProduct: RemoveCartProductUseCase

    @BeforeTest
    fun setup() {
        removeCartProduct = RemoveCartProductUseCase(
            cartProductRepo = cartProductRepo,
            cartProductAdditionRepository = cartProductAdditionRepository
        )
    }

    @Test
    fun `return false when there is no product in cart`() = runTest {
        // Given
        coEvery { cartProductRepo.getCartProduct(cartProductUuid) } returns null
        coEvery { cartProductAdditionRepository.delete(cartProductUuid) } returns Unit

        // When
        val result = removeCartProduct(cartProductUuid)

        // Then
        coVerify(exactly = 1) {
            cartProductRepo.getCartProduct(cartProductUuid)
        }
        assertFalse(result)
    }

    @Test
    fun `return true when product count more than 1`() = runTest {
        // Given
        val initialCartProduct = generateCartProduct(uuid = cartProductUuid, count = 2)
        val resultCartProduct = initialCartProduct.copy(count = 1)
        coEvery { cartProductRepo.getCartProduct(cartProductUuid) } returns initialCartProduct

        coEvery {
            cartProductRepo.updateCartProductCount(
                cartProductUuid,
                1
            )
        } returns true
        coEvery { cartProductAdditionRepository.delete(cartProductUuid) } returns Unit

        // When
        val result = removeCartProduct(cartProductUuid)

        // Then
        coVerify(exactly = 1) {
            cartProductRepo.getCartProduct(cartProductUuid)
        }
        coVerify(exactly = 1) {
            cartProductRepo.updateCartProductCount(cartProductUuid, 1)
        }
        assertTrue(result)
    }

    @Test
    fun `return true when product count equals 1`() = runTest {
        // Given
        val cartProduct = generateCartProduct(uuid = cartProductUuid, count = 1)
        coEvery { cartProductRepo.getCartProduct(cartProductUuid) } returns cartProduct

        coEvery { cartProductRepo.deleteCartProduct(cartProductUuid) } returns Unit
        coEvery { cartProductAdditionRepository.delete(cartProductUuid) } returns Unit

        // When
        val result = removeCartProduct(cartProductUuid)

        // Then
        coVerify(exactly = 1) {
            cartProductRepo.getCartProduct(cartProductUuid)
        }
        coVerify(exactly = 1) {
            cartProductRepo.deleteCartProduct(cartProductUuid)
        }
        assertTrue(result)
    }

    companion object {
        private fun generateCartProduct(
            uuid: String = "cart_uuid",
            count: Int = 1,
            cartProductAdditionList: List<CartProductAddition> = emptyList(),
        ): CartProduct {
            return CartProduct(uuid, count, mockk(), cartProductAdditionList)
        }
    }
}
