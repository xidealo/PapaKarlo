package com.bunbeauty.domain.feature.cart

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.feature.addition.GetIsAdditionsAreEqual
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.cart.CartProductAddition
import com.bunbeauty.shared.domain.repo.CartProductRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class AddCartProductUseCaseTest {

    private val cartProductRepo: CartProductRepo = mockk()
    private val cartProductAdditionRepository: CartProductAdditionRepository = mockk()
    private val additionRepository: AdditionRepository = mockk()
    private val getIsAdditionsAreEqual: GetIsAdditionsAreEqual = mockk()
    private val menuProductUuid = "menu_product_uuid"
    private lateinit var addCartProduct: AddCartProductUseCase

    @BeforeTest
    fun setup() {
        addCartProduct = AddCartProductUseCase(
            cartProductRepo = cartProductRepo,
            cartProductAdditionRepository = cartProductAdditionRepository,
            additionRepository = additionRepository,
            getIsAdditionsAreEqual = getIsAdditionsAreEqual
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
    fun `update cart product count when product is in cart`() = runTest {
        // Given
        val initialCount = 2
        val initialCartProduct = generateCartProduct(count = initialCount)
        coEvery { cartProductRepo.getCartProductList() } returns listOf(initialCartProduct)
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns listOf(
            initialCartProduct
        )
        coEvery { getIsAdditionsAreEqual(initialCartProduct, emptyList()) } returns true
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
