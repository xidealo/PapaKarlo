package com.bunbeauty.papakarlo.utils

import android.content.Context
import javax.inject.Inject

class ResourcesProvider @Inject constructor(private val context: Context) {

    fun getString(stringId: Int): String {
        return context.resources.getString(stringId)
    }
}