package com.bunbeauty.papakarlo.util.resources

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable

interface IResourcesProvider {
    fun getString(stringId: Int): String
    fun getDrawable(drawableId: Int): Drawable?
    fun getColorById(colorId: Int): Int
    fun getColorByAttr(attrId: Int): Int
    fun getColorStateListById(colorId: Int): ColorStateList
    fun getColorStateListByAttr(attrId: Int): ColorStateList
    fun getColorStateListByColor(color: Int): ColorStateList
    fun getDimensionPixelOffset(dimensionId: Int): Int
}
