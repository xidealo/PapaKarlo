package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getAddition
import com.bunbeauty.getCartProduct
import com.bunbeauty.getCartProductAddition
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.feature.cart.EditCartProductUseCase
import com.bunbeauty.shared.domain.repo.CartProductRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class EditCartProductUseCaseTest {

    private val cartProductRepo: CartProductRepo = mockk()
    private val cartProductAdditionRepository: CartProductAdditionRepository = mockk(relaxed = true)
    private val additionRepository: AdditionRepository = mockk()
    private val areAdditionsEqualUseCase: AreAdditionsEqualUseCase = mockk()
    private lateinit var editCartProductUseCase: EditCartProductUseCase

    @BeforeTest
    fun setup() {
        editCartProductUseCase = EditCartProductUseCase(
            cartProductRepo = cartProductRepo,
            cartProductAdditionRepository = cartProductAdditionRepository,
            additionRepository = additionRepository,
            areAdditionsEqualUseCase = areAdditionsEqualUseCase
        )
    }

    @Test
    fun `delete previous additions when additions are not equal`() = runTest {
        val initialCartProductAddition = getCartProductAddition()
        val initialAddition = getAddition()
        val initialCartProduct = getCartProduct(
            menuProduct = getMenuProduct(),
            cartProductAdditionList = listOf(initialCartProductAddition)
        )
        // Given
        coEvery { cartProductRepo.getCartProduct(initialCartProduct.uuid) } returns initialCartProduct
        coEvery {
            areAdditionsEqualUseCase(
                initialCartProduct,
                listOf(initialAddition.uuid)
            )
        } returns false
        coEvery { cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid) } returns Unit
        coEvery { additionRepository.getAddition(uuid = initialAddition.uuid) } returns initialAddition
        coEvery {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
            )
        } returns Unit

        // When
        editCartProductUseCase(initialCartProduct.uuid, listOf(initialAddition.uuid))

        // Then
        coVerify {
            cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid)
        }
    }

    @Test
    fun `insert new additions when additions are not equal`() = runTest {
        val initialCartProductAddition = getCartProductAddition()
        val initialAddition = getAddition()
        val initialCartProduct = getCartProduct(
            menuProduct = getMenuProduct(),
            cartProductAdditionList = listOf(initialCartProductAddition)
        )
        // Given
        coEvery { cartProductRepo.getCartProduct(initialCartProduct.uuid) } returns initialCartProduct
        coEvery {
            areAdditionsEqualUseCase(
                initialCartProduct,
                listOf(initialAddition.uuid)
            )
        } returns false
        coEvery { cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid) } returns Unit
        coEvery { additionRepository.getAddition(uuid = initialAddition.uuid) } returns initialAddition
        coEvery {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
            )
        } returns Unit

        // When
        editCartProductUseCase(initialCartProduct.uuid, listOf(initialAddition.uuid))

        // Then
        coVerify {
            additionRepository.getAddition(uuid = initialAddition.uuid)
        }
        coVerify {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition,
            )
        }
    }

    @Test
    fun `return from method when not found cart product`() = runTest {
        val initialCartProductAddition = getCartProductAddition()
        val initialAddition = getAddition()
        val initialCartProduct = getCartProduct(
            menuProduct = getMenuProduct(),
            cartProductAdditionList = listOf(initialCartProductAddition)
        )

        // Given
        coEvery { cartProductRepo.getCartProduct(initialCartProduct.uuid) } returns null
        coEvery {
            areAdditionsEqualUseCase(
                initialCartProduct,
                listOf(initialAddition.uuid)
            )
        } returns false
        coEvery { cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid) } returns Unit
        coEvery { additionRepository.getAddition(uuid = initialAddition.uuid) } returns initialAddition
        coEvery {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
            )
        } returns Unit

        // When
        editCartProductUseCase(initialCartProduct.uuid, listOf(initialAddition.uuid))

        // Then
        coVerify(exactly = 0) {
            cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid)
        }
        coVerify(exactly = 0) {
            areAdditionsEqualUseCase(
                initialCartProduct,
                listOf(initialAddition.uuid)
            )
        }
        coVerify(exactly = 0) {
            additionRepository.getAddition(uuid = initialAddition.uuid)
        }
        coVerify(exactly = 0) {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition,
            )
        }
    }

    @Test
    fun `return from method when has same additions`() = runTest {
        val initialCartProductAddition = getCartProductAddition()
        val initialAddition = getAddition()
        val initialCartProduct = getCartProduct(
            menuProduct = getMenuProduct(),
            cartProductAdditionList = listOf(initialCartProductAddition)
        )

        // Given
        coEvery { cartProductRepo.getCartProduct(initialCartProduct.uuid) } returns initialCartProduct
        coEvery {
            areAdditionsEqualUseCase(
                initialCartProduct,
                listOf(initialAddition.uuid)
            )
        } returns true
        coEvery { cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid) } returns Unit
        coEvery { additionRepository.getAddition(uuid = initialAddition.uuid) } returns initialAddition
        coEvery {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
            )
        } returns Unit

        // When
        editCartProductUseCase(initialCartProduct.uuid, listOf(initialAddition.uuid))

        // Then
        coVerify(exactly = 0) {
            cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid)
        }
        coVerify(exactly = 0) {
            additionRepository.getAddition(uuid = initialAddition.uuid)
        }
        coVerify(exactly = 0) {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition,
            )
        }
    }

}
