package com.bunbeauty.shared

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

actual class OpenExternalSource(
    val context: Context,
) {
    actual fun openPhone(uri: String) {
        val intent = Intent(Intent.ACTION_DIAL, uri.toUri())
        context.startActivity(intent)
    }

    actual fun openMap(uri: String) {
        openLink(uri = uri)
    }

    actual fun openLink(uri: String) {
        val intent = Intent(Intent.ACTION_VIEW, uri.toUri()).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent)
    }
}