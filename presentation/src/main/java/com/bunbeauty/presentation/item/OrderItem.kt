package com.bunbeauty.presentation.item

import com.bunbeauty.domain.model.BaseItem

data class OrderItem(
    override var uuid: String,
    val orderStatus: String,
    val orderColor: Int,
    val code: String,
    val time: String,
    var deferredTime: String
) : BaseItem()