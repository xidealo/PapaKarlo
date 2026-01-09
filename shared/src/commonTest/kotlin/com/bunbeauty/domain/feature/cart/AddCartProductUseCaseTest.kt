package com.bunbeauty.domain.feature.cart

import com.bunbeauty.getAddition
import com.bunbeauty.getAdditionGroup
import com.bunbeauty.getCartProduct
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.domain.exeptions.CartProductLimitReachedException
import com.bunbeauty.shared.domain.feature.addition.AreAdditionsEqualUseCase
import com.bunbeauty.shared.domain.feature.addition.GetAdditionPriorityUseCase
import com.bunbeauty.shared.domain.feature.cart.AddCartProductUseCase
import com.bunbeauty.core.domain.GetCartProductCountUseCase
import com.bunbeauty.shared.domain.repo.AdditionGroupRepo
import com.bunbeauty.shared.domain.repo.AdditionRepo
import com.bunbeauty.shared.domain.repo.CartProductAdditionRepo
import com.bunbeauty.core.domain.repo.CartProductRepo
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

class AddCartProductUseCaseTest {
    private val getCartProductCountUseCase: GetCartProductCountUseCase = mock()
    private val cartProductRepo: CartProductRepo = mock()
    private val cartProductAdditionRepository: CartProductAdditionRepo = mock(MockMode.autofill)
    private val additionRepository: AdditionRepo = mock()
    private val areAdditionsEqualUseCase: AreAdditionsEqualUseCase = mock()
    private val additionGroupRepository: AdditionGroupRepo = mock()
    private val getAdditionPriorityUseCase: GetAdditionPriorityUseCase = mock()
    private val menuProductUuid = "menu_product_uuid"
    private val addCartProduct: AddCartProductUseCase =
        AddCartProductUseCase(
            getCartProductCountUseCase = getCartProductCountUseCase,
            cartProductRepo = cartProductRepo,
            cartProductAdditionRepository = cartProductAdditionRepository,
            additionRepository = additionRepository,
            areAdditionsEqualUseCase = areAdditionsEqualUseCase,
            additionGroupRepository = additionGroupRepository,
            getAdditionPriorityUseCase = getAdditionPriorityUseCase,
        )

    @Test
    fun `throws CartProductLimitReachedException when cart is full`() =
        runTest {
            // Given
            everySuspend {
                getCartProductCountUseCase()
            } returns 99

            // When-Then
            assertFailsWith(CartProductLimitReachedException::class) {
                addCartProduct(
                    menuProductUuid = menuProductUuid,
                    additionUuidList = emptyList(),
                )
            }
            verifySuspend(mode = VerifyMode.atLeast(0)) {
                cartProductRepo.getCartProductListByMenuProductUuid(any())
            }
            verifySuspend(mode = VerifyMode.atLeast(0)) {
                cartProductRepo.saveAsCartProduct(any())
            }
            verifySuspend(mode = VerifyMode.atLeast(0)) {
                cartProductRepo.updateCartProductCount(any(), any())
            }
        }

    @Test
    fun `save as new cart product when there is no cart product with the same uuid`() =
        runTest {
            // Given
            val menuProductUuid = "menuProductUuid"
            val cartProductUuid = "cartProductUuid"
            everySuspend { getCartProductCountUseCase() } returns 0
            everySuspend {
                cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid = menuProductUuid)
            } returns emptyList()
            everySuspend { cartProductRepo.saveAsCartProduct(menuProductUuid = menuProductUuid) } returns cartProductUuid

            // When
            addCartProduct(
                menuProductUuid = menuProductUuid,
                additionUuidList = emptyList(),
            )

            // Then
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.saveAsCartProduct(
                    menuProductUuid = menuProductUuid,
                )
            }
        }

    @Test
    fun `save as new cart product when there is cart product with the same uuid but different additions`() =
        runTest {
            // Given
            val menuProductUuid = "menuProductUuid"
            val cartProduct =
                getCartProduct(
                    menuProduct = getMenuProduct(uuid = menuProductUuid),
                    cartProductAdditionList = emptyList(),
                )
            val additionGroupUuid = "additionGroupUuid"
            val additionUuid1 = "additionUuid1"
            val additionUuid2 = "additionUuid2"
            val addition1 = getAddition(uuid = additionUuid1, additionGroupUuid = additionGroupUuid)
            val addition2 = getAddition(uuid = additionUuid2, additionGroupUuid = additionGroupUuid)
            val additionUuidList = listOf(additionUuid1, additionUuid2)
            val additionGroup = getAdditionGroup(uuid = additionGroupUuid)
            val cartProductUuid = "cartProductUuid"
            everySuspend { getCartProductCountUseCase() } returns 1
            everySuspend {
                cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid = menuProductUuid)
            } returns listOf(cartProduct)
            everySuspend {
                areAdditionsEqualUseCase(
                    cartProduct = cartProduct,
                    additionUuidList = additionUuidList,
                )
            } returns false
            everySuspend {
                additionGroupRepository.getAdditionGroup(uuid = additionGroupUuid)
            } returns additionGroup
            everySuspend {
                getAdditionPriorityUseCase(
                    additionGroup = additionGroup,
                    addition = addition1,
                )
            } returns 1
            everySuspend {
                getAdditionPriorityUseCase(
                    additionGroup = additionGroup,
                    addition = addition2,
                )
            } returns 2
            everySuspend { cartProductRepo.saveAsCartProduct(menuProductUuid = menuProductUuid) } returns cartProductUuid
            everySuspend { additionRepository.getAddition(uuid = additionUuid1) } returns addition1
            everySuspend { additionRepository.getAddition(uuid = additionUuid2) } returns addition2
            everySuspend {
                cartProductAdditionRepository.saveAsCartProductAddition(
                    cartProductUuid = cartProductUuid,
                    addition = addition1.copy(priority = 1),
                )
            } returns Unit
            everySuspend {
                cartProductAdditionRepository.saveAsCartProductAddition(
                    cartProductUuid = cartProductUuid,
                    addition = addition2.copy(priority = 2),
                )
            } returns Unit

            // When
            addCartProduct(menuProductUuid = menuProductUuid, additionUuidList = additionUuidList)

            // Then
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.saveAsCartProduct(
                    menuProductUuid = menuProductUuid,
                )
            }
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductAdditionRepository.saveAsCartProductAddition(
                    cartProductUuid = cartProductUuid,
                    addition = addition1.copy(priority = 1),
                )
            }
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductAdditionRepository.saveAsCartProductAddition(
                    cartProductUuid = cartProductUuid,
                    addition = addition2.copy(priority = 2),
                )
            }
        }

    @Test
    fun `update cart product when there is cart product with the same uuid and different`() =
        runTest {
            // Given
            val menuProductUuid = "menuProductUuid"
            val cartProductUuid = "cartProductUuid"
            val cartProduct =
                getCartProduct(
                    uuid = cartProductUuid,
                    menuProduct = getMenuProduct(uuid = menuProductUuid),
                    count = 2,
                    cartProductAdditionList = emptyList(),
                )
            val additionUuidList = listOf("additionUuid1", "additionUuid2")
            everySuspend { getCartProductCountUseCase() } returns 1
            everySuspend {
                cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid = menuProductUuid)
            } returns listOf(cartProduct)
            everySuspend {
                areAdditionsEqualUseCase(
                    cartProduct = cartProduct,
                    additionUuidList = additionUuidList,
                )
            } returns true
            everySuspend {
                cartProductRepo.updateCartProductCount(
                    cartProductUuid = cartProductUuid,
                    count = 3,
                )
            } returns Unit

            // When
            addCartProduct(menuProductUuid = menuProductUuid, additionUuidList = additionUuidList)

            // Then
            verifySuspend(mode = VerifyMode.atLeast(1)) {
                cartProductRepo.updateCartProductCount(
                    cartProductUuid = cartProductUuid,
                    count = 3,
                )
            }
        }
}
