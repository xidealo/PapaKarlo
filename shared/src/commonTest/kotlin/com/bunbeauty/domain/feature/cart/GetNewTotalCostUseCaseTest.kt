package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getCartProduct
import com.bunbeauty.getCartProductAddition
import com.bunbeauty.getMenuProduct
import com.bunbeauty.core.domain.GetCartProductAdditionsPriceUseCase
import com.bunbeauty.core.domain.discount.GetDiscountUseCase
import com.bunbeauty.core.domain.GetNewTotalCostUseCase
import com.bunbeauty.core.domain.GetNewTotalCostUseCaseImpl
import com.bunbeauty.core.model.Discount
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetNewTotalCostUseCaseTest {
    private val getDiscountUseCase: GetDiscountUseCase = mock()
    private val getCartProductAdditionsPriceUseCase: GetCartProductAdditionsPriceUseCase = mock()

    private val getNewTotalCostUseCase: GetNewTotalCostUseCase =
        GetNewTotalCostUseCaseImpl(
            getDiscountUseCase = getDiscountUseCase,
            getCartProductAdditionsPriceUseCase = getCartProductAdditionsPriceUseCase,
        )

    @Test
    fun `should return zero newFinalCost when product list is empty`() =
        runTest {
            // Given

            everySuspend { getDiscountUseCase() } returns null

            // When
            val newFinalCost = getNewTotalCostUseCase(emptyList())

            // Then
            assertEquals(
                expected = 0,
                actual = newFinalCost,
            )
        }

    @Test
    fun `should return newFinalCost equals sum of newPrice from cartProductList`() =
        runTest {
            // Given
            val cartProductListMockData =
                listOf(
                    getCartProduct(
                        count = 1,
                        menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100),
                    ),
                    getCartProduct(
                        count = 1,
                        menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100),
                    ),
                )
            everySuspend { getDiscountUseCase() } returns null
            everySuspend { getCartProductAdditionsPriceUseCase(any()) } returns 0

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 100,
                actual = newFinalCost,
            )
        }

    @Test
    fun `should return newFinalCost equals sum of newPrice minus discount`() =
        runTest {
            // Given
            val cartProductListMockData =
                listOf(
                    getCartProduct(
                        count = 1,
                        menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100),
                    ),
                    getCartProduct(
                        count = 1,
                        menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100),
                    ),
                )
            everySuspend { getDiscountUseCase() } returns Discount(firstOrderDiscount = 10)
            everySuspend { getCartProductAdditionsPriceUseCase(any()) } returns 0

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 90,
                actual = newFinalCost,
            )
        }

    @Test
    fun `should return newFinalCost rounded to bottom`() =
        runTest {
            // Given
            val cartProductListMockData =
                listOf(
                    getCartProduct(
                        count = 1,
                        menuProduct = getMenuProduct(newPrice = 666, oldPrice = 1000),
                    ),
                )
            everySuspend { getDiscountUseCase() } returns Discount(firstOrderDiscount = 10)
            everySuspend { getCartProductAdditionsPriceUseCase(any()) } returns 0

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 600,
                actual = newFinalCost,
            )
        }

    @Test
    fun `should return zero newFinalCost when discount 100 percent`() =
        runTest {
            // Given
            val cartProductListMockData =
                listOf(
                    getCartProduct(
                        count = 1,
                        menuProduct = getMenuProduct(newPrice = 666, oldPrice = 1000),
                    ),
                )
            everySuspend { getDiscountUseCase() } returns Discount(firstOrderDiscount = 100)
            everySuspend { getCartProductAdditionsPriceUseCase(any()) } returns 0

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 0,
                actual = newFinalCost,
            )
        }

    @Test
    fun `should return same newFinalCost when discount zero`() =
        runTest {
            // Given
            val cartProductListMockData =
                listOf(
                    getCartProduct(
                        count = 1,
                        menuProduct = getMenuProduct(newPrice = 666, oldPrice = 1000),
                    ),
                )
            everySuspend { getDiscountUseCase() } returns Discount(firstOrderDiscount = 0)
            everySuspend { getCartProductAdditionsPriceUseCase(any()) } returns 0

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 666,
                actual = newFinalCost,
            )
        }

    @Test
    fun `should return newFinalCost with additions price when has additions`() =
        runTest {
            // Given
            val cartProductListMockData =
                listOf(
                    getCartProduct(
                        count = 1,
                        menuProduct = getMenuProduct(newPrice = 666, oldPrice = 1000),
                        cartProductAdditionList =
                            listOf(
                                getCartProductAddition(price = 50),
                                getCartProductAddition(price = 50),
                            ),
                    ),
                )
            everySuspend { getDiscountUseCase() } returns Discount(firstOrderDiscount = 0)
            everySuspend { getCartProductAdditionsPriceUseCase(any()) } returns 100

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 766,
                actual = newFinalCost,
            )
        }
}
