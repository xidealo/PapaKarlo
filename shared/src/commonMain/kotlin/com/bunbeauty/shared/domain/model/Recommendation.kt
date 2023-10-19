package com.bunbeauty.shared.domain.model

import com.bunbeauty.shared.domain.model.product.MenuProduct

data class Recommendation(
    val uuid: String,
    val menuProduct: MenuProduct,
)
