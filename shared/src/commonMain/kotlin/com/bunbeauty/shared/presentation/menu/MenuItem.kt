package com.bunbeauty.shared.presentation.menu

import com.bunbeauty.shared.ItemModel

sealed class MenuItem : ItemModel() {
    data class MenuCategoryHeaderItem(
        override val key: String,
        val uuid: String,
        val name: String
    ) : MenuItem()

    data class MenuProductItem(
        override val key: String,
        val product: com.bunbeauty.shared.presentation.menu.MenuProductItem,
    ) : MenuItem()
}
