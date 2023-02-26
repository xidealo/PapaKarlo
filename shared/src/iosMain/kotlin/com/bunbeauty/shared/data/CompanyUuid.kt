package com.bunbeauty.shared.data

internal actual val companyUuid: String = if (Platform.isDebugBinary) {
    "fd483dcb-3f44-457f-b4d4-f82d2aa83b46"
} else {
    "7416dba5-2825-4fe3-abfb-1494a5e2bf99"
}