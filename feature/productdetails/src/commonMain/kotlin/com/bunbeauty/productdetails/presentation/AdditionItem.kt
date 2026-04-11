package com.bunbeauty.productdetails.presentation

import com.bunbeauty.core.ItemModel
import kotlinx.collections.immutable.ImmutableList

sealed class AdditionItem : ItemModel() {
    data class AdditionHeaderItem(
        override val key: String,
        val uuid: String,
        val name: String,
    ) : AdditionItem()

    data class AdditionListItem(
        override val key: String,
        val product: MenuProductAdditionItem,
        val isMultiple: Boolean,
    ) : AdditionItem()

    /** Horizontal row of card tiles when the group has more than three additions. */
    data class AdditionCardRowItem(
        override val key: String,
        val products: ImmutableList<MenuProductAdditionItem>,
        val isMultiple: Boolean,
    ) : AdditionItem()
}
