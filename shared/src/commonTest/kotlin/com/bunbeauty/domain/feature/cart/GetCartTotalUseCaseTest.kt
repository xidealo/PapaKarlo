package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getCartProduct
import com.bunbeauty.getMenuProduct
import com.bunbeauty.core.domain.cart.GetDeliveryCostFlowUseCase
import com.bunbeauty.core.domain.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalFlowUseCase
import com.bunbeauty.core.domain.GetNewTotalCostUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetOldTotalCostUseCase
import com.bunbeauty.core.domain.repo.CartProductRepo
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCartTotalUseCaseTest {
    private val cartProductRepo: CartProductRepo = mock()
    private val getDiscountUseCase: GetDiscountUseCase = mock()
    private val getNewTotalCostUseCase: GetNewTotalCostUseCase = mock()
    private val getOldTotalCostUseCase: GetOldTotalCostUseCase = mock()
    private val getDeliveryCostFlowUseCase: GetDeliveryCostFlowUseCase = mock()
    private val getCartTotalFlowUseCase: GetCartTotalFlowUseCase =
        GetCartTotalFlowUseCase(
            cartProductRepo = cartProductRepo,
            getDiscountUseCase = getDiscountUseCase,
            getNewTotalCostUseCase = getNewTotalCostUseCase,
            getOldTotalCostUseCase = getOldTotalCostUseCase,
            getDeliveryCostFlowUseCase = getDeliveryCostFlowUseCase,
        )

    @Test
    fun `should return zero totalCost when product list is empty`() =
        runTest {
            // Given

            everySuspend { cartProductRepo.getCartProductList() } returns listOf()
            everySuspend { getDiscountUseCase() } returns null
            everySuspend { getNewTotalCostUseCase(listOf()) } returns 0
            everySuspend { getOldTotalCostUseCase(listOf()) } returns 0
            everySuspend { getDeliveryCostFlowUseCase(any(), any()) } returns flow { emit(null) }

            // When
            val cartTotal = getCartTotalFlowUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = 0,
                actual = cartTotal.first().newTotalCost,
            )
        }

    @Test
    fun `should return null oldFinalCost when product list is empty`() =
        runTest {
            // Given

            everySuspend { cartProductRepo.getCartProductList() } returns listOf()
            everySuspend { getDiscountUseCase() } returns null
            everySuspend { getNewTotalCostUseCase(listOf()) } returns 0
            everySuspend { getOldTotalCostUseCase(listOf()) } returns 0
            everySuspend { getDeliveryCostFlowUseCase(any(), any()) } returns flow { emit(null) }

            // When
            val cartTotal = getCartTotalFlowUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = null,
                actual = cartTotal.first().oldFinalCost,
            )
        }

    @Test
    fun `should return null oldFinalCost when oldTotal cast is same as newTotalCost`() =
        runTest {
            // Given

            everySuspend { cartProductRepo.getCartProductList() } returns listOf()
            everySuspend { getDiscountUseCase() } returns null
            everySuspend { getNewTotalCostUseCase(listOf()) } returns 0
            everySuspend { getOldTotalCostUseCase(listOf()) } returns 0
            everySuspend { getDeliveryCostFlowUseCase(any(), any()) } returns flow { emit(null) }

            // When
            val cartTotal = getCartTotalFlowUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = null,
                actual = cartTotal.first().oldFinalCost,
            )
        }

    @Test
    fun `should return null discount when no discount`() =
        runTest {
            // Given
            everySuspend { cartProductRepo.getCartProductList() } returns listOf()
            everySuspend { getDiscountUseCase() } returns null
            everySuspend { getNewTotalCostUseCase(listOf()) } returns 0
            everySuspend { getOldTotalCostUseCase(listOf()) } returns 0
            everySuspend { getDeliveryCostFlowUseCase(any(), any()) } returns flow { emit(null) }

            // When
            val cartTotal = getCartTotalFlowUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = null,
                actual = cartTotal.first().discount,
            )
        }

    @Test
    fun `should return oldFinalCost with cost of delivery when delivery is true`() =
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
            everySuspend { cartProductRepo.getCartProductList() } returns cartProductListMockData

            everySuspend { getDiscountUseCase() } returns null
            everySuspend { getNewTotalCostUseCase(cartProductListMockData) } returns 100
            everySuspend { getOldTotalCostUseCase(cartProductListMockData) } returns 200
            everySuspend { getDeliveryCostFlowUseCase(any(), any()) } returns flow { emit(10) }

            // When
            val cartTotal = getCartTotalFlowUseCase(isDelivery = true)

            // Then
            assertEquals(
                expected = 210,
                actual = cartTotal.first().oldFinalCost,
            )
        }

    @Test
    fun `should return newFinalCost with cost of delivery when delivery is true`() =
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
            everySuspend { cartProductRepo.getCartProductList() } returns cartProductListMockData

            everySuspend { getDiscountUseCase() } returns null
            everySuspend { getNewTotalCostUseCase(cartProductListMockData) } returns 100
            everySuspend { getOldTotalCostUseCase(cartProductListMockData) } returns 0
            everySuspend { getDeliveryCostFlowUseCase(any(), any()) } returns flow { emit(10) }

            // When
            val cartTotal = getCartTotalFlowUseCase(isDelivery = true)

            // Then
            assertEquals(
                expected = 110,
                actual = cartTotal.first().newFinalCost,
            )
        }
}
