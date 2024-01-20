package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getAddition
import com.bunbeauty.getAdditionGroup
import com.bunbeauty.getCartProduct
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.data.repository.AdditionGroupRepository
import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.addition.CartProductAddition
import com.bunbeauty.shared.domain.repo.CartProductRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class AddCartProductUseCaseTest {

    private val cartProductRepo: CartProductRepo = mockk()
    private val cartProductAdditionRepository: CartProductAdditionRepository = mockk(relaxed = true)
    private val additionRepository: AdditionRepository = mockk()
    private val areAdditionsEqualUseCase: AreAdditionsEqualUseCase = mockk()
    private val additionGroupRepository: AdditionGroupRepository = mockk()
    private val getAdditionPriorityUseCase: GetAdditionPriorityUseCase = mockk()
    private val menuProductUuid = "menu_product_uuid"
    private lateinit var addCartProduct: AddCartProductUseCase

    @BeforeTest
    fun setup() {
        addCartProduct = AddCartProductUseCase(
            cartProductRepo = cartProductRepo,
            cartProductAdditionRepository = cartProductAdditionRepository,
            additionRepository = additionRepository,
            areAdditionsEqualUseCase = areAdditionsEqualUseCase,
            additionGroupRepository = additionGroupRepository,
            getAdditionPriorityUseCase = getAdditionPriorityUseCase
        )
    }

    @Test
    fun `return false when cart is full`() = runTest {
        // Given
        coEvery { cartProductRepo.getCartProductList() } returns generateCartProducts(
            CART_PRODUCT_LIMIT
        )

        // When
        val result = addCartProduct(menuProductUuid, emptyList())

        // Then
        assertFalse(result)
    }

    @Test
    fun `save new cart product when product is not in cart`() = runTest {
        // Given
        coEvery { cartProductRepo.getCartProductList() } returns emptyList()
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns emptyList()
        coEvery { cartProductRepo.saveAsCartProduct(menuProductUuid) } returns generateCartProduct().uuid

        // When
        val result = addCartProduct(menuProductUuid, emptyList())

        // Then
        assertTrue(result)
    }

    @Test
    fun `save new cart product when product has not same additions`() = runTest {
        // Given
        val initialCartProduct = getCartProduct(
            menuProduct = getMenuProduct(),
            cartProductAdditionList = emptyList()
        )
        val initialAdditionList = listOf(
            getAddition("1"),
            getAddition("2")
        )
        coEvery { cartProductRepo.getCartProductList() } returns listOf(
            initialCartProduct
        )
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns listOf(
            initialCartProduct
        )
        coEvery {
            areAdditionsEqualUseCase(
                initialCartProduct,
                initialAdditionList.map { addition -> addition.uuid })
        } returns false
        coEvery {
            additionGroupRepository.getAdditionGroup("")
        } returns getAdditionGroup()

        coEvery {
            getAdditionPriorityUseCase(any(), any())
        } returns 0

        coEvery { cartProductRepo.saveAsCartProduct(menuProductUuid) } returns initialCartProduct.uuid
        coEvery { additionRepository.getAddition("1") } returns getAddition("1")
        coEvery { additionRepository.getAddition("2") } returns getAddition("2")

        // When
        val result =
            addCartProduct(menuProductUuid, initialAdditionList.map { addition -> addition.uuid })

        // Then
        coVerify { cartProductRepo.saveAsCartProduct(menuProductUuid) }

        coVerify {
            cartProductAdditionRepository.saveAsCartProductAddition(
                initialCartProduct.uuid,
                getAddition("1")
            )
        }
        coVerify {
            cartProductAdditionRepository.saveAsCartProductAddition(
                initialCartProduct.uuid,
                getAddition("2")
            )
        }
        assertTrue(result)
    }

    @Test
    fun `update cart product when product has same additions`() = runTest {
        // Given
        val initialCount = 2
        val initialCartProduct = getCartProduct(
            count = initialCount,
            menuProduct = getMenuProduct(),
            cartProductAdditionList = emptyList()
        )
        val initialAdditionList = listOf(
            getAddition("1"),
            getAddition("2")
        )
        coEvery { cartProductRepo.getCartProductList() } returns listOf(
            initialCartProduct
        )
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns listOf(
            initialCartProduct
        )
        coEvery {
            areAdditionsEqualUseCase(
                initialCartProduct,
                initialAdditionList.map { addition -> addition.uuid })
        } returns true

        coEvery {
            cartProductRepo.updateCartProductCount(
                initialCartProduct.uuid,
                initialCount + 1
            )
        } returns true
        coEvery { additionRepository.getAddition("1") } returns getAddition("1")
        coEvery { additionRepository.getAddition("2") } returns getAddition("2")

        // When
        val result =
            addCartProduct(menuProductUuid, initialAdditionList.map { addition -> addition.uuid })

        // Then
        coVerify {
            cartProductRepo.updateCartProductCount(
                initialCartProduct.uuid,
                initialCount + 1
            )
        }

        assertTrue(result)
    }

    @Test
    fun `update cart product count when product is in cart`() = runTest {
        // Given
        val initialCount = 2
        val initialCartProduct = generateCartProduct(count = initialCount)
        coEvery { cartProductRepo.getCartProductList() } returns listOf(initialCartProduct)
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns listOf(
            initialCartProduct
        )
        coEvery { areAdditionsEqualUseCase(initialCartProduct, emptyList()) } returns true
        coEvery {
            cartProductRepo.updateCartProductCount(
                initialCartProduct.uuid,
                initialCount + 1
            )
        } returns true

        // When
        val result = addCartProduct(menuProductUuid, emptyList())

        // Then
        assertTrue(result)
    }

    companion object {

        private fun generateCartProducts(count: Int): List<CartProduct> {
            return List(count) {
                generateCartProduct(uuid = "cart_uuid_$it")
            }
        }

        private fun generateCartProduct(
            uuid: String = "cart_uuid",
            count: Int = 1,
            cartProductAdditionList: List<CartProductAddition> = emptyList(),
        ): CartProduct {
            return CartProduct(uuid, count, mockk(), cartProductAdditionList)
        }
    }
}
