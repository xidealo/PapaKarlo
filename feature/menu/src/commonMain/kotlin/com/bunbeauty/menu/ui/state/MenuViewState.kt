package com.bunbeauty.menu.ui.state

import androidx.compose.runtime.Immutable
import com.bunbeauty.core.base.BaseViewState
import com.bunbeauty.core.model.CategoryItem
import com.bunbeauty.core.model.ProductUi
import com.bunbeauty.core.model.order.LightOrder
import com.bunbeauty.designsystem.ui.element.TopCartUi
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class MenuViewState(
    val categoryItemList: ImmutableList<CategoryItem>,
    val topCartUi: TopCartUi,
    val menuItemList: ImmutableList<MenuItemUi>,
    val favoriteProductList: ImmutableList<ProductUi>,
    val hasFavoritesSection: Boolean,
    val state: State,
    val userScrollEnabled: Boolean,
    val lastOrder: LightOrder?,
) : BaseViewState {
    @Immutable
    sealed interface State {
        data object Loading : State

        data object Success : State

        data object Error : State
    }
}

@Immutable
sealed interface MenuItemUi {
    val key: String

    @Immutable
    data class CategoryHeader(
        override val key: String,
        val uuid: String,
        val name: String,
    ) : MenuItemUi

    @Immutable
    data class Product(
        override val key: String,
        val product: ProductUi,
    ) : MenuItemUi

    @Immutable
    data class Discount(
        override val key: String,
        val discount: String,
    ) : MenuItemUi
}
