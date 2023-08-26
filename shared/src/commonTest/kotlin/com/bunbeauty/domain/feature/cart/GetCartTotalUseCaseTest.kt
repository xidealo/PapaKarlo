package com.bunbeauty.domain.feature.cart

import com.bunbeauty.shared.domain.feature.discount.GetDiscountUseCase
import com.bunbeauty.shared.domain.interactor.cart.GetCartTotalUseCase
import com.bunbeauty.shared.domain.model.Delivery
import com.bunbeauty.shared.domain.model.Discount
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.product.MenuProduct
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
    private lateinit var getCartTotalUseCase: GetCartTotalUseCase

    @BeforeTest
    fun setup() {
        getCartTotalUseCase = GetCartTotalUseCase(
            cartProductRepo = cartProductRepo,
            deliveryRepo = deliveryRepo,
            getDiscountUseCase = getDiscountUseCase,
        )
    }

    @Test
    fun `should return 0 totalCost when product list is empty`() = runTest {
        // Given

        coEvery { cartProductRepo.getCartProductList() } returns listOf()
        coEvery { getDiscountUseCase() } returns null
        coEvery { deliveryRepo.getDelivery() } returns Delivery(
            cost = 10,
            forFree = 100
        )

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

        // When
        val cartTotal = getCartTotalUseCase(isDelivery = false)

        // Then
        assertEquals(
            expected = 0,
            actual = cartTotal.oldFinalCost
        )
    }

    @Test
    fun `should return 0 newFinalCost when product list is empty`() = runTest {
        // Given

        coEvery { cartProductRepo.getCartProductList() } returns listOf()
        coEvery { getDiscountUseCase() } returns null
        coEvery { deliveryRepo.getDelivery() } returns Delivery(
            cost = 10,
            forFree = 100
        )

        // When
        val cartTotal = getCartTotalUseCase(isDelivery = false)

        // Then
        assertEquals(
            expected = 0,
            actual = cartTotal.newFinalCost
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

        // When
        val cartTotal = getCartTotalUseCase(isDelivery = false)

        // Then
        assertEquals(
            expected = null,
            actual = cartTotal.discount
        )
    }

    @Test
    fun `should return 100 newFinalCost when cart product list has 2 products with 50 newPrice`() =
        runTest {
            // Given
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns null
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 5000
            )

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = 100,
                actual = cartTotal.newFinalCost
            )
        }


    @Test
    fun `should return 200 totalCost when cart product list has 2 products with 100 oldPrice`() =
        runTest {
            // Given
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns null
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 100
            )

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = 100,
                actual = cartTotal.totalCost
            )
        }

    @Test
    fun `should return 200 oldFinalCost when cart product list has 2 products with 100 oldPrice and not equal to newPrice`() =
        runTest {
            // Given
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns null
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 100
            )

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
    fun `should return 110 newFinalCost when cart product list has 2 products with 50 newPrice and delivery`() =
        runTest {
            // Given
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns null
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 5000
            )

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = true)

            // Then
            assertEquals(
                expected = 110,
                actual = cartTotal.newFinalCost
            )
        }

    @Test
    fun `should return 210 oldFinalCost when delivery true`() =
        runTest {
            // Given
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns null
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 100000
            )

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = true)

            // Then
            assertEquals(
                expected = 210,
                actual = cartTotal.oldFinalCost
            )
        }

    //discount
    @Test
    fun `should return 90 newFinalCost when discount 10 percent`() =
        runTest {
            // Given
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns Discount(firstOrderDiscount = 10)
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 100000
            )

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = 90,
                actual = cartTotal.newFinalCost
            )
        }

    @Test
    fun `should return 600 newFinalCost and round to bottom when discount 10 percent and new price 666`() =
        runTest {
            // Given
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockDataWith666NewPrice

            coEvery { getDiscountUseCase() } returns Discount(firstOrderDiscount = 10)
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 100000
            )

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = 600,
                actual = cartTotal.newFinalCost
            )
        }

    @Test
    fun `should return 0 newFinalCost  when discount 100`() =
        runTest {
            // Given
            coEvery { cartProductRepo.getCartProductList() } returns cartProductListMockData

            coEvery { getDiscountUseCase() } returns Discount(firstOrderDiscount = 100)
            coEvery { deliveryRepo.getDelivery() } returns Delivery(
                cost = 10,
                forFree = 100000
            )

            // When
            val cartTotal = getCartTotalUseCase(isDelivery = false)

            // Then
            assertEquals(
                expected = 0,
                actual = cartTotal.newFinalCost
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