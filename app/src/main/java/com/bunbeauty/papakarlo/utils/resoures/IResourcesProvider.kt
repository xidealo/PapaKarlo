package com.bunbeauty.papakarlo.utils.resoures

import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable

interface IResourcesProvider {

    fun getString(stringId: Int): String
    fun getDrawable(drawableId: Int): Drawable?
    fun getColor(colorId: Int): Int
}