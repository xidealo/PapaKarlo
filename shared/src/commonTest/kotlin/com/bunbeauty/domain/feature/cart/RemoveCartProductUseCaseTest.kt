package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getCartProduct
import com.bunbeauty.getCartProductAddition
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.domain.feature.cart.RemoveCartProductUseCase
import com.bunbeauty.shared.domain.repo.CartProductAdditionRepo
import com.bunbeauty.core.domain.repo.CartProductRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RemoveCartProductUseCaseTest {
    private val cartProductRepo: CartProductRepo = mock()
    private val cartProductAdditionRepository: CartProductAdditionRepo = mock()
    private val cartProductUuid = "cart_uuid"
    private val removeCartProduct: RemoveCartProductUseCase =
        RemoveCartProductUseCase(
            cartProductRepo = cartProductRepo,
            cartProductAdditionRepository = cartProductAdditionRepository,
        )

    @Test
    fun `return false when there is no product in cart`() =
        runTest {
            // Given
            everySuspend { cartProductRepo.getCartProduct(cartProductUuid) } returns null
            everySuspend { cartProductAdditionRepository.delete(cartProductUuid) } returns Unit

            // When
            val result = removeCartProduct(cartProductUuid)

            // Then
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.getCartProduct(cartProductUuid)
            }
            assertFalse(result)
        }

    @Test
    fun `return true when product count more than 1`() =
        runTest {
            // Given
            val initialCartProduct =
                getCartProduct(
                    uuid = cartProductUuid,
                    count = 2,
                    menuProduct = getMenuProduct(),
                )
            everySuspend { cartProductRepo.getCartProduct(cartProductUuid) } returns initialCartProduct

            everySuspend {
                cartProductRepo.updateCartProductCount(
                    cartProductUuid,
                    1,
                )
            } returns Unit
            everySuspend { cartProductAdditionRepository.delete(cartProductUuid) } returns Unit

            // When
            val result = removeCartProduct(cartProductUuid)

            // Then
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.getCartProduct(cartProductUuid)
            }
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.updateCartProductCount(cartProductUuid, 1)
            }
            assertTrue(result)
        }

    @Test
    fun `return true when product count equals 1`() =
        runTest {
            // Given
            val initialCartProductAddition = getCartProductAddition(uuid = "1")
            val cartProduct =
                getCartProduct(
                    uuid = cartProductUuid,
                    count = 1,
                    menuProduct = getMenuProduct(),
                    cartProductAdditionList = listOf(initialCartProductAddition),
                )
            everySuspend { cartProductRepo.getCartProduct(cartProductUuid) } returns cartProduct

            everySuspend { cartProductRepo.deleteCartProduct(cartProductUuid) } returns Unit
            everySuspend { cartProductAdditionRepository.delete(initialCartProductAddition.uuid) } returns Unit

            // When
            val result = removeCartProduct(cartProductUuid)

            // Then
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.getCartProduct(cartProductUuid)
            }
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.deleteCartProduct(cartProductUuid)
            }
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductAdditionRepository.delete(initialCartProductAddition.uuid)
            }
            assertTrue(result)
        }
}
