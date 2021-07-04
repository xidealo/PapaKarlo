package com.bunbeauty.domain.model.adapter

import android.graphics.Bitmap
import com.bunbeauty.domain.model.local.BaseModel
import java.lang.ref.SoftReference

data class MenuProductAdapterModel(
    override var uuid: String,
    val name: String,
    val cost: String,
    val discountCost: String,
    val photoLink: String,
    var photo: SoftReference<Bitmap?> = SoftReference(null)
) : BaseModel
