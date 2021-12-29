package com.bunbeauty.presentation.item

import com.bunbeauty.domain.model.BaseItem

data class CategoryItem(
    override var uuid: String,
    val name: String
) : BaseItem()