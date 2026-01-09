package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getCartProduct
import com.bunbeauty.shared.domain.exeptions.CartProductLimitReachedException
import com.bunbeauty.shared.domain.exeptions.CartProductNotFoundException
import com.bunbeauty.core.domain.GetCartProductCountUseCase
import com.bunbeauty.core.domain.cart.IncreaseCartProductCountUseCase
import com.bunbeauty.core.domain.repo.CartProductRepo
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class IncreaseCartProductCountUseCaseTest {
    private val getCartProductCountUseCase: GetCartProductCountUseCase = mock(MockMode.autofill)

    private val cartProductRepo: CartProductRepo = mock(MockMode.autofill)

    private val increaseCartProductCountUseCase: IncreaseCartProductCountUseCase =
        IncreaseCartProductCountUseCase(
            getCartProductCountUseCase = getCartProductCountUseCase,
            cartProductRepo = cartProductRepo,
        )

    @Test
    fun `throws CartProductLimitReachedException when cart is full`() =
        runTest {
            // Given
            val cartProductUuid = "cartProductUuid"
            everySuspend {
                getCartProductCountUseCase()
            } returns 99

            // When-Then
            assertFailsWith(CartProductLimitReachedException::class) {
                increaseCartProductCountUseCase(cartProductUuid = cartProductUuid)
            }
            verifySuspend(mode = VerifyMode.atLeast(0)) {
                cartProductRepo.getCartProduct(cartProductUuid)
            }
            verifySuspend(mode = VerifyMode.atLeast(0)) {
                cartProductRepo.updateCartProductCount(
                    cartProductUuid = cartProductUuid,
                    count = any(),
                )
            }
        }

    @Test
    fun `throw CartProductNotFoundException when cart product is not found`() =
        runTest {
            val cartProductUuid = "cartProductUuid"
            everySuspend { cartProductRepo.getCartProduct(cartProductUuid) } returns null

            assertFailsWith(
                exceptionClass = CartProductNotFoundException::class,
                block = {
                    increaseCartProductCountUseCase(cartProductUuid)
                },
            )
        }

    @Test
    fun `update cart product when cart product is found`() =
        runTest {
            val cartProductUuid = "cartProductUuid"
            val cartProduct =
                getCartProduct(
                    uuid = cartProductUuid,
                    count = 2,
                )
            everySuspend {
                cartProductRepo.getCartProduct(cartProductUuid = cartProductUuid)
            } returns cartProduct
            everySuspend {
                cartProductRepo.updateCartProductCount(
                    cartProductUuid = cartProductUuid,
                    count = 3,
                )
            } returns Unit

            increaseCartProductCountUseCase(cartProductUuid = cartProductUuid)

            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.updateCartProductCount(
                    cartProductUuid = cartProductUuid,
                    count = 3,
                )
            }
        }
}
