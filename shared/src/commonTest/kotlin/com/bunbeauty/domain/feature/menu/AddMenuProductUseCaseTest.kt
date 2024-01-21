package com.bunbeauty.domain.feature.menu

import com.bunbeauty.getCartProduct
import com.bunbeauty.shared.domain.feature.menu.AddMenuProductUseCase
import com.bunbeauty.shared.domain.repo.CartProductRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class AddMenuProductUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var cartProductRepo: CartProductRepo

    @InjectMockKs
    private lateinit var addMenuProductUseCase: AddMenuProductUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `save as new cart product when there is no cart product with the same uuid`() = runTest {
        val menuProductUuid = "menuProductUuid"
        val cartProductUuid = "cartProductUuid"
        coEvery { cartProductRepo.getCartProductListByMenuProductUuid(menuProductUuid) } returns emptyList()
        coEvery { cartProductRepo.saveAsCartProduct(menuProductUuid) } returns cartProductUuid

        addMenuProductUseCase(menuProductUuid)

        coVerify(exactly = 1) {
            cartProductRepo.saveAsCartProduct(menuProductUuid)
        }
    }

    @Test
    fun `update existed cart product when there is cart product with the same uuid`() = runTest {
        val menuProductUuid = "menuProductUuid"
        val cartProductUuid = "cartProductUuid"
        val cartProductList = listOf(
            getCartProduct(uuid = cartProductUuid, count = 2)
        )
        coEvery {
            cartProductRepo.getCartProductListByMenuProductUuid(
                menuProductUuid = menuProductUuid
            )
        } returns cartProductList
        coEvery {
            cartProductRepo.updateCartProductCount(
                cartProductUuid = cartProductUuid,
                count = 3
            )
        } returns true

        addMenuProductUseCase(menuProductUuid)

        coVerify(exactly = 1) {
            cartProductRepo.updateCartProductCount(
                cartProductUuid = cartProductUuid,
                count = 3
            )
        }
    }
}