package com.bunbeauty.papakarlo.feature.menu

import com.bunbeauty.papakarlo.common.BaseItem

sealed class MenuItem : BaseItem() {

    data class CategorySectionItem(
        override val uuid: String,
        val name: String
    ) : MenuItem()

    data class MenuProductItem(
        override val uuid: String,
        val name: String,
        val newPrice: String,
        val oldPrice: String?,
        val photoLink: String
    ) : MenuItem()

}
