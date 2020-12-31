package com.bunbeauty.papakarlo.utils.resoures

import android.content.Context
import javax.inject.Inject

class ResourcesProvider @Inject constructor(private val context: Context): IResourcesProvider {

    override fun getString(stringId: Int): String {
        return context.resources.getString(stringId)
    }
}