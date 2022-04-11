package com.bunbeauty.papakarlo.feature.menu.view_state

import com.bunbeauty.papakarlo.common.ItemModel

sealed class MenuItemModel: ItemModel() {
    data class MenuCategoryHeaderItemModel(
        override val key: String,
        val uuid: String,
        val name: String
    ) : MenuItemModel()

    data class MenuProductPairItemModel(
        override val key: String,
        val firstProduct: MenuProductItemModel,
        val secondProduct: MenuProductItemModel?
    ) : MenuItemModel()
}
