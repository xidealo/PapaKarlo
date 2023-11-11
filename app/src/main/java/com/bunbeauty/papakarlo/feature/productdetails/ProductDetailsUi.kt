package com.bunbeauty.papakarlo.feature.productdetails

import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.presentation.base.BaseViewState


sealed interface ProductDetailsUi : BaseViewState {
    data class Success(
        val topCartUi: TopCartUi,
        val menuProductUi: MenuProductUi?,
    ) : ProductDetailsUi {
        data class MenuProductUi(
            val photoLink: String,
            val name: String,
            val size: String,
            val oldPrice: String?,
            val newPrice: String,
            val description: String,
        )
    }

    data object Loading : ProductDetailsUi
    data object Error : ProductDetailsUi
}

