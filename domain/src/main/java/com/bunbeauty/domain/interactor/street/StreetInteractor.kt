package com.bunbeauty.domain.interactor.street

import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.Street
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.repo.StreetRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class StreetInteractor(
    private val streetRepo: StreetRepo,
    private val dataStoreRepo: DataStoreRepo
) : IStreetInteractor {

    override fun observeStreetList(): Flow<List<Street>> {
        return dataStoreRepo.selectedCityUuid.flatMapLatest { cityUuid ->
            streetRepo.observeStreetListByCityUuid(cityUuid ?: "")
        }
    }

    override fun observeStreetNameList(): Flow<List<String>> {
        return observeStreetList().mapListFlow { street ->
            street.name
        }
    }
}