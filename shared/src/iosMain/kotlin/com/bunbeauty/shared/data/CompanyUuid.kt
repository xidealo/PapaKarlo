package com.bunbeauty.shared.data

import com.bunbeauty.shared.Constants.DJAN_COMPANY_UUID
import com.bunbeauty.shared.Constants.DJAN_TARGET_NAME
import com.bunbeauty.shared.Constants.PAPA_KARLO_COMPANY_UUID
import com.bunbeauty.shared.Constants.YULIAR_TARGET_NAME
import com.bunbeauty.shared.Constants.TEST_COMPANY_UUID
import com.bunbeauty.shared.Constants.YULIAR_COMPANY_UUID
import com.bunbeauty.shared.Constants.PAPA_KARLO_TARGET_NAME
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException

internal actual val companyUuid: String = if (Platform.isDebugBinary) {
    TEST_COMPANY_UUID
} else {
    when (targetName) {
        YULIAR_TARGET_NAME -> YULIAR_COMPANY_UUID
        PAPA_KARLO_TARGET_NAME -> PAPA_KARLO_COMPANY_UUID
        DJAN_TARGET_NAME -> DJAN_COMPANY_UUID
        else -> throw UnknownFlavorException()
    }
}