package com.bunbeauty.domain.model.adapter

import com.bunbeauty.domain.model.local.BaseModel

data class CartProductAdapterModel(
    override var uuid: String,
    val name: String,
    val cost: String,
    val discountCost: String,
    val photoLink: String,
    val count: Int
) : BaseModel
