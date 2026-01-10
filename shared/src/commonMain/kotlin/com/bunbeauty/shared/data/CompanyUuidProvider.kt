package com.bunbeauty.shared.data

import com.bunbeauty.core.Constants.TEST_COMPANY_UUID
import com.bunbeauty.designsystem.FoodDeliveryCompany

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
