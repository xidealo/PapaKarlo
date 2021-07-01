package com.bunbeauty.domain.model.adapter

import com.bunbeauty.domain.model.local.BaseModel

data class CartProductAdapterModel(
    override var uuid: String,
    var name: String,
    var cost: String,
    val discountCost: String,
    var photoLink: String,
    var count: String
) : BaseModel
