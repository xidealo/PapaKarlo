package com.bunbeauty.domain.resources

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import javax.inject.Inject

class ResourcesProvider @Inject constructor(private val context: Context): IResourcesProvider {

    override fun getString(stringId: Int): String {
        return context.resources.getString(stringId)
    }

    override fun getDrawable(drawableId: Int): Drawable? {
        return ContextCompat.getDrawable(context, drawableId)
    }

    override fun getColor(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }
}