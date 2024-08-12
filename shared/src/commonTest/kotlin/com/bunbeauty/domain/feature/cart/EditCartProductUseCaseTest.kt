package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getAddition
import com.bunbeauty.getCartProduct
import com.bunbeauty.getCartProductAddition
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.feature.cart.EditCartProductUseCase
import com.bunbeauty.shared.domain.repo.CartProductAdditionRepo
import com.bunbeauty.shared.domain.repo.CartProductRepo
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class EditCartProductUseCaseTest {

    private val cartProductRepo: CartProductRepo = mock()
    private val cartProductAdditionRepository: CartProductAdditionRepo =
        mock(MockMode.autofill)
    private val areAdditionsEqualUseCase: AreAdditionsEqualUseCase = mock()
    private val editCartProductUseCase: EditCartProductUseCase = EditCartProductUseCase(
        cartProductRepo = cartProductRepo,
        cartProductAdditionRepository = cartProductAdditionRepository,
        areAdditionsEqualUseCase = areAdditionsEqualUseCase
    )

    @Test
    fun `delete previous additions when additions are not equal`() = runTest {
        val initialCartProductAddition = getCartProductAddition()
        val initialAddition = getAddition()
        val initialCartProduct = getCartProduct(
            menuProduct = getMenuProduct(),
            cartProductAdditionList = listOf(initialCartProductAddition)
        )
        // Given
        everySuspend { cartProductRepo.getCartProduct(initialCartProduct.uuid) } returns initialCartProduct
        everySuspend {
            areAdditionsEqualUseCase(
                initialCartProduct,
                listOf(initialAddition.uuid)
            )
        } returns false
        everySuspend { cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid) } returns Unit
        everySuspend {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
            )
        } returns Unit

        // When
        editCartProductUseCase(initialCartProduct.uuid, listOf(initialAddition))

        // Then
        verifySuspend {
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
        everySuspend { cartProductRepo.getCartProduct(initialCartProduct.uuid) } returns initialCartProduct
        everySuspend {
            areAdditionsEqualUseCase(
                initialCartProduct,
                listOf(initialAddition.uuid)
            )
        } returns false
        everySuspend { cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid) } returns Unit
        everySuspend {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
            )
        } returns Unit

        // When
        editCartProductUseCase(initialCartProduct.uuid, listOf(initialAddition))

        // Then
        verifySuspend {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
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
        everySuspend { cartProductRepo.getCartProduct(initialCartProduct.uuid) } returns null
        everySuspend {
            areAdditionsEqualUseCase(
                initialCartProduct,
                listOf(initialAddition.uuid)
            )
        } returns false
        everySuspend { cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid) } returns Unit
        everySuspend {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
            )
        } returns Unit

        // When
        editCartProductUseCase(initialCartProduct.uuid, listOf(initialAddition))

        // Then
        verifySuspend(mode = VerifyMode.atLeast(0)) {
            cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid)
        }
        verifySuspend(mode = VerifyMode.atLeast(0)) {
            areAdditionsEqualUseCase(
                initialCartProduct,
                listOf(initialAddition.uuid)
            )
        }
        verifySuspend(mode = VerifyMode.atLeast(0)) {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
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
        everySuspend { cartProductRepo.getCartProduct(initialCartProduct.uuid) } returns initialCartProduct
        everySuspend {
            areAdditionsEqualUseCase(
                initialCartProduct,
                listOf(initialAddition.uuid)
            )
        } returns true
        everySuspend { cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid) } returns Unit
        everySuspend {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
            )
        } returns Unit

        // When
        editCartProductUseCase(initialCartProduct.uuid, listOf(initialAddition))

        // Then
        verifySuspend(mode = VerifyMode.atLeast(0)) {
            cartProductAdditionRepository.delete(cartProductAdditionUuid = initialCartProductAddition.uuid)
        }
        verifySuspend(mode = VerifyMode.atLeast(0)) {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = initialCartProduct.uuid,
                addition = initialAddition
            )
        }
    }
}
