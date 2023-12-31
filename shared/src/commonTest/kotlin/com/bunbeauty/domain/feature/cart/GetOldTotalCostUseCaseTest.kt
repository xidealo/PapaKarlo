package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getCartProduct
import com.bunbeauty.getCartProductAddition
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.domain.interactor.cart.GetOldTotalCostUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetOldTotalCostUseCaseTest {


    private lateinit var getOldTotalCostUseCase: GetOldTotalCostUseCase

    @BeforeTest
    fun setup() {
        getOldTotalCostUseCase = GetOldTotalCostUseCase()
    }

    @Test
    fun `should return zero newFinalCost when product list is empty`() = runTest {

        // When
        val oldFinalCost = getOldTotalCostUseCase(emptyList())

        // Then
        assertEquals(
            expected = 0,
            actual = oldFinalCost
        )
    }

    @Test
    fun `should return oldFinalCost equals sum of oldPrice and additions when cart product list is not empty`() =
        runTest {
            // Given
            val cartProductListMockData = listOf(
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100),
                    cartProductAdditionList = listOf(getCartProductAddition(price = 100))
                ),
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100),
                    cartProductAdditionList = listOf(
                        getCartProductAddition(price = 75),
                        getCartProductAddition(price = 75)
                    )
                ),
            )
            // When
            val oldFinalCost = getOldTotalCostUseCase(cartProductListMockData)

            // Then
            assertEquals(
                expected = 450,
                actual = oldFinalCost
            )
        }

}