package com.bunbeauty.domain.util.resources

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable

interface IResourcesProvider {
    fun getString(stringId: Int): String
    fun getDrawable(drawableId: Int): Drawable?
    fun getColor(colorId: Int): Int
    fun getColorTint(colorId: Int): ColorStateList
}