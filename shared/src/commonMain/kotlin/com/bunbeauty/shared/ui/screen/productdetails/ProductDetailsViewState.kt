package com.bunbeauty.shared.ui.screen.productdetails

import androidx.compose.runtime.Immutable
import com.bunbeauty.designsystem.ui.element.TopCartUi
import com.bunbeauty.shared.presentation.base.BaseViewState
import com.bunbeauty.shared.presentation.product_details.AdditionItem
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface ProductDetailsViewState : BaseViewState {
    @Immutable
    data class Success(
        val topCartUi: TopCartUi,
        val menuProductUi: MenuProductUi,
    ) : ProductDetailsViewState {
        @Immutable
        data class MenuProductUi(
            val photoLink: String,
            val name: String,
            val size: String,
            val oldPrice: String?,
            val newPrice: String,
            val priceWithAdditions: String,
            val description: String,
            val additionList: ImmutableList<AdditionItem>,
        )
    }

    data object Loading : ProductDetailsViewState

    data object Error : ProductDetailsViewState
}
