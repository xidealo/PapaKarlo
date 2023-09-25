package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getCartProduct
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetNewTotalCostUseCase
import com.bunbeauty.shared.domain.model.Delivery
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.DeliveryRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCartTotalUseCaseTest {

    private val cartProductRepo: CartProductRepo = mockk()
    private val deliveryRepo: DeliveryRepo = mockk()
    private val getDiscountUseCase: GetDiscountUseCase = mockk()
    private val getNewTotalCostUseCase: GetNewTotalCostUseCase = mockk()
    private lateinit var getCartTotalUseCase: GetCartTotalUseCase

    @BeforeTest
    fun setup() {
        getCartTotalUseCase = GetCartTotalUseCase(
            cartProductRepo = cartProductRepo,
            deliveryRepo = deliveryRepo,
            getDiscountUseCase = getDiscountUseCase,
            getNewTotalCostUseCase = getNewTotalCostUseCase
        )
    }

    @Test
    fun `should return zero totalCost when product list is empty`() = runTest {
        // Given

        coEvery { cartProductRepo.getCartProductList() } returns listOf()
        coEvery { getDiscountUseCase() } returns null
        coEvery { deliveryRepo.getDelivery() } returns Delivery(
            cost = 10,
            forFree = 100
        )
        coEvery { getNewTotalCostUseCase(listOf()) } returns 0

        // When
        val cartTotal = getCartTotalUseCase(isDelivery = false)

        // Then
        assertEquals(
            expected = 0,
            actual = cartTotal.totalCost
        )
    }

    @Test
    fun `should return null oldFinalCost when product list is empty`() = runTest {
        // Given

        coEvery { cartProductRepo.getCartProductList() } returns listOf()
        coEvery { getDiscountUseCase() } returns null
        coEvery { deliveryRepo.getDelivery() } returns Delivery(
            cost = 10,
            forFree = 100
        )
        coEvery { getNewTotalCostUseCase(listOf()) } returns 0

        // When
        val cartTotal = getCartTotalUseCase(isDelivery = false)

        // Then
        assertEquals(
            expected = null,
            actual = cartTotal.oldFinalCost
        )
    }

    @Test
    fun `should return null discount when no discount`() = runTest {
        // Given

        coEvery { cartProductRepo.getCartProductList() } returns listOf()
        coEvery { getDiscountUseCase() } returns null
        coEvery { deliveryRepo.getDelivery() } returns Delivery(
            cost = 10,
            forFree = 100
        )
        coEvery { getNewTotalCostUseCase(listOf()) } returns 0

        // When
        val cartTotal = getCartTotalUseCase(isDelivery = false)

        // Then
        assertEquals(
            expected = null,
            actual = cartTotal.discount
        )
    }

    @Test
    fun `should return totalCost equals sum of newPrice from productList`() =
        runTest {
            // Given
            val cartProductListMockData = listOf(
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100)
                ),
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100)
                ),
            )

            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns null
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 100
            )
            coEvery { getNewTotalCostUseCase(cartProductListMockData) } returns 100

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = 100,
                actual = cartTotal.totalCost
            )
        }

    @Test
    fun `should return oldFinalCost equals sum of oldPrice from cart product list when it is not equal to newPrice`() =
        runTest {
            // Given
            val cartProductListMockData = listOf(
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100)
                ),
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100)
                ),
            )
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns null
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 100
            )
            coEvery { getNewTotalCostUseCase(cartProductListMockData) } returns 100

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = 200,
                actual = cartTotal.oldFinalCost
            )
        }

    //delivery

    @Test
    fun `should return oldFinalCost with cost of delivery when delivery is true`() =
        runTest {
            // Given
            val cartProductListMockData = listOf(
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100)
                ),
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100)
                ),
            )
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns null
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 100000
            )
            coEvery { getNewTotalCostUseCase(cartProductListMockData) } returns 100

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = true)

            // Then
            assertEquals(
                expected = 210,
                actual = cartTotal.oldFinalCost
            )
        }

    @Test
    fun `should return newFinalCost with cost of delivery when delivery is true`() =
        runTest {
            // Given
            val cartProductListMockData = listOf(
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100)
                ),
                getCartProduct(
                    count = 1,
                    menuProduct = getMenuProduct(newPrice = 50, oldPrice = 100)
                ),
            )
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns null
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 100000
            )
            coEvery { getNewTotalCostUseCase(cartProductListMockData) } returns 100

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = true)

            // Then
            assertEquals(
                expected = 110,
                actual = cartTotal.newFinalCost
            )
        }

}