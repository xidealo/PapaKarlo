package com.bunbeauty.domain.feature.recommendation

import com.bunbeauty.getCartProduct
import com.bunbeauty.getCategoryProduct
import com.bunbeauty.getMenuProduct
import com.bunbeauty.shared.data.repository.RecommendationRepository
import com.bunbeauty.shared.domain.feature.cart.GetRecommendationsUseCase
import com.bunbeauty.shared.domain.repo.CartProductRepo
import com.bunbeauty.shared.domain.repo.MenuProductRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class GetRecommendationsUseCaseTest {

    private val recommendationRepository: RecommendationRepository = mockk()
    private val cartProductRepo: CartProductRepo = mockk()
    private val menuProductRepo: MenuProductRepo = mockk()
    lateinit var getRecommendationsUseCase: GetRecommendationsUseCase

    @BeforeTest
    fun setup() {
        getRecommendationsUseCase = GetRecommendationsUseCase(
            recommendationRepository = recommendationRepository,
            cartProductRepo = cartProductRepo,
            menuProductRepository = menuProductRepo
        )
    }

    @Test
    fun `return empty list when has no recommendations`() = runTest {
        coEvery { recommendationRepository.getMaxVisibleCount() } returns 3
        coEvery { menuProductRepo.getMenuProductList() } returns listOf()
        coEvery { cartProductRepo.getCartProductList() } returns listOf(
            getCartProductWithCategory("1", "1")
        )
        assertTrue(getRecommendationsUseCase().isEmpty())
    }

    @Test
    fun `return empty list when has no visible recommendations`() = runTest {
        coEvery { recommendationRepository.getMaxVisibleCount() } returns 3
        coEvery { menuProductRepo.getMenuProductList() } returns listOf(
            getMenuProductWithCategory("1", "1", visible = false),
            getMenuProductWithCategory("2", "2", visible = false),
            getMenuProductWithCategory("3", "3", visible = false),
        )

        coEvery { cartProductRepo.getCartProductList() } returns listOf()
        assertTrue(getRecommendationsUseCase().isEmpty())
    }

    @Test
    fun `return recommendation filtered by visible`() = runTest {
        coEvery { recommendationRepository.getMaxVisibleCount() } returns 3
        coEvery { menuProductRepo.getMenuProductList() } returns listOf(
            getMenuProductWithCategory("1", "1", visible = false),
            getMenuProductWithCategory("2", "2", visible = false),
            getMenuProductWithCategory("3", "3", visible = true),
        )

        coEvery { cartProductRepo.getCartProductList() } returns listOf()

        assertEquals(
            expected = listOf(
                getMenuProductWithCategory("3", "3"),
            ),
            actual = getRecommendationsUseCase()
        )

    }

    @Test
    fun `return recommendation list when cartProductList has no same category products`() =
        runTest {
            coEvery { recommendationRepository.getMaxVisibleCount() } returns 3
            coEvery { menuProductRepo.getMenuProductList() } returns
                    listOf(
                        getMenuProductWithCategory("2", "1")
                    )

            coEvery { cartProductRepo.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1")
            )

            assertEquals(
                expected = listOf(
                    getMenuProductWithCategory("2", "1")
                ),
                actual = getRecommendationsUseCase()
            )
        }

    @Test
    fun `return empty recommendation list when cartProductList has same category and no other recommendations`() =
        runTest {
            coEvery { recommendationRepository.getMaxVisibleCount() } returns 3
            coEvery { menuProductRepo.getMenuProductList() } returns listOf(
                getMenuProductWithCategory("1", "1")
            )

            coEvery { cartProductRepo.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1")
            )
            assertTrue(getRecommendationsUseCase().isEmpty())
        }

    @Test
    fun `return recommendations list when cartProductList has same category but recommendations has different menuProductUuid`() =
        runTest {
            coEvery { recommendationRepository.getMaxVisibleCount() } returns 3
            coEvery { menuProductRepo.getMenuProductList() } returns listOf(
                getMenuProductWithCategory("1", "1"),
                getMenuProductWithCategory("1", "2")
            )

            coEvery { cartProductRepo.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1")
            )
            assertEquals(
                expected = listOf(
                    getMenuProductWithCategory("1", "2")
                ),
                actual = getRecommendationsUseCase()
            )
        }

    @Test
    fun `return recommendation list when cartProductList has same category but not same by uuid`() =
        runTest {
            coEvery { recommendationRepository.getMaxVisibleCount() } returns 3
            coEvery { menuProductRepo.getMenuProductList() } returns listOf(
                getMenuProductWithCategory("1", "1"),
                getMenuProductWithCategory("2", "2")
            )

            coEvery { cartProductRepo.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1")
            )
            assertEquals(
                expected = listOf(
                    getMenuProductWithCategory("2", "2")
                ),
                actual = getRecommendationsUseCase()
            )
        }

    @Test
    fun `return recommendations size equal maxVisibleCount`() =
        runTest {
            coEvery { recommendationRepository.getMaxVisibleCount() } returns 3
            coEvery { menuProductRepo.getMenuProductList() } returns listOf(
                getMenuProductWithCategory("1", "1"),
                getMenuProductWithCategory("2", "2"),
                getMenuProductWithCategory("3", "3"),
                getMenuProductWithCategory("4", "4"),
                getMenuProductWithCategory("5", "5")
            )
            coEvery { cartProductRepo.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1")
            )

            assertEquals(
                expected = listOf(
                    getMenuProductWithCategory("2", "2"),
                    getMenuProductWithCategory("3", "3"),
                    getMenuProductWithCategory("4", "4")
                ),
                actual = getRecommendationsUseCase()
            )
        }

    @Test
    fun `return recommendations when cartProductList has same category but not all`() =
        runTest {
            coEvery { recommendationRepository.getMaxVisibleCount() } returns 4
            coEvery { menuProductRepo.getMenuProductList() } returns listOf(
                getMenuProductWithCategory("1", "1"),
                getMenuProductWithCategory("2", "2"),
                getMenuProductWithCategory("3", "3"),
                getMenuProductWithCategory("4", "4")

            )

            coEvery { cartProductRepo.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1"),
            )

            assertEquals(
                expected = listOf(
                    getMenuProductWithCategory("2", "2"),
                    getMenuProductWithCategory("3", "3"),
                    getMenuProductWithCategory("4", "4")
                ),
                actual = getRecommendationsUseCase()
            )
        }

    @Test
    fun `return recommendations when cartProducts has same category but not all and many cartProducts`() =
        runTest {
            coEvery { recommendationRepository.getMaxVisibleCount() } returns 4
            coEvery { menuProductRepo.getMenuProductList() } returns listOf(
                getMenuProductWithCategory("1", "1"),
                getMenuProductWithCategory("2", "2"),
                getMenuProductWithCategory("3", "3"),
                getMenuProductWithCategory("4", "4")

            )

            coEvery { cartProductRepo.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1"),
                getCartProductWithCategory("2", "2")
            )
            assertEquals(
                expected = listOf(
                    getMenuProductWithCategory("3", "3"),
                    getMenuProductWithCategory("4", "4")
                ),
                actual = getRecommendationsUseCase()
            )
        }

    private fun getCartProductWithCategory(categoryUuid: String, menuProductUuid: String) =
        getCartProduct(
            menuProduct = getMenuProductWithCategory(categoryUuid, menuProductUuid)
        )

    private fun getMenuProductWithCategory(
        categoryUuid: String,
        menuProductUuid: String,
        visible: Boolean = true,
    ) =
        getMenuProduct(
            uuid = menuProductUuid,
            categoryList = listOf(
                getCategoryProduct(
                    uuid = categoryUuid,
                )
            ),
            visible = visible,
            isRecommended = true
        )
}