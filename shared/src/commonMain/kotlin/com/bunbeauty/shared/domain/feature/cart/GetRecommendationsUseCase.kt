package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.data.repository.RecommendationRepository
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.model.RecommendationProduct
import com.bunbeauty.shared.domain.model.RecommendationProductList
import com.bunbeauty.shared.domain.model.category.Category

class GetRecommendationsUseCase(
    private val recommendationRepository: RecommendationRepository,
    private val cartProductInteractor: ICartProductInteractor,
) {
    suspend operator fun invoke(): List<RecommendationProduct> {

        val cartProductList = cartProductInteractor.getCartProductList()
        val recommendation = recommendationRepository.getRecommendations()

        val recommendationProductList =
            recommendation?.recommendationProductList ?: return emptyList()

        val recommendationProductListNoneCategory = buildList {
            recommendationProductList.forEach { recommendationProduct ->
                val hasSameCategory =
                    recommendationProduct.menuProduct.categoryList.any { recommendationProductCategory ->
                        cartProductList
                            .any { cartProduct ->
                                cartProduct.product.categoryList
                                    .any { cartProductCategory -> cartProductCategory.uuid == recommendationProductCategory.uuid }
                            }
                    }

                if (!hasSameCategory) {
                    add(recommendationProduct)
                }
            }
        }

        return if (recommendationProductListNoneCategory.size < 2) {
            buildList {
                addAll(recommendationProductListNoneCategory)
                recommendationProductList.forEach { recommendationProduct ->
                    if (cartProductList.none { it.product.uuid == recommendationProduct.menuProduct.uuid }) {
                        add(recommendationProduct)
                    }
                }
            }.take(recommendation.maxVisibleCount)
        } else {
            recommendationProductListNoneCategory.take(recommendation.maxVisibleCount)
        }
    }
}