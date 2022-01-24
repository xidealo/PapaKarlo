package com.bunbeauty.domain.interactor.city

import com.bunbeauty.domain.model.City
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CityRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.worker.ICafeWorkerUtil
import com.bunbeauty.domain.worker.IStreetWorkerUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class CityInteractor @Inject constructor(
    private val dataStoreRepo: DataStoreRepo,
    @Api private val cityRepo: CityRepo,
    private val cafeWorkerUtil: ICafeWorkerUtil,
    private val streetWorkerUtil: IStreetWorkerUtil
) : ICityInteractor {

    override suspend fun checkIsCitySelected(): Boolean {
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid()
        if (selectedCityUuid != null) {
            cafeWorkerUtil.refreshCafeList(selectedCityUuid)
            streetWorkerUtil.refreshStreetList(selectedCityUuid)
        }

        return selectedCityUuid != null
    }

    override suspend fun saveSelectedCity(city: City) {
        dataStoreRepo.saveSelectedCityUuid(city.uuid)
        cafeWorkerUtil.refreshCafeList(city.uuid)
        streetWorkerUtil.refreshStreetList(city.uuid)
    }

    override fun observeCityList(): Flow<List<City>> {
        return cityRepo.observeCityList()
    }

    override fun observeSelectedCity(): Flow<City?> {
        return dataStoreRepo.selectedCityUuid.flatMapLatest { selectedCityUuid ->
            cityRepo.observeCityByUuid(selectedCityUuid ?: "")
        }
    }
}