package com.bunbeauty.domain.feature.cart

import com.bunbeauty.shared.Constants.CART_PRODUCT_LIMIT
import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.feature.addition.GetIsAdditionsAreEqualUseCase
import com.bunbeauty.shared.domain.feature.cart.EditCartProductUseCase
import com.bunbeauty.shared.domain.repo.CartProductRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse

internal class EditCartProductUseCaseTest {

    private val cartProductRepo: CartProductRepo = mockk()
    private val cartProductAdditionRepository: CartProductAdditionRepository = mockk(relaxed = true)
    private val additionRepository: AdditionRepository = mockk()
    private val getIsAdditionsAreEqualUseCase: GetIsAdditionsAreEqualUseCase = mockk()
    private val menuProductUuid = "menu_product_uuid"
    private lateinit var editCartProductUseCase: EditCartProductUseCase

    @BeforeTest
    fun setup() {
        editCartProductUseCase = EditCartProductUseCase(
            cartProductRepo = cartProductRepo,
            cartProductAdditionRepository = cartProductAdditionRepository,
            additionRepository = additionRepository,
            getIsAdditionsAreEqualUseCase = getIsAdditionsAreEqualUseCase
        )
    }
/*
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
    }*/


}
