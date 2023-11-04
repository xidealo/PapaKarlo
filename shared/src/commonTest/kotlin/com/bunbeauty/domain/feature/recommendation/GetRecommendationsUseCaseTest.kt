package com.bunbeauty.domain.feature.recommendation

import com.bunbeauty.getCartProduct
import com.bunbeauty.getCategoryProduct
import com.bunbeauty.getMenuProduct
import com.bunbeauty.getRecommendationProduct
import com.bunbeauty.getRecommendationProductList
import com.bunbeauty.shared.data.repository.RecommendationRepository
import com.bunbeauty.shared.domain.feature.cart.GetRecommendationsUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.model.RecommendationProduct
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class GetRecommendationsUseCaseTest {

    private val recommendationRepository: RecommendationRepository = mockk()
    private val cartProductInteractor: ICartProductInteractor = mockk()
    lateinit var getRecommendationsUseCase: GetRecommendationsUseCase

    @BeforeTest
    fun setup() {
        getRecommendationsUseCase = GetRecommendationsUseCase(
            recommendationRepository = recommendationRepository,
            cartProductInteractor = cartProductInteractor,
        )
    }

    @Test
    fun `return empty list when has no recommendations`() = runTest {
        coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList()

        coEvery { cartProductInteractor.getCartProductList() } returns listOf(
            getCartProductWithCategory("1", "1")
        )
        assertTrue(getRecommendationsUseCase().isEmpty())
    }

    @Test
    fun `return empty list when has no visible recommendations`() = runTest {
        coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
            maxVisibleCount = 3,
            recommendationProductList = listOf(
                getRecommendationProduct(
                    uuid = "1",
                    menuProduct = getMenuProductWithCategory("1", "1"),
                    isVisible = false
                ),
                getRecommendationProduct(
                    uuid = "2",
                    menuProduct = getMenuProductWithCategory("2", "2"),
                    isVisible = false
                ),
                getRecommendationProduct(
                    uuid = "3",
                    menuProduct = getMenuProductWithCategory("3", "3"),
                    isVisible = false
                )
            )
        )

        coEvery { cartProductInteractor.getCartProductList() } returns listOf()
        assertTrue(getRecommendationsUseCase().isEmpty())
    }

    @Test
    fun `return recommendation filtered by visible`() = runTest {
        coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
            maxVisibleCount = 3,
            recommendationProductList = listOf(
                getRecommendationProduct(
                    uuid = "1",
                    menuProduct = getMenuProductWithCategory("1", "1"),
                    isVisible = false
                ),
                getRecommendationProduct(
                    uuid = "2",
                    menuProduct = getMenuProductWithCategory("2", "2"),
                    isVisible = false
                ),
                getRecommendationProduct(
                    uuid = "3",
                    menuProduct = getMenuProductWithCategory("3", "3"),
                    isVisible = true
                )
            )
        )

        coEvery { cartProductInteractor.getCartProductList() } returns listOf()

        assertEquals(
            expected = listOf(
                getRecommendationProduct(
                    uuid = "3",
                    menuProduct = getMenuProductWithCategory("3", "3"),
                    isVisible = true
                )
            ),
            actual = getRecommendationsUseCase()
        )

    }

    @Test
    fun `return recommendation list when cartProductList has no same category products`() =
        runTest {
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 3,
                recommendationProductList = listOf(
                    getRecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProductWithCategory("2", "1")
                    )
                )
            )

            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1")
            )
            assertEquals(
                expected = listOf(
                    getRecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProductWithCategory("2", "1")
                    )
                ),
                actual = getRecommendationsUseCase()
            )
        }

    @Test
    fun `return empty recommendation list when cartProductList has same category and no other recommendations`() =
        runTest {
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 3,
                recommendationProductList = listOf(
                    getRecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProductWithCategory("1", "1")
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1")
            )
            assertTrue(getRecommendationsUseCase().isEmpty())
        }

    @Test
    fun `return recommendations list when cartProductList has same category but recommendations has different menuProductUuid`() =
        runTest {
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 3,
                recommendationProductList = listOf(
                    getRecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProductWithCategory("1", "1")
                    ),
                    getRecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProductWithCategory("1", "2")
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1")
            )
            assertEquals(
                expected = listOf(
                    getRecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProductWithCategory("1", "2")
                    )
                ),
                actual = getRecommendationsUseCase()
            )
        }

    @Test
    fun `return recommendation list when cartProductList has same category but not same by uuid`() =
        runTest {
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 3,
                recommendationProductList = listOf(
                    getRecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProductWithCategory("1", "1")
                    ),
                    getRecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProductWithCategory("2", "2")
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1")
            )
            assertEquals(
                expected = listOf(
                    getRecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProductWithCategory("2", "2")
                    )
                ),
                actual = getRecommendationsUseCase()
            )
        }

    @Test
    fun `return recommendations size equal maxVisibleCount`() =
        runTest {
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 3,
                recommendationProductList = listOf(
                    getRecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProductWithCategory("1", "1")
                    ),
                    getRecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProductWithCategory("2", "2")
                    ),
                    getRecommendationProduct(
                        uuid = "3",
                        menuProduct = getMenuProductWithCategory("3", "3")
                    ),
                    getRecommendationProduct(
                        uuid = "4",
                        menuProduct = getMenuProductWithCategory("4", "4")
                    ),
                    getRecommendationProduct(
                        uuid = "5",
                        menuProduct = getMenuProductWithCategory("5", "5")
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1")
            )

            assertEquals(
                expected = listOf(
                    getRecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProductWithCategory("2", "2")
                    ),
                    getRecommendationProduct(
                        uuid = "3",
                        menuProduct = getMenuProductWithCategory("3", "3")
                    ),
                    getRecommendationProduct(
                        uuid = "4",
                        menuProduct = getMenuProductWithCategory("4", "4")
                    )
                ),
                actual = getRecommendationsUseCase()
            )
        }

    @Test
    fun `return recommendations when cartProductList has same category but not all`() =
        runTest {
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 4,
                recommendationProductList = listOf(
                    getRecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProductWithCategory("1", "1")
                    ),
                    getRecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProductWithCategory("2", "2")
                    ),
                    getRecommendationProduct(
                        uuid = "3",
                        menuProduct = getMenuProductWithCategory("3", "3")
                    ),
                    getRecommendationProduct(
                        uuid = "4",
                        menuProduct = getMenuProductWithCategory("4", "4")
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1"),
            )

            assertEquals(
                expected = listOf(
                    getRecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProductWithCategory("2", "2")
                    ),
                    getRecommendationProduct(
                        uuid = "3",
                        menuProduct = getMenuProductWithCategory("3", "3")
                    ),
                    getRecommendationProduct(
                        uuid = "4",
                        menuProduct = getMenuProductWithCategory("4", "4")
                    )
                ),
                actual = getRecommendationsUseCase()
            )
        }

    @Test
    fun `return recommendations when cartProducts has same category but not all and many cartProducts`() =
        runTest {
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 4,
                recommendationProductList = listOf(
                    getRecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProductWithCategory("1", "1")
                    ),
                    getRecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProductWithCategory("2", "2")
                    ),
                    getRecommendationProduct(
                        uuid = "3",
                        menuProduct = getMenuProductWithCategory("3", "3")
                    ),
                    getRecommendationProduct(
                        uuid = "4",
                        menuProduct = getMenuProductWithCategory("4", "4")
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProductWithCategory("1", "1"),
                getCartProductWithCategory("2", "2")
            )
            assertEquals(
                expected = listOf(
                    getRecommendationProduct(
                        uuid = "3",
                        menuProduct = getMenuProductWithCategory("3", "3")
                    ),
                    getRecommendationProduct(
                        uuid = "4",
                        menuProduct = getMenuProductWithCategory("4", "4")
                    )
                ),
                actual = getRecommendationsUseCase()
            )
        }

    private fun getCartProductWithCategory(categoryUuid: String, menuProductUuid: String) =
        getCartProduct(
            menuProduct = getMenuProductWithCategory(categoryUuid, menuProductUuid)
        )

    private fun getMenuProductWithCategory(categoryUuid: String, menuProductUuid: String) =
        getMenuProduct(
            uuid = menuProductUuid,
            categoryList = listOf(
                getCategoryProduct(
                    uuid = categoryUuid,
                )
            )
        )


}