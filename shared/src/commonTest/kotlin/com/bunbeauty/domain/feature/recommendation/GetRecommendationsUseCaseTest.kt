package com.bunbeauty.domain.feature.recommendation

import com.bunbeauty.getCartProduct
import com.bunbeauty.getCategoryProduct
import com.bunbeauty.getMenuProduct
import com.bunbeauty.getRecommendationProductList
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.repository.RecommendationRepository
import com.bunbeauty.shared.domain.exeptions.NoTokenException
import com.bunbeauty.shared.domain.feature.auth.CheckCodeUseCase
import com.bunbeauty.shared.domain.feature.cart.GetRecommendationsUseCase
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.model.RecommendationProduct
import com.bunbeauty.shared.domain.model.RecommendationProductList
import com.bunbeauty.shared.domain.model.cart.CartProduct
import com.bunbeauty.shared.domain.model.category.Category
import com.bunbeauty.shared.domain.model.product.MenuProduct
import com.bunbeauty.shared.domain.repo.AuthRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
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
            getCartProduct(
                uuid = "1",
                count = 1,
                menuProduct = getMenuProduct("1"),
            )
        )
        assertTrue(getRecommendationsUseCase().isEmpty())
    }

    @Test
    fun `return recommendation list when cartProductList has no same category products`() =
        runTest {
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 3,
                recommendationProductList = listOf(
                    RecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "2",
                                )
                            )
                        )
                    )
                )
            )

            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProduct(
                    uuid = "1",
                    count = 1,
                    menuProduct = getMenuProduct(
                        uuid = "1",
                        categoryList = listOf(
                            getCategoryProduct(
                                uuid = "1",
                            )
                        )
                    ),
                )
            )
            assertTrue(getRecommendationsUseCase().isNotEmpty())
        }

    @Test
    fun `return empty recommendation list when cartProductList has same category and no other recommendations`() =
        runTest {
            val categoryList = listOf(
                getCategoryProduct(
                    uuid = "1",
                )
            )
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 3,
                recommendationProductList = listOf(
                    RecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProduct(
                            categoryList = categoryList
                        )
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProduct(
                    uuid = "1",
                    count = 1,
                    menuProduct = getMenuProduct(
                        categoryList = categoryList
                    ),
                )
            )
            assertTrue(getRecommendationsUseCase().isEmpty())
        }

    @Test
    fun `return recommendation list when cartProductList has same category but not same by uuid`() =
        runTest {
            val categoryList = listOf(
                getCategoryProduct(
                    uuid = "1",
                )
            )
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 3,
                recommendationProductList = listOf(
                    RecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProduct(
                            categoryList = categoryList
                        )
                    ),
                    RecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "2",
                                )
                            )
                        )
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProduct(
                    uuid = "1",
                    count = 1,
                    menuProduct = getMenuProduct(
                        categoryList = categoryList
                    ),
                )
            )
            assertTrue(getRecommendationsUseCase().isNotEmpty())
        }

    @Test
    fun `return recommendation list size equal maxVisibleCount`() =
        runTest {
            val categoryList = listOf(
                getCategoryProduct(
                    uuid = "1",
                )
            )
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 3,
                recommendationProductList = listOf(
                    RecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProduct(
                            categoryList = categoryList
                        )
                    ),
                    RecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "2",
                                )
                            )
                        )
                    ),
                    RecommendationProduct(
                        uuid = "3",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "3",
                                )
                            )
                        )
                    ),
                    RecommendationProduct(
                        uuid = "4",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "4",
                                )
                            )
                        )
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProduct(
                    uuid = "1",
                    count = 1,
                    menuProduct = getMenuProduct(
                        categoryList = categoryList
                    ),
                )
            )
            assertTrue(getRecommendationsUseCase().size == 3)
        }

    @Test
    fun `return recommendations when cartProductList has same category but not all`() =
        runTest {
            val categoryList = listOf(
                getCategoryProduct(
                    uuid = "1",
                )
            )
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 4,
                recommendationProductList = listOf(
                    RecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "1",
                                )
                            )
                        )
                    ),
                    RecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "2",
                                )
                            )
                        )
                    ),
                    RecommendationProduct(
                        uuid = "3",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "3",
                                )
                            )
                        )
                    ),
                    RecommendationProduct(
                        uuid = "4",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "4",
                                )
                            )
                        )
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProduct(
                    uuid = "1",
                    count = 1,
                    menuProduct = getMenuProduct(
                        categoryList = listOf(
                            getCategoryProduct(
                                uuid = "1",
                            )
                        )
                    ),
                )
            )
            assertTrue(getRecommendationsUseCase().size == 3)
        }

    @Test
    fun `return recommendations when cartProducts has same category but not all and many cartProducts`() =
        runTest {
            val categoryList = listOf(
                getCategoryProduct(
                    uuid = "1",
                )
            )
            coEvery { recommendationRepository.getRecommendations() } returns getRecommendationProductList(
                maxVisibleCount = 4,
                recommendationProductList = listOf(
                    RecommendationProduct(
                        uuid = "1",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "1",
                                )
                            )
                        )
                    ),
                    RecommendationProduct(
                        uuid = "2",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "2",
                                )
                            )
                        )
                    ),
                    RecommendationProduct(
                        uuid = "3",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "3",
                                )
                            )
                        )
                    ),
                    RecommendationProduct(
                        uuid = "4",
                        menuProduct = getMenuProduct(
                            categoryList = listOf(
                                getCategoryProduct(
                                    uuid = "4",
                                )
                            )
                        )
                    )
                )
            )
            coEvery { cartProductInteractor.getCartProductList() } returns listOf(
                getCartProduct(
                    uuid = "1",
                    count = 1,
                    menuProduct = getMenuProduct(
                        categoryList = listOf(
                            getCategoryProduct(
                                uuid = "1",
                            )
                        )
                    ),
                ),
                getCartProduct(
                    uuid = "1",
                    count = 1,
                    menuProduct = getMenuProduct(
                        categoryList = listOf(
                            getCategoryProduct(
                                uuid = "2",
                            )
                        )
                    ),
                )
            )
            assertTrue(getRecommendationsUseCase().size == 2)
        }
}