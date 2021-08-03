package com.bunbeauty.presentation.view_model.base.adapter

import android.graphics.drawable.Drawable
import com.bunbeauty.domain.model.local.BaseModel
import java.lang.ref.SoftReference

data class MenuProductItem(
    override var uuid: String,
    val name: String,
    val cost: String,
    val discountCost: String,
    val photoLink: String,
    var photoNotWeak: SoftReference<Drawable?> = SoftReference(null)
) : BaseModel
