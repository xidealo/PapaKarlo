package com.bunbeauty.data.repository

import com.bunbeauty.common.Logger.CAFE_TAG
import com.bunbeauty.data.database.dao.CafeDao
import com.bunbeauty.data.database.entity.cafe.SelectedCafeUuidEntity
import com.bunbeauty.data.handleListResult
import com.bunbeauty.data.mapper.cafe.ICafeMapper
import com.bunbeauty.data.network.api.ApiRepo
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.repo.AuthRepo
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class CafeRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val cafeDao: CafeDao,
    private val cafeMapper: ICafeMapper,
    private val authRepo: AuthRepo,
) : CafeRepo {

    override suspend fun refreshCafeList(selectedCityUuid: String) {
        apiRepo.getCafeListByCityUuid(selectedCityUuid).handleListResult(CAFE_TAG) { cafeList ->
            if (cafeList != null) {
                cafeDao.insertAll(cafeList.map(cafeMapper::toEntityModel))
            }
        }
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
        return cafeDao.getCafeListByCityUuid(selectedCityUuid).map(cafeMapper::toModel)
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe? {
        return cafeDao.getCafeByUuid(cafeUuid)?.let { cafeEntity ->
            cafeMapper.toModel(cafeEntity)
        }
    }

    override fun observeSelectedCafeByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<Cafe?> {
        return cafeDao.observeSelectedCafeByUserAndCityUuid(userUuid, cityUuid)
            .mapFlow(cafeMapper::toModel)
    }

    override fun observeFirstCafeCityUuid(cityUuid: String): Flow<Cafe?> {
        return cafeDao.observeFirstCafeByCityUuid(cityUuid)
            .mapFlow(cafeMapper::toModel)
    }

    override fun observeCafeList(): Flow<List<Cafe>> {
        return dataStoreRepo.selectedCityUuid.flatMapLatest { selectedCityUuid ->
            cafeDao.observeCafeListByCityUuid(selectedCityUuid ?: "")
        }.mapListFlow(cafeMapper::toModel)
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