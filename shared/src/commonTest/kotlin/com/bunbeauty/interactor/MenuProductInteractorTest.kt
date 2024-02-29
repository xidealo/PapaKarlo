package com.bunbeauty.interactor

import com.bunbeauty.shared.domain.interactor.menu_product.MenuProductInteractor
import com.bunbeauty.shared.domain.model.category.Category
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.domain.repo.MenuProductRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class MenuProductInteractorTest {

    @MockK
    private lateinit var menuProductRepo: MenuProductRepo

    @InjectMockKs
    private lateinit var menuProductInteractor: MenuProductInteractor

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `return 1 element when menu product list has 1 visible element`() = runTest {
        coEvery { menuProductRepo.getMenuProductList() } returns testMenuProductList
        val menuList = menuProductInteractor.getMenuSectionList()
        coVerify { menuProductRepo.getMenuProductList() }
        assertTrue(menuList.size == 1)
    }

    @Test
    fun `return list is empty return null`() = runTest {
        coEvery { menuProductRepo.getMenuProductList() } returns listOf()
        val menuList = menuProductInteractor.getMenuSectionList()
        coVerify { menuProductRepo.getMenuProductList() }
        assertTrue(menuList.isEmpty())
    }

    companion object {
        val testMenuProductList =
            listOf(
                MenuProduct(
                    uuid = "uuid",
                    name = "name",
                    newPrice = 0,
                    oldPrice = 1,
                    utils = "utils",
                    nutrition = 1,
                    description = "description",
                    comboDescription = "comboDescription",
                    photoLink = "photoLink",
                    categoryList = listOf(
                        Category(
                            uuid = "uuid",
                            name = "free",
                            priority = 1
                        )
                    ),
                    visible = true,
                    isRecommended = false,
                    additionGroups = emptyList()
                ),
                MenuProduct(
                    uuid = "uuid2",
                    name = "name",
                    newPrice = 0,
                    oldPrice = 1,
                    utils = "utils",
                    nutrition = 1,
                    description = "description",
                    comboDescription = "comboDescription",
                    photoLink = "photoLink",
                    categoryList = listOf(
                        Category(
                            uuid = "uuid",
                            name = "free",
                            priority = 1
                        )
                    ),
                    visible = false,
                    isRecommended = false,
                    additionGroups = emptyList()
                ),
                MenuProduct(
                    uuid = "uuid3",
                    name = "name",
                    newPrice = 0,
                    oldPrice = 1,
                    utils = "utils",
                    nutrition = 1,
                    description = "description",
                    comboDescription = "comboDescription",
                    photoLink = "photoLink",
                    categoryList = listOf(
                        Category(
                            uuid = "uuid",
                            name = "free",
                            priority = 1
                        )
                    ),
                    visible = false,
                    isRecommended = false,
                    additionGroups = emptyList()
                ),
            )
    }
}