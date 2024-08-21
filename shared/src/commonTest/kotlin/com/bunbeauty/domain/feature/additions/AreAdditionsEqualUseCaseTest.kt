package com.bunbeauty.domain.feature.additions

import com.bunbeauty.getCartProduct
import com.bunbeauty.getCartProductAddition
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCaseImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AreAdditionsEqualUseCaseTest {

    private lateinit var areAdditionsEqualUseCase: AreAdditionsEqualUseCaseImpl

    @BeforeTest
    fun setup() {
        areAdditionsEqualUseCase = AreAdditionsEqualUseCaseImpl()
    }

    @Test
    fun `return true when additions are equal`() = runTest {
        val result = areAdditionsEqualUseCase(
            cartProduct = getCartProduct(
                menuProduct = getMenuProduct(),
                cartProductAdditionList = listOf(
                    getCartProductAddition(
                        uuid = "1",
                        additionUuid = "1"
                    ),
                    getCartProductAddition(
                        uuid = "2",
                        additionUuid = "2"
                    ),
                    getCartProductAddition(
                        uuid = "3",
                        additionUuid = "3"
                    )
                )
            ),
            additionUuidList = listOf("1", "2", "3")
        )

        assertTrue(result)
    }

    @Test
    fun `return true when additions are equal and have different order`() = runTest {
        val result = areAdditionsEqualUseCase(
            cartProduct = getCartProduct(
                menuProduct = getMenuProduct(),
                cartProductAdditionList = listOf(
                    getCartProductAddition(
                        uuid = "1",
                        additionUuid = "1"
                    ),
                    getCartProductAddition(
                        uuid = "2",
                        additionUuid = "2"
                    ),
                    getCartProductAddition(
                        uuid = "3",
                        additionUuid = "3"
                    )
                )
            ),
            additionUuidList = listOf("3", "2", "1")
        )

        assertTrue(result)
    }

    @Test
    fun `return false when cart product has more additions`() = runTest {
        val result = areAdditionsEqualUseCase(
            cartProduct = getCartProduct(
                menuProduct = getMenuProduct(),
                cartProductAdditionList = listOf(
                    getCartProductAddition(
                        uuid = "1",
                        additionUuid = "1"
                    ),
                    getCartProductAddition(
                        uuid = "2",
                        additionUuid = "2"
                    ),
                    getCartProductAddition(
                        uuid = "3",
                        additionUuid = "3"
                    ),
                    getCartProductAddition(
                        uuid = "4",
                        additionUuid = "4"
                    )
                )
            ),
            additionUuidList = listOf("1", "2", "3")
        )

        assertFalse(result)
    }

    @Test
    fun `return false when cart product has less additions`() = runTest {
        val result = areAdditionsEqualUseCase(
            cartProduct = getCartProduct(
                menuProduct = getMenuProduct(),
                cartProductAdditionList = listOf(
                    getCartProductAddition(
                        uuid = "1",
                        additionUuid = "1"
                    ),
                    getCartProductAddition(
                        uuid = "2",
                        additionUuid = "2"
                    )
                )
            ),
            additionUuidList = listOf("1", "2", "3")
        )

        assertFalse(result)
    }

    @Test
    fun `return false when cart product has different additions`() = runTest {
        val result = areAdditionsEqualUseCase(
            cartProduct = getCartProduct(
                menuProduct = getMenuProduct(),
                cartProductAdditionList = listOf(
                    getCartProductAddition(
                        uuid = "1",
                        price = null,
                        additionUuid = "1"
                    ),
                    getCartProductAddition(
                        uuid = "2",
                        price = null,
                        additionUuid = "2"
                    ),
                    getCartProductAddition(
                        uuid = "3",
                        price = null,
                        additionUuid = "3"
                    )
                )
            ),
            additionUuidList = listOf("1", "2", "4")
        )

        assertFalse(result)
    }
}
