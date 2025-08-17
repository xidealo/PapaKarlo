package com.bunbeauty.papakarlo.util.resources

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ResourcesProvider(
    private val context: Context
) : IResourcesProvider {

    override fun getString(stringId: Int): String {
        return context.resources.getString(stringId)
    }

    override fun getDrawable(drawableId: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawableId)
    }

    override fun getColorById(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    override fun getColorStateListById(colorId: Int): ColorStateList {
        return ColorStateList.valueOf(getColorById(colorId))
    }

    override fun getColorStateListByColor(color: Int): ColorStateList {
        return ColorStateList.valueOf(color)
    }

    override fun getDimensionPixelOffset(dimensionId: Int): Int {
        return context.resources.getDimensionPixelOffset(dimensionId)
    }
}
