package com.bunbeauty.domain.feature.cart

import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetNewTotalCostUseCase
import com.bunbeauty.shared.domain.model.Discount
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.product.MenuProduct
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
    fun `should return 0 newFinalCost when product list is empty`() = runTest {
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
    fun `should return 100 newFinalCost when cart product list has 2 products with 50 newPrice`() =
        runTest {
            // Given
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
    fun `should return 90 newFinalCost when discount 10 percent`() =
        runTest {
            // Given
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
    fun `should return 600 newFinalCost and round to bottom when discount 10 percent and new price 666`() =
        runTest {
            // Given

            coEvery { getDiscountUseCase() } returns Discount(firstOrderDiscount = 10)

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockDataWith666NewPrice)

            // Then
            assertEquals(
                expected = 600,
                actual = newFinalCost
            )
        }

    @Test
    fun `should return 0 newFinalCost  when discount 100`() =
        runTest {
            // Given

            coEvery { getDiscountUseCase() } returns Discount(firstOrderDiscount = 100)

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockDataWith666NewPrice)

            // Then
            assertEquals(
                expected = 0,
                actual = newFinalCost
            )
        }

    @Test
    fun `should return same newFinalCost when discount 0`() =
        runTest {
            // Given

            coEvery { getDiscountUseCase() } returns Discount(firstOrderDiscount = 0)

            // When
            val newFinalCost = getNewTotalCostUseCase(cartProductListMockDataWith666NewPrice)

            // Then
            assertEquals(
                expected = 666,
                actual = newFinalCost
            )
        }

    private val cartProductListMockData = listOf(
        CartProduct(
            uuid = "1",
            count = 1,
            product = MenuProduct(
                uuid = "1",
                name = "Kapusta",
                newPrice = 50,
                oldPrice = 100,
                utils = "г",
                nutrition = 1,
                description = "",
                comboDescription = "",
                photoLink = "",
                categoryList = emptyList(),
                visible = true,
            ),
        ),
        CartProduct(
            uuid = "2",
            count = 1,
            product = MenuProduct(
                uuid = "2",
                name = "Kartoxa",
                newPrice = 50,
                oldPrice = 100,
                utils = "г",
                nutrition = 1,
                description = "",
                comboDescription = "",
                photoLink = "",
                categoryList = emptyList(),
                visible = true,
            ),
        ),
    )

    private val cartProductListMockDataWith666NewPrice = listOf(
        CartProduct(
            uuid = "1",
            count = 1,
            product = MenuProduct(
                uuid = "1",
                name = "Kapusta",
                newPrice = 666,
                oldPrice = 1000,
                utils = "г",
                nutrition = 1,
                description = "",
                comboDescription = "",
                photoLink = "",
                categoryList = emptyList(),
                visible = true,
            ),
        ),
    )
}