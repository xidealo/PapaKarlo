package com.bunbeauty.shared.data

import com.bunbeauty.shared.Constants
import com.bunbeauty.shared.domain.exeptions.UnknownFlavorException

internal expect val companyUuid: String
 expect class GetCompanyUuidUseCase {
     fun getCompanyUuid(): String
}