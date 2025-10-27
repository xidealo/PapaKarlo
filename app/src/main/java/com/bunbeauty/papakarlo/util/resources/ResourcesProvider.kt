package com.bunbeauty.papakarlo.util.resources

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ResourcesProvider(
    private val context: Context,
) : IResourcesProvider {
    override fun getString(stringId: Int): String = context.resources.getString(stringId)

    override fun getDrawable(drawableId: Int): Drawable? = ContextCompat.getDrawable(context, drawableId)

    override fun getColorById(colorId: Int): Int = ContextCompat.getColor(context, colorId)

    override fun getColorStateListById(colorId: Int): ColorStateList = ColorStateList.valueOf(getColorById(colorId))

    override fun getColorStateListByColor(color: Int): ColorStateList = ColorStateList.valueOf(color)

    override fun getDimensionPixelOffset(dimensionId: Int): Int = context.resources.getDimensionPixelOffset(dimensionId)
}
