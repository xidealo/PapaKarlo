package com.bunbeauty.presentation.item

import com.bunbeauty.domain.model.BaseItem

class OrderProductItem(
    override var uuid: String,
    val name: String,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: String
) : BaseItem()