package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getCartProduct
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetNewTotalCostUseCase
import com.bunbeauty.shared.domain.model.Discount
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetNewTotalCostUseCaseTest {

    private val getDiscountUseCase: GetDiscountUseCase = mockk()

    private lateinit var getNewTotalCostUseCase: GetNewTotalCostUseCase

    @BeforeTest
    fun setup() {
        getNewTotalCostUseCase = GetNewTotalCostUseCase(
            getDiscountUseCase = getDiscountUseCase,
        )
    }

    @Test
    fun `should return zero newFinalCost when product list is empty`() = runTest {
        // Given

        coEvery { getDiscountUseCase() } returns null

        // When
        val newFinalCost = getNewTotalCostUseCase(emptyList())

        // Then
        assertEquals(
            expected = 0,
            actual = newFinalCost
        )
    }

    @Test
    fun `should return newFinalCost equals sum of newPrice from cartProductList`() =
        runTest {
            // Given
            val cartProductListMockData = listOf(
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100)
                )
            )
            coEvery { getDiscountUseCase() } returns null

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 100,
                actual = newFinalCost
            )
        }

    @Test
    fun `should return newFinalCost equals sum of newPrice minus discount`() =
        runTest {
            // Given
            val cartProductListMockData = listOf(
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100)
                )
            )
            coEvery { getDiscountUseCase() } returns Discount(firstOrderDiscount = 10)

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 90,
                actual = newFinalCost
            )
        }

    @Test
    fun `should return newFinalCost rounded to bottom`() =
        runTest {
            // Given
            val cartProductListMockData = listOf(
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 666, oldPrice = 1000)
                )
            )
            coEvery { getDiscountUseCase() } returns Discount(firstOrderDiscount = 10)

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 600,
                actual = newFinalCost
            )
        }

    @Test
    fun `should return zero newFinalCost when discount 100 percent`() =
        runTest {
            // Given
            val cartProductListMockData = listOf(
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 666, oldPrice = 1000)
                )
            )
            coEvery { getDiscountUseCase() } returns Discount(firstOrderDiscount = 100)

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 0,
                actual = newFinalCost
            )
        }

    @Test
    fun `should return same newFinalCost when discount zero`() =
        runTest {
            // Given
            val cartProductListMockData = listOf(
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 666, oldPrice = 1000)
                )
            )
            coEvery { getDiscountUseCase() } returns Discount(firstOrderDiscount = 0)

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 666,
                actual = newFinalCost
            )
        }

}