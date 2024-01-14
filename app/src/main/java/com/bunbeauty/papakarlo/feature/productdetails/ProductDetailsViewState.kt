package com.bunbeauty.papakarlo.feature.productdetails

import com.bunbeauty.papakarlo.feature.topcart.TopCartUi
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.bunbeauty.shared.presentation.product_details.AdditionItem

sealed interface ProductDetailsViewState : BaseViewState {
    data class Success(
        val topCartUi: TopCartUi,
        val menuProductUi: MenuProductUi
    ) : ProductDetailsViewState {
        data class MenuProductUi(
            val photoLink: String,
            val name: String,
            val size: String,
            val oldPrice: String?,
            val newPrice: String,
            val priceWithAdditions: String,
            val description: String,
            val additionList: List<AdditionItem>
        )
    }

    data object Loading : ProductDetailsViewState
    data object Error : ProductDetailsViewState
}
