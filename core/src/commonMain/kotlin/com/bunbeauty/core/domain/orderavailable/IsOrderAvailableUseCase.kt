package com.bunbeauty.core.domain.orderavailable

import com.bunbeauty.core.domain.repo.CompanyRepo

class IsOrderAvailableUseCase(
    private val companyRepo: CompanyRepo,
) {
    suspend operator fun invoke(): Boolean = companyRepo.getCompany().isOrderAvailable
}
