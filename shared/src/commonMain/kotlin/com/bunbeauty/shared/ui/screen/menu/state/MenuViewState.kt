package com.bunbeauty.shared.ui.screen.menu.state

import androidx.compose.runtime.Immutable
import com.bunbeauty.shared.presentation.menu.model.CategoryItem
import com.bunbeauty.shared.presentation.menu.model.MenuDataState
import com.bunbeauty.designsystem.ui.element.TopCartUi
import kotlinx.collections.immutable.ImmutableList

// TODO refactor
@Immutable
data class MenuViewState(
    val categoryItemList: ImmutableList<CategoryItem>,
    val topCartUi: TopCartUi,
    val menuItemList: ImmutableList<MenuItemUi>,
    val state: MenuDataState.State,
    val userScrollEnabled: Boolean,
    val eventList: ImmutableList<MenuDataState.Event>,
)

@Immutable
sealed interface MenuItemUi {
    val key: String

    @Immutable
    data class CategoryHeader(
        override val key: String,
        val name: String,
    ) : MenuItemUi

    @Immutable
    data class Product(
        override val key: String,
        val uuid: String,
        val photoLink: String,
        val name: String,
        val oldPrice: String?,
        val newPrice: String,
    ) : MenuItemUi

    @Immutable
    data class Discount(
        override val key: String,
        val discount: String,
    ) : MenuItemUi
}
