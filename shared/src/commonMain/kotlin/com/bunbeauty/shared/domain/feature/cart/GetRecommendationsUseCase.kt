package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.data.repository.RecommendationRepository
import com.bunbeauty.shared.domain.interactor.cart.ICartProductInteractor
import com.bunbeauty.shared.domain.model.RecommendationProduct
import com.bunbeauty.shared.domain.model.RecommendationProductList

class GetRecommendationsUseCase(
    private val recommendationRepository: RecommendationRepository,
    private val cartProductInteractor: ICartProductInteractor,
) {
    suspend operator fun invoke(): List<RecommendationProduct> {

        val cartProductList = cartProductInteractor.getCartProductList()

        val recommendationProductList =
            recommendationRepository.getRecommendations()?.recommendationProductList
                ?: emptyList()

        return buildList {
            recommendationProductList.forEach { recommendationProduct ->
                if (cartProductList.none { it.product.categoryList.firstOrNull()?.uuid == recommendationProduct.menuProduct.categoryList.firstOrNull()?.uuid }) {
                    add(recommendationProduct)
                }

            }
        }
    }
}