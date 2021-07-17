package com.bunbeauty.domain.util.uri

import android.net.Uri

interface IUriHelper {
    fun createUri(label: String, latitude: Double, longitude: Double): Uri
}