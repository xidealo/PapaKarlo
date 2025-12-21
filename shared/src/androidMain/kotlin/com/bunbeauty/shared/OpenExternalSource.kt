package com.bunbeauty.shared

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

actual class OpenExternalSource(
    val context: Context,
) {
    actual fun openPhone(uri: String) {
        openExternalSource(action = Intent.ACTION_DIAL, uri = uri)
    }

    actual fun openMap(uri: String) {
        openExternalSource(action = Intent.ACTION_VIEW, uri = uri)
    }

    actual fun openLink(uri: String) {
        openExternalSource(action = Intent.ACTION_VIEW, uri = uri)
    }

    private fun openExternalSource(
        action: String,
        uri: String,
    ) {
        val intent =
            Intent(action, uri.toUri()).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        context.startActivity(intent)
    }
}
