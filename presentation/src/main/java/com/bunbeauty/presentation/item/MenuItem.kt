package com.bunbeauty.presentation.item

import android.graphics.drawable.Drawable
import com.bunbeauty.domain.model.BaseItem
import java.lang.ref.SoftReference

sealed class MenuItem : BaseItem() {

    data class CategorySectionItem(
        override var uuid: String,
        val name: String
    ) : MenuItem()

    data class MenuProductItem(
        override var uuid: String,
        val name: String,
        val newPrice: String,
        val oldPrice: String,
        val photoLink: String
    ) : MenuItem() {
        var photoReference: SoftReference<Drawable?> = SoftReference(null)
    }

}
