package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.model.company.Company
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.core.domain.repo.CompanyRepo

class CompanyRepository(
    private val networkConnector: NetworkConnector,
) : CompanyRepo {
    /**
     * Поле влияет на показ уведомления во всем приложении
     * Пока что решили, что из-за привязке адреса к конкретному кафе
     * показываем уведомление только на экране [OrderDetailsFragment]
     * */
    override suspend fun getCompany(): Company = Company(isOrderAvailable = true)
}
