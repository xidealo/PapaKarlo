package com.bunbeauty.domain.feature.menuproduct

import com.bunbeauty.getAddition
import com.bunbeauty.getAdditionGroup
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.domain.feature.menu_product.GetMenuProductUseCase
import com.bunbeauty.shared.domain.repo.MenuProductRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMenuProductUseCaseTest {
    private val menuProductRepo: MenuProductRepo = mockk()
    private val menuProductUuid = "menuProductUuid"

    private lateinit var getMenuProductUseCase: GetMenuProductUseCase

    @BeforeTest
    fun setup() {
        getMenuProductUseCase = GetMenuProductUseCase(
            menuProductRepo = menuProductRepo
        )
    }

    @Test
    fun `should return menu product with addition group list sorted by priority`() =
        runTest {
            // Given
            val additionGroup10 = getAdditionGroup(priority = 10)
            val additionGroup2 = getAdditionGroup(priority = 2)

            val initialAdditionGroups = listOf(
                additionGroup10,
                additionGroup2
            )

            val initialMenuProduct = getMenuProduct(
                additionGroups = initialAdditionGroups
            )

            val menuProductWithSortedAdditionGroups = getMenuProduct(
                additionGroups = listOf(
                    additionGroup2,
                    additionGroup10
                )
            )

            coEvery { menuProductRepo.getMenuProductByUuid(menuProductUuid) } returns initialMenuProduct

            // When
            val result = getMenuProductUseCase(menuProductUuid)

            // Then
            assertEquals(
                expected = menuProductWithSortedAdditionGroups,
                actual = result
            )
        }


    @Test
    fun `should return menu product with addition list sorted by priority`() =
        runTest {
            // Given

            val addition10 = getAddition(priority = 10)
            val addition2 = getAddition(priority = 2)

            val initialAdditionGroups = listOf(
                getAdditionGroup(
                    additions = listOf(
                        addition10,
                        addition2
                    )
                ),
            )

            val additionGroupsWithSortedAdditions = listOf(
                getAdditionGroup(
                    additions = listOf(
                        addition2,
                        addition10
                    )
                ),
            )

            val initialMenuProduct = getMenuProduct(
                additionGroups = initialAdditionGroups
            )

            val menuProductWithSortedAdditionGroups = getMenuProduct(
                additionGroups = additionGroupsWithSortedAdditions
            )

            coEvery { menuProductRepo.getMenuProductByUuid(menuProductUuid) } returns initialMenuProduct

            // When
            val result = getMenuProductUseCase(menuProductUuid)

            // Then
            assertEquals(
                expected = menuProductWithSortedAdditionGroups,
                actual = result
            )
        }

}