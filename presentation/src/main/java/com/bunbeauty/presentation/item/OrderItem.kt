package com.bunbeauty.presentation.item

import com.bunbeauty.domain.model.BaseItem

data class OrderItem(
    override var uuid: String,
    val orderStatus: String,
    val orderColorResource: Int,
    val code: String,
    val dateTime: String
) : BaseItem()