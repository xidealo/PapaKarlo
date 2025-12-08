package com.bunbeauty.shared.data

import com.bunbeauty.shared.Constants.TEST_COMPANY_UUID
import com.bunbeauty.core.FoodDeliveryCompany

class CompanyUuidProvider(
    flavor: String,
    isDebug: Boolean
) {
    val companyUuid: String = if (isDebug) {
        TEST_COMPANY_UUID
    } else {
        FoodDeliveryCompany.getByFlavor(flavor).companyUuid
    }
}
