package com.bunbeauty.shared.domain.repo

import com.bunbeauty.core.model.company.Company

interface CompanyRepo {
    suspend fun getCompany(): Company
}
