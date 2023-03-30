package com.bunbeauty.shared.data

import com.bunbeauty.shared.BuildConfig
import com.bunbeauty.shared.data.CompanyConstants.PAPA_KARLO_COMPANY_UUID
import com.bunbeauty.shared.data.CompanyConstants.PAPA_KARLO_FLAVOR_NAME
import com.bunbeauty.shared.data.CompanyConstants.TEST_COMPANY_UUID
import com.bunbeauty.shared.data.CompanyConstants.YULIAR_COMPANY_UUID
import com.bunbeauty.shared.data.CompanyConstants.YULIAR_FLAVOR_NAME
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException

internal actual val companyUuid: String = if (BuildConfig.DEBUG) {
    TEST_COMPANY_UUID
} else {
    when (BuildConfig.FLAVOR) {
        PAPA_KARLO_FLAVOR_NAME -> PAPA_KARLO_COMPANY_UUID
        YULIAR_FLAVOR_NAME -> YULIAR_COMPANY_UUID
        else -> throw UnknownFlavorException()
    }
}
