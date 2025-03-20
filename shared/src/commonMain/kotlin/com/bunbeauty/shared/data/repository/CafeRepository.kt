package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.dao.cafe.ICafeDao
import com.bunbeauty.shared.data.mapper.cafe.toCafe
import com.bunbeauty.shared.data.mapper.cafe.toCafeEntity
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.data.storage.CafeStorage
import com.bunbeauty.shared.db.SelectedCafeUuidEntity
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo

class CafeRepository(
    private val networkConnector: NetworkConnector,
    private val cafeDao: ICafeDao,
    private val cafeStorage: CafeStorage,
    private val dataStoreRepo: DataStoreRepo,
) : CacheListRepository<Cafe>(), CafeRepo {

    override val tag: String = "CAFE_TAG"

    override suspend fun getCafeList(selectedCityUuid: String): List<Cafe> {
        return getCacheOrListData(
            isCacheValid = {
                false
            },
            onApiRequest = {
                networkConnector.getCafeListByCityUuid(selectedCityUuid)
            },
            onLocalRequest = {
                cafeDao.getCafeListByCityUuid(selectedCityUuid)
                    .map { cafeEntity ->
                        cafeEntity.toCafe()
                    }
            },
            onSaveLocally = { cafeServerList ->
                cafeServerList.map { cafeServer ->
                    cafeServer.toCafeEntity()
                }.let { cafeEntityList ->
                    cafeDao.insertCafeList(cafeEntityList)
                }
            },
            serverToDomainModel = { cafeServer ->
                cafeServer.toCafe()
            }
        )
    }

    override suspend fun saveSelectedCafeUuid(
        userUuid: String,
        selectedCityUuid: String,
        cafeUuid: String,
    ) {
        val selectedCafeUuidEntity = SelectedCafeUuidEntity(
            userUuid = userUuid,
            cityUuid = selectedCityUuid,
            cafeUuid = cafeUuid
        )
        cafeDao.insertSelectedCafeUuid(selectedCafeUuidEntity)
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe? {
        return cafeDao.getCafeByUuid(cafeUuid)?.toCafe()
    }

    override suspend fun getUserCafe(): Cafe {
        return getCafeList(
            selectedCityUuid = dataStoreRepo.getSelectedCityUuid().orEmpty()
        ).find { cafe ->
            cafe.uuid == dataStoreRepo.getUserCafeUuid()
        } ?: notSelectedCafeByUserAddress
    }

    override suspend fun getSelectedCafeByUserAndCityUuid(
        userUuid: String,
        cityUuid: String,
    ): Cafe? {
        return cafeDao.getSelectedCafeByUserAndCityUuid(userUuid, cityUuid)?.toCafe()
    }

    override suspend fun getFirstCafeCityUuid(cityUuid: String): Cafe? {
        return cafeDao.getFirstCafeByCityUuid(cityUuid)?.toCafe()
    }

    override fun clearCache() {
        cafeStorage.clear()
    }


    private val notSelectedCafeByUserAddress = Cafe(
        uuid = "",
        fromTime = 0,
        toTime = 0,
        phone = "",
        address = "",
        latitude = 0.0,
        longitude = 0.0,
        cityUuid = "",
        isVisible = false,
        workType = Cafe.WorkType.DELIVERY_AND_PICKUP,
        workload = Cafe.Workload.LOW
    )
}
