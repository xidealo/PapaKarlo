package com.bunbeauty.domain.model.adapter

import com.bunbeauty.domain.model.local.BaseModel

data class MenuProductAdapterModel(
    override var uuid: String,
    val name: String,
    val cost: String,
    val discountCost: String,
    val photoLink: String
) : BaseModel
