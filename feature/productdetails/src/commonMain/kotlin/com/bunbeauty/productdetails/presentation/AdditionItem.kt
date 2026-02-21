package com.bunbeauty.productdetails.presentation

import com.bunbeauty.core.ItemModel

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
}
