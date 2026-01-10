package com.bunbeauty.shared.data

import com.bunbeauty.designsystem.FoodDeliveryCompany
import com.bunbeauty.core.Constants.TEST_COMPANY_UUID

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
