package com.bunbeauty.presentation.view_model.base.adapter

import com.bunbeauty.domain.model.ui.BaseItem

data class CartProductItem(
    override var uuid: String,
    val name: String,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: Int,
    val menuProductUuid: String
) : BaseItem()
