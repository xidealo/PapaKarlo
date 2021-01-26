package com.bunbeauty.papakarlo.utils.uri

import android.net.Uri

interface IUriHelper {
    fun createUri(label: String, latitude: Double, longitude: Double): Uri
}