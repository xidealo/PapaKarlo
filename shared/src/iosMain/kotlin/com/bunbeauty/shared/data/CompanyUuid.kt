package com.bunbeauty.shared.data

import com.bunbeauty.shared.Constants.PAPA_KARLO_COMPANY_UUID
import com.bunbeauty.shared.Constants.PAPA_KARLO_FLAVOR_NAME
import com.bunbeauty.shared.Constants.TEST_COMPANY_UUID
import com.bunbeauty.shared.Constants.YULIAR_COMPANY_UUID
import com.bunbeauty.shared.Constants.YULIAR_FLAVOR_NAME
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException
import platform.Foundation.NSBundle

internal actual val companyUuid: String = if (Platform.isDebugBinary) {
    TEST_COMPANY_UUID
} else {
    when (NSBundle.mainBundle.bundleIdentifier?.split('.')?.lastOrNull()) {
        YULIAR_FLAVOR_NAME.lowercase() -> YULIAR_COMPANY_UUID
        PAPA_KARLO_FLAVOR_NAME.lowercase() -> PAPA_KARLO_COMPANY_UUID
        else -> throw UnknownFlavorException()
    }
}