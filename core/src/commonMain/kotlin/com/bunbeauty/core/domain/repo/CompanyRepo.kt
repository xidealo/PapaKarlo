package com.bunbeauty.core.domain.repo

import com.bunbeauty.core.model.company.Company

interface CompanyRepo {
    suspend fun getCompany(): Company
}
