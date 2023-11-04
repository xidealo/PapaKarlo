package com.bunbeauty.shared.domain.model

import com.bunbeauty.shared.domain.model.product.MenuProduct

data class RecommendationProductList(
    val maxVisibleCount:Int,
    val recommendationProductList: List<RecommendationProduct>
)

data class RecommendationProduct(
    val uuid: String,
    val menuProduct: MenuProduct,
    val isVisible: Boolean,
)

