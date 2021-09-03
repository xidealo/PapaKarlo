package com.bunbeauty.presentation.view_model.base.adapter

import android.graphics.drawable.Drawable
import com.bunbeauty.domain.model.ui.BaseItem
import java.lang.ref.SoftReference

data class MenuProductItem(
    override var uuid: String,
    val name: String,
    val newPrice: String,
    val oldPrice: String?,
    val photoLink: String,
    var photoNotWeak: SoftReference<Drawable?> = SoftReference(null)
) : BaseItem()
