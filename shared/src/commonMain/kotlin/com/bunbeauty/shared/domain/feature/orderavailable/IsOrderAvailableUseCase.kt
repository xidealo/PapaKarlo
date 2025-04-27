package com.bunbeauty.shared.domain.feature.orderavailable

import com.bunbeauty.shared.domain.repo.CompanyRepo

class IsOrderAvailableUseCase(
    private val companyRepo: CompanyRepo
) {
    suspend operator fun invoke(): Boolean {
        return companyRepo.getCompany().isOrderAvailable
    }
}
