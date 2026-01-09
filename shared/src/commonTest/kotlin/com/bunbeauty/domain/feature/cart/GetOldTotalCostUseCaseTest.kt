package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getCartProduct
import com.bunbeauty.getCartProductAddition
import com.bunbeauty.getMenuProduct
import com.bunbeauty.core.domain.GetCartProductAdditionsPriceUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetOldTotalCostUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetOldTotalCostUseCaseImpl
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetOldTotalCostUseCaseTest {
    private val getCartProductAdditionsPriceUseCase: GetCartProductAdditionsPriceUseCase = mock()
    private val getOldTotalCostUseCase: GetOldTotalCostUseCase =
        GetOldTotalCostUseCaseImpl(
            getCartProductAdditionsPriceUseCase = getCartProductAdditionsPriceUseCase,
        )

    @Test
    fun `should return zero newFinalCost when product list is empty`() =
        runTest {
            // When
            val oldFinalCost = getOldTotalCostUseCase(emptyList())
            everySuspend { getCartProductAdditionsPriceUseCase(any()) } returns 0

            // Then
            assertEquals(
                expected = 0,
                actual = oldFinalCost,
            )
        }

    @Test
    fun `should return oldFinalCost equals sum of oldPrice and additions when cart product list is not empty`() =
        runTest {
            // Given
            val cartProductListMockData =
                listOf(
                    getCartProduct(
                        count = 1,
                        menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100),
                        cartProductAdditionList = listOf(getCartProductAddition(price = 100)),
                    ),
                    getCartProduct(
                        count = 1,
                        menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100),
                        cartProductAdditionList =
                            listOf(
                                getCartProductAddition(price = 75),
                                getCartProductAddition(price = 75),
                            ),
                    ),
                )
            everySuspend { getCartProductAdditionsPriceUseCase(listOf(getCartProductAddition(price = 100))) } returns 100

            everySuspend {
                getCartProductAdditionsPriceUseCase(
                    listOf(
                        getCartProductAddition(price = 75),
                        getCartProductAddition(price = 75),
                    ),
                )
            } returns 150

            // When
            val oldFinalCost = getOldTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 450,
                actual = oldFinalCost,
            )
        }
}
