package com.bunbeauty.shared.data

import com.bunbeauty.shared.BuildConfig
import com.bunbeauty.shared.Constants.DJAN_COMPANY_UUID
import com.bunbeauty.shared.Constants.DJAN_FLAVOR_NAME
import com.bunbeauty.shared.Constants.PAPA_KARLO_COMPANY_UUID
import com.bunbeauty.shared.Constants.PAPA_KARLO_FLAVOR_NAME
import com.bunbeauty.shared.Constants.TEST_COMPANY_UUID
import com.bunbeauty.shared.Constants.YULIAR_COMPANY_UUID
import com.bunbeauty.shared.Constants.YULIAR_FLAVOR_NAME
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException

internal actual val companyUuid: String = if (BuildConfig.DEBUG) {
    TEST_COMPANY_UUID
} else {
    when (BuildConfig.FLAVOR) {
        PAPA_KARLO_FLAVOR_NAME -> PAPA_KARLO_COMPANY_UUID
        YULIAR_FLAVOR_NAME -> YULIAR_COMPANY_UUID
        DJAN_FLAVOR_NAME -> DJAN_COMPANY_UUID
        else -> throw UnknownFlavorException()
    }
}