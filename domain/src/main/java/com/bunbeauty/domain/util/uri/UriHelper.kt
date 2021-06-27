package com.bunbeauty.domain.util.uri

import android.net.Uri
import javax.inject.Inject

class UriHelper @Inject constructor(): IUriHelper {

    override fun createUri(label: String, latitude: Double, longitude: Double): Uri {
        val uriBegin = "geo:$latitude,$longitude"
        val query = "$latitude,$longitude($label)"
        val encodedQuery = Uri.encode(query)
        val uriString = "$uriBegin?q=$encodedQuery&z=16"

        return Uri.parse(uriString)
    }
}