package com.bunbeauty.domain.model.adapter

import android.graphics.Bitmap
import com.bunbeauty.domain.model.local.BaseModel

data class MenuProductAdapterModel(
    override var uuid: String,
    val name: String,
    val cost: String,
    val discountCost: String,
    val photoLink: String,
    var photo: Bitmap? = null
) : BaseModel
