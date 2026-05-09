package com.bunbeauty.shared.data.repository

import com.bunbeauty.core.domain.repo.CreateOrderSettingsRepo
import com.bunbeauty.shared.DataStoreRepo

class CreateOrderSettingsRepository(
    private val dataStoreRepo: DataStoreRepo,
) : CreateOrderSettingsRepo {
    override suspend fun getWithoutUtensils(): Boolean =
        dataStoreRepo.getWithoutUtensils() ?: false

    override suspend fun saveWithoutUtensils(withoutUtensils: Boolean) {
        dataStoreRepo.saveWithoutUtensils(withoutUtensils = withoutUtensils)
    }

    override suspend fun clearWithoutUtensils() {
        dataStoreRepo.clearWithoutUtensils()
    }
}
