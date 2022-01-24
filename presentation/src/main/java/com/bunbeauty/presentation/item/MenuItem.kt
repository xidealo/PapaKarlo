package com.bunbeauty.presentation.item

import com.bunbeauty.domain.model.BaseItem

sealed class MenuItem : BaseItem() {

    data class CategorySectionItem(
        override var uuid: String,
        val name: String
    ) : MenuItem()

    data class MenuProductItem(
        override var uuid: String,
        val name: String,
        val newPrice: String,
        val oldPrice: String,
        val photoLink: String
    ) : MenuItem()

}
