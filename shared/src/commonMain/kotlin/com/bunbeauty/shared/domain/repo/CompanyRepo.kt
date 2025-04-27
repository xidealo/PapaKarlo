package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.company.Company

interface CompanyRepo {
    suspend fun getCompany(): Company
}
