package com.bunbeauty.productdetails.ui

import androidx.compose.runtime.Immutable
import com.bunbeauty.core.base.BaseViewState
import com.bunbeauty.designsystem.ui.element.TopCartUi
import com.bunbeauty.productdetails.presentation.AdditionItem
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
            val uuid: String,
        )
    }

    data object Loading : ProductDetailsViewState

    data object Error : ProductDetailsViewState
}
