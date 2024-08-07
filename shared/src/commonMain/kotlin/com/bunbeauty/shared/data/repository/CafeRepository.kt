package com.bunbeauty.shared.data.repository

import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.data.dao.cafe.ICafeDao
import com.bunbeauty.shared.data.mapper.cafe.toCafe
import com.bunbeauty.shared.data.mapper.cafe.toCafeAddress
import com.bunbeauty.shared.data.mapper.cafe.toCafeEntity
import com.bunbeauty.shared.data.network.api.NetworkConnector
import com.bunbeauty.shared.db.SelectedCafeUuidEntity
import com.bunbeauty.shared.domain.mapFlow
import com.bunbeauty.shared.domain.mapListFlow
import com.bunbeauty.shared.domain.model.address.CafeAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.repo.CafeRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class CafeRepository(
    private val networkConnector: NetworkConnector,
    private val dataStoreRepo: DataStoreRepo,
    private val cafeDao: ICafeDao
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
        cafeUuid: String
    ) {
        val selectedCafeUuidEntity = SelectedCafeUuidEntity(
            userUuid = userUuid,
            cityUuid = selectedCityUuid,
            cafeUuid = cafeUuid
        )
        cafeDao.insertSelectedCafeUuid(selectedCafeUuidEntity)
    }

    override suspend fun getCafeList(): List<Cafe> {
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid() ?: ""
        return cafeDao.getCafeListByCityUuid(selectedCityUuid)
            .map { cafeEntity ->
                cafeEntity.toCafe()
            }
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe? {
        return cafeDao.getCafeByUuid(cafeUuid)?.toCafe()
    }

    override suspend fun getSelectedCafeByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Cafe? {
        return cafeDao.getSelectedCafeByUserAndCityUuid(userUuid, cityUuid)?.toCafe()
    }

    override suspend fun getFirstCafeCityUuid(cityUuid: String): Cafe? {
        return cafeDao.getFirstCafeByCityUuid(cityUuid)?.toCafe()
    }

    override fun observeSelectedCafeByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<Cafe?> {
        return cafeDao.observeSelectedCafeByUserAndCityUuid(userUuid, cityUuid)
            .mapFlow { cafeEntity ->
                cafeEntity.toCafe()
            }
    }

    override fun observeFirstCafeCityUuid(cityUuid: String): Flow<Cafe?> {
        return cafeDao.observeFirstCafeByCityUuid(cityUuid)
            .mapFlow { cafeEntity ->
                cafeEntity.toCafe()
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeCafeList(): Flow<List<Cafe>> {
        return dataStoreRepo.selectedCityUuid.flatMapLatest { selectedCityUuid ->
            cafeDao.observeCafeListByCityUuid(selectedCityUuid ?: "")
        }.mapListFlow { cafeEntity ->
            cafeEntity.toCafe()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeCafeAddressList(): Flow<List<CafeAddress>> {
        return dataStoreRepo.selectedCityUuid.flatMapLatest { selectedCityUuid ->
            cafeDao.observeCafeListByCityUuid(selectedCityUuid ?: "")
        }.mapListFlow { cafeEntity ->
            cafeEntity.toCafeAddress()
        }
    }

    override fun observeCafeAddressByUuid(cafeUuid: String): Flow<CafeAddress?> {
        return cafeDao.observeCafeByUuid(cafeUuid).mapFlow { cafeEntity ->
            cafeEntity.toCafeAddress()
        }
    }
}
