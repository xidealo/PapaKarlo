package com.bunbeauty.papakarlo.feature.menu.model

import com.bunbeauty.papakarlo.common.model.ItemModel

sealed class MenuItem : ItemModel() {
    data class MenuCategoryHeaderItem(
        override val key: String,
        val uuid: String,
        val name: String
    ) : MenuItem()

    data class MenuProductPairItem(
        override val key: String,
        val firstProduct: MenuProductItem,
        val secondProduct: MenuProductItem?
    ) : MenuItem()
}
