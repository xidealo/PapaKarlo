package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getAddition
import com.bunbeauty.getAdditionGroup
import com.bunbeauty.getCartProduct
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.data.repository.AdditionGroupRepository
import com.bunbeauty.shared.data.repository.AdditionRepository
import com.bunbeauty.shared.data.repository.CartProductAdditionRepository
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.shared.domain.feature.cart.GetCartProductCountUseCase
import com.bunbeauty.shared.domain.repo.CartProductRepo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

internal class AddCartProductUseCaseTest {

    private val getCartProductCountUseCase: GetCartProductCountUseCase = mockk()
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
            getCartProductCountUseCase = getCartProductCountUseCase,
            cartProductRepo = cartProductRepo,
            cartProductAdditionRepository = cartProductAdditionRepository,
            additionRepository = additionRepository,
            areAdditionsEqualUseCase = areAdditionsEqualUseCase,
            additionGroupRepository = additionGroupRepository,
            getAdditionPriorityUseCase = getAdditionPriorityUseCase
        )
    }

    @Test
    fun `do nothing when cart is full`() = runTest {
        // Given
        coEvery {
            getCartProductCountUseCase()
        } returns 99

        // When
        addCartProduct(
            menuProductUuid = menuProductUuid,
            additionUuidList = emptyList()
        )

        // Then
        coVerify(exactly = 0) {
            cartProductRepo.getCartProductListByMenuProductUuid(any())
        }
        coVerify(exactly = 0) {
            cartProductRepo.saveAsCartProduct(any())
        }
        coVerify(exactly = 0) {
            cartProductRepo.updateCartProductCount(any(), any())
        }
    }

    @Test
    fun `save as new cart product when there is no cart product with the same uuid`() = runTest {
        // Given
        val menuProductUuid = "menuProductUuid"
        val cartProductUuid = "cartProductUuid"
        coEvery { getCartProductCountUseCase() } returns 0
        coEvery {
            cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid = menuProductUuid)
        } returns emptyList()
        coEvery { cartProductRepo.saveAsCartProduct(menuProductUuid = menuProductUuid) } returns cartProductUuid

        // When
        addCartProduct(
            menuProductUuid = menuProductUuid,
            additionUuidList = emptyList()
        )

        // Then
        coVerify(exactly = 1) { cartProductRepo.saveAsCartProduct(menuProductUuid = menuProductUuid) }
    }

    @Test
    fun `save as new cart product when there is cart product with the same uuid but different additions`() = runTest {
        // Given
        val menuProductUuid = "menuProductUuid"
        val cartProduct = getCartProduct(
            menuProduct = getMenuProduct(uuid = menuProductUuid),
            cartProductAdditionList = emptyList()
        )
        val additionGroupUuid = "additionGroupUuid"
        val additionUuid1 = "additionUuid1"
        val additionUuid2 = "additionUuid2"
        val addition1 = getAddition(uuid = additionUuid1, additionGroupUuid = additionGroupUuid)
        val addition2 = getAddition(uuid = additionUuid2, additionGroupUuid = additionGroupUuid)
        val additionUuidList = listOf(additionUuid1, additionUuid2)
        val additionGroup = getAdditionGroup(uuid = additionGroupUuid)
        val cartProductUuid = "cartProductUuid"
        coEvery { getCartProductCountUseCase() } returns 1
        coEvery {
            cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid = menuProductUuid)
        } returns listOf(cartProduct)
        coEvery {
            areAdditionsEqualUseCase(
                cartProduct = cartProduct,
                additionUuidList = additionUuidList
            )
        } returns false
        coEvery {
            additionGroupRepository.getAdditionGroup(uuid = additionGroupUuid)
        } returns additionGroup
        coEvery {
            getAdditionPriorityUseCase(
                additionGroup = additionGroup,
                addition = addition1
            )
        } returns 1
        coEvery {
            getAdditionPriorityUseCase(
                additionGroup = additionGroup,
                addition = addition2
            )
        } returns 2
        coEvery { cartProductRepo.saveAsCartProduct(menuProductUuid = menuProductUuid) } returns cartProductUuid
        coEvery { additionRepository.getAddition(uuid = additionUuid1) } returns addition1
        coEvery { additionRepository.getAddition(uuid = additionUuid2) } returns addition2
        coEvery {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = cartProductUuid,
                addition = addition1.copy(priority = 1)
            )
        } returns Unit
        coEvery {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = cartProductUuid,
                addition = addition2.copy(priority = 2)
            )
        } returns Unit

        // When
        addCartProduct(menuProductUuid = menuProductUuid, additionUuidList = additionUuidList)

        // Then
        coVerify(exactly = 1) { cartProductRepo.saveAsCartProduct(menuProductUuid = menuProductUuid) }
        coVerify(exactly = 1) {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = cartProductUuid,
                addition = addition1.copy(priority = 1)
            )
        }
        coVerify(exactly = 1) {
            cartProductAdditionRepository.saveAsCartProductAddition(
                cartProductUuid = cartProductUuid,
                addition = addition2.copy(priority = 2)
            )
        }
    }

    @Test
    fun `update cart product when there is cart product with the same uuid and different`() = runTest {
        // Given
        val menuProductUuid = "menuProductUuid"
        val cartProductUuid = "cartProductUuid"
        val cartProduct = getCartProduct(
            uuid = cartProductUuid,
            menuProduct = getMenuProduct(uuid = menuProductUuid),
            count = 2,
            cartProductAdditionList = emptyList()
        )
        val additionUuidList = listOf("additionUuid1", "additionUuid2")
        coEvery { getCartProductCountUseCase() } returns 1
        coEvery {
            cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid = menuProductUuid)
        } returns listOf(cartProduct)
        coEvery {
            areAdditionsEqualUseCase(
                cartProduct = cartProduct,
                additionUuidList = additionUuidList
            )
        } returns true
        coEvery {
            cartProductRepo.updateCartProductCount(
                cartProductUuid = cartProductUuid,
                count = 3
            )
        } returns Unit

        // When
        addCartProduct(menuProductUuid = menuProductUuid, additionUuidList = additionUuidList)

        // Then
        coVerify(exactly = 1) {
            cartProductRepo.updateCartProductCount(
                cartProductUuid = cartProductUuid,
                count = 3
            )
        }
    }

}
