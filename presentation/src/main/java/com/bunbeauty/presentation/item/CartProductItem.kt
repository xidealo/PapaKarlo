package com.bunbeauty.presentation.item

import android.graphics.drawable.Drawable
import com.bunbeauty.domain.model.BaseItem
import java.lang.ref.SoftReference

data class CartProductItem(
    override var uuid: String,
    val name: String,
    val newCost: String,
    val oldCost: String?,
    val photoLink: String,
    val count: Int,
    val menuProductUuid: String,
) : BaseItem() {
    var photoReference: SoftReference<Drawable?> = SoftReference(null)
}
