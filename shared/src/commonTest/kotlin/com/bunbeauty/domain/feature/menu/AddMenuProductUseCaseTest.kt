package com.bunbeauty.domain.feature.menu

import com.bunbeauty.getCartProduct
import com.bunbeauty.core.domain.GetCartProductCountUseCase
import com.bunbeauty.core.domain.exeptions.CartProductLimitReachedException
import com.bunbeauty.core.domain.menu_product.AddMenuProductUseCase
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

class AddMenuProductUseCaseTest {
    private val getCartProductCountUseCase: GetCartProductCountUseCase = mock(MockMode.autofill)

    private val cartProductRepo: CartProductRepo = mock(MockMode.autofill)

    private val addMenuProductUseCase: AddMenuProductUseCase =
        AddMenuProductUseCase(
            getCartProductCountUseCase = getCartProductCountUseCase,
            cartProductRepo = cartProductRepo,
        )

    @Test
    fun `throws CartProductLimitReachedException when cart is full`() =
        runTest {
            // Given
            val menuProductUuid = "menuProductUuid"
            everySuspend {
                getCartProductCountUseCase()
            } returns 99

            // When-Then
            assertFailsWith(CartProductLimitReachedException::class) {
                addMenuProductUseCase(menuProductUuid = menuProductUuid)
            }
            verifySuspend(mode = VerifyMode.atLeast(0)) {
                cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid = menuProductUuid)
            }
            verifySuspend(mode = VerifyMode.atLeast(0)) {
                cartProductRepo.saveAsCartProduct(menuProductUuid = menuProductUuid)
            }
            verifySuspend(mode = VerifyMode.atLeast(0)) {
                cartProductRepo.updateCartProductCount(
                    cartProductUuid = menuProductUuid,
                    count = any(),
                )
            }
        }

    @Test
    fun `save as new cart product when there is no cart product with the same uuid`() =
        runTest {
            val menuProductUuid = "menuProductUuid"
            val cartProductUuid = "cartProductUuid"
            everySuspend { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns emptyList()
            everySuspend { cartProductRepo.saveAsCartProduct(menuProductUuid) } returns cartProductUuid

            addMenuProductUseCase(menuProductUuid)

            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.saveAsCartProduct(menuProductUuid)
            }
        }

    @Test
    fun `update existed cart product when there is cart product with the same uuid`() =
        runTest {
            val menuProductUuid = "menuProductUuid"
            val cartProductUuid = "cartProductUuid"
            val cartProductList =
                listOf(
                    getCartProduct(uuid = cartProductUuid, count = 2),
                )
            everySuspend {
                cartProductRepo.getCartProductListByMenuProductUuid(
                    menuProductUuid = menuProductUuid,
                )
            } returns cartProductList
            everySuspend {
                cartProductRepo.updateCartProductCount(
                    cartProductUuid = cartProductUuid,
                    count = 3,
                )
            } returns Unit

            addMenuProductUseCase(menuProductUuid)

            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.updateCartProductCount(
                    cartProductUuid = cartProductUuid,
                    count = 3,
                )
            }
        }
}
