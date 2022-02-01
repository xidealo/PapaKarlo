package com.bunbeauty.papakarlo.feature.consumer_cart

import com.bunbeauty.papakarlo.common.BaseItem

data class CartProductItem(
    override var uuid: String,
    val name: String,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: Int,
    val menuProductUuid: String,
) : BaseItem()
