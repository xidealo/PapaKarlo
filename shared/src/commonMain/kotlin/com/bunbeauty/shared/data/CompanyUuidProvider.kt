package com.bunbeauty.shared.data

import com.bunbeauty.core.FoodDeliveryCompany
import com.bunbeauty.shared.Constants.TEST_COMPANY_UUID

class CompanyUuidProvider(
    flavor: String,
    isDebug: Boolean,
) {
    val companyUuid: String =
        if (isDebug) {
            TEST_COMPANY_UUID
        } else {
            FoodDeliveryCompany.getByFlavor(flavor).companyUuid
        }
}
