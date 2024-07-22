package com.bunbeauty.shared.data

import com.bunbeauty.shared.Constants.DJAN_COMPANY_UUID
import com.bunbeauty.shared.Constants.DJAN_FLAVOR_NAME
import com.bunbeauty.shared.Constants.GUSTO_PUB_COMPANY_UUID
import com.bunbeauty.shared.Constants.GUSTO_PUB_FLAVOR_NAME
import com.bunbeauty.shared.Constants.PAPA_KARLO_COMPANY_UUID
import com.bunbeauty.shared.Constants.PAPA_KARLO_FLAVOR_NAME
import com.bunbeauty.shared.Constants.TANDIR_HOUSE_COMPANY_UUID
import com.bunbeauty.shared.Constants.TANDIR_HOUSE_FLAVOR_NAME
import com.bunbeauty.shared.Constants.TEST_COMPANY_UUID
import com.bunbeauty.shared.Constants.VKUS_KAVKAZA_COMPANY_UUID
import com.bunbeauty.shared.Constants.VKUS_KAVKAZA_FLAVOR_NAME
import com.bunbeauty.shared.Constants.YULIAR_COMPANY_UUID
import com.bunbeauty.shared.Constants.YULIAR_FLAVOR_NAME
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException

class CompanyUuidProvider(
    flavor: String,
    isDebug: Boolean
) {

    val companyUuid: String = if (isDebug) {
        TEST_COMPANY_UUID
    } else {
        when (flavor) {
            PAPA_KARLO_FLAVOR_NAME -> PAPA_KARLO_COMPANY_UUID
            YULIAR_FLAVOR_NAME -> YULIAR_COMPANY_UUID
            DJAN_FLAVOR_NAME -> DJAN_COMPANY_UUID
            GUSTO_PUB_FLAVOR_NAME -> GUSTO_PUB_COMPANY_UUID
            TANDIR_HOUSE_FLAVOR_NAME -> TANDIR_HOUSE_COMPANY_UUID
            VKUS_KAVKAZA_FLAVOR_NAME -> VKUS_KAVKAZA_COMPANY_UUID
            else -> throw UnknownFlavorException()
        }
    }
}
