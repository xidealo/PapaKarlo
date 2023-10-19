package com.bunbeauty.shared.domain.feature.cart

import com.bunbeauty.shared.data.repository.RecommendationRepository
import com.bunbeauty.shared.domain.model.product.MenuProduct

class GetRecommendationsUseCase(
    private val recommendationRepository: RecommendationRepository,
) {

    suspend operator fun invoke(): List<MenuProduct> {
        return recommendationRepository.getRecommendations().map {
            it.menuProduct
        }
    }
}