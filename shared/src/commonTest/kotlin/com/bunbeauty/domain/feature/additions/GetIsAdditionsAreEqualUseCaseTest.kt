package com.bunbeauty.domain.feature.additions

import com.bunbeauty.getCartProduct
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.domain.feature.addition.GetIsAdditionsAreEqualUseCase
import com.bunbeauty.shared.domain.model.cart.CartProductAddition
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GetIsAdditionsAreEqualUseCaseTest {

    private lateinit var getIsAdditionsAreEqualUseCase: GetIsAdditionsAreEqualUseCase

    @BeforeTest
    fun setup() {
        getIsAdditionsAreEqualUseCase = GetIsAdditionsAreEqualUseCase()
    }

    @Test
    fun `return true when addition uuid list are equal to cart additions`() = runTest {
        val result = getIsAdditionsAreEqualUseCase(
            initialCartProduct = getCartProduct(
                menuProduct = getMenuProduct(),
                cartProductAdditionList = listOf(
                    CartProductAddition(
                        uuid = "1",
                        name = "1",
                        fullName = null,
                        price = null,
                        cartProductUuid = "1",
                        additionUuid = "1"
                    ),
                    CartProductAddition(
                        uuid = "2",
                        name = "2",
                        fullName = null,
                        price = null,
                        cartProductUuid = "2",
                        additionUuid = "2"
                    ),
                    CartProductAddition(
                        uuid = "3",
                        name = "3",
                        fullName = null,
                        price = null,
                        cartProductUuid = "3",
                        additionUuid = "3"
                    ),
                )
            ),
            additionUuidList = listOf("1", "2", "3"),
        )
        assertTrue(result)
    }

    @Test
    fun `return false when addition uuid list are not equal to cart additions`() = runTest {
        val result = getIsAdditionsAreEqualUseCase(
            initialCartProduct = getCartProduct(
                menuProduct = getMenuProduct(),
                cartProductAdditionList = listOf(
                    CartProductAddition(
                        uuid = "1",
                        name = "1",
                        fullName = null,
                        price = null,
                        cartProductUuid = "1",
                        additionUuid = "1"
                    ),
                    CartProductAddition(
                        uuid = "2",
                        name = "2",
                        fullName = null,
                        price = null,
                        cartProductUuid = "2",
                        additionUuid = "2"
                    ),
                    CartProductAddition(
                        uuid = "3",
                        name = "3",
                        fullName = null,
                        price = null,
                        cartProductUuid = "3",
                        additionUuid = "3"
                    ),
                )
            ),
            additionUuidList = listOf("1", "2", "4"),
        )
        assertFalse(result)
    }

    @Test
    fun `return false when addition uuid list has less elements than`() = runTest {
        val result = getIsAdditionsAreEqualUseCase(
            initialCartProduct = getCartProduct(
                menuProduct = getMenuProduct(),
                cartProductAdditionList = listOf(
                    CartProductAddition(
                        uuid = "1",
                        name = "1",
                        fullName = null,
                        price = null,
                        cartProductUuid = "1",
                        additionUuid = "1"
                    ),
                    CartProductAddition(
                        uuid = "2",
                        name = "2",
                        fullName = null,
                        price = null,
                        cartProductUuid = "2",
                        additionUuid = "2"
                    ),
                    CartProductAddition(
                        uuid = "3",
                        name = "3",
                        fullName = null,
                        price = null,
                        cartProductUuid = "3",
                        additionUuid = "3"
                    ),
                )
            ),
            additionUuidList = listOf("1", "2"),
        )
        assertFalse(result)
    }

}