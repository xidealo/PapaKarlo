package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.data.dao.cafe.ICafeDao
import com.bunbeauty.shared.data.mapper.cafe.ICafeMapper
import com.bunbeauty.shared.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.shared.db.SelectedCafeUuidEntity
import com.bunbeauty.shared.domain.model.address.CafeAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo
import com.bunbeauty.shared.DataStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class CafeRepository(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val cafeDao: ICafeDao,
    private val cafeMapper: ICafeMapper,
) : CacheListRepository<Cafe>(), CafeRepo {

    override val tag: String = "CAFE_TAG"

    override suspend fun getCafeList(selectedCityUuid: String): List<Cafe> {
        return getCacheOrListData(
            isCacheValid = { cacheList ->
                cacheList.all { cafe ->
                    cafe.cityUuid == selectedCityUuid
                }
            },
            onApiRequest = {
                apiRepo.getCafeListByCityUuid(selectedCityUuid)
            },
            onLocalRequest = {
                cafeDao.getCafeListByCityUuid(selectedCityUuid).map(cafeMapper::toCafe)
            },
            onSaveLocally = { cafeServerList ->
                cafeDao.insertCafeList(cafeServerList.map(cafeMapper::toCafeEntity))
            },
            serverToDomainModel = cafeMapper::toCafe
        )
    }

    override suspend fun saveSelectedCafeUuid(
        userUuid: String,
        selectedCityUuid: String,
        cafeUuid: String
    ) {
        val selectedCafeUuidEntity = SelectedCafeUuidEntity(
            userUuid = userUuid,
            cityUuid = selectedCityUuid,
            cafeUuid = cafeUuid,
        )
        cafeDao.insertSelectedCafeUuid(selectedCafeUuidEntity)
    }

    override suspend fun getCafeList(): List<Cafe> {
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid() ?: ""
        return cafeDao.getCafeListByCityUuid(selectedCityUuid).map(cafeMapper::toCafe)
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe? {
        return cafeDao.getCafeByUuid(cafeUuid)?.let { cafeEntity ->
            cafeMapper.toCafe(cafeEntity)
        }
    }

    override fun observeSelectedCafeByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<Cafe?> {
        return cafeDao.observeSelectedCafeByUserAndCityUuid(userUuid, cityUuid)
            .mapFlow(cafeMapper::toCafe)
    }

    override fun observeFirstCafeCityUuid(cityUuid: String): Flow<Cafe?> {
        return cafeDao.observeFirstCafeByCityUuid(cityUuid)
            .mapFlow(cafeMapper::toCafe)
    }

    override fun observeCafeList(): Flow<List<Cafe>> {
        return dataStoreRepo.selectedCityUuid.flatMapLatest { selectedCityUuid ->
            cafeDao.observeCafeListByCityUuid(selectedCityUuid ?: "")
        }.mapListFlow(cafeMapper::toCafe)
    }

    override fun observeCafeAddressList(): Flow<List<CafeAddress>> {
        return dataStoreRepo.selectedCityUuid.flatMapLatest { selectedCityUuid ->
            cafeDao.observeCafeListByCityUuid(selectedCityUuid ?: "")
        }.mapListFlow(cafeMapper::toCafeAddress)
    }

    override fun observeCafeAddressByUuid(cafeUuid: String): Flow<CafeAddress?> {
        return cafeDao.observeCafeByUuid(cafeUuid).mapFlow(cafeMapper::toCafeAddress)
    }
}