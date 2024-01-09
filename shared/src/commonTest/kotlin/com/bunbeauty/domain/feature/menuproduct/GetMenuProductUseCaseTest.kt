package com.bunbeauty.domain.feature.menuproduct

import com.bunbeauty.getAddition
import com.bunbeauty.getAdditionGroup
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.domain.feature.menu_product.GetMenuProductUseCase
import com.bunbeauty.shared.domain.model.Discount
import com.bunbeauty.shared.domain.model.order.LightOrder
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
            val initialAdditionGroups = listOf(
                getAdditionGroup(priority = 10),
                getAdditionGroup(priority = 2),
            )

            val initialMenuProduct = getMenuProduct(
                additionGroups = initialAdditionGroups
            )

            val menuProductWithSortedAdditionGroups = getMenuProduct(
                additionGroups = initialAdditionGroups.sortedBy { additionGroup ->
                    additionGroup.priority
                }
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
            val initialAdditions = listOf(
                getAddition(priority = 10),
                getAddition(priority = 2)
            )

            val initialAdditionGroups = listOf(
                getAdditionGroup(
                    additions = initialAdditions
                ),
            )

            val additionGroupsWithSortedAdditions = listOf(
                getAdditionGroup(
                    additions = initialAdditions.sortedBy { addition ->
                        addition.priority
                    }
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