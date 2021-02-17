package com.example.data_api.repository

import com.bunbeauty.common.Logger.CAFE_TAG
import com.bunbeauty.domain.mapFlow
import com.bunbeauty.domain.mapListFlow
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.repo.AuthRepo
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.example.data_api.dao.CafeDao
import com.example.data_api.handleListResult
import com.example.domain_api.mapper.ICafeMapper
import com.example.domain_api.model.entity.cafe.SelectedCafeUuidEntity
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
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

    override suspend fun observeSelectedCafe(): Flow<Cafe?> {
        val userUuid = authRepo.firebaseUserUuid ?: ""
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid() ?: ""

        return cafeDao.observeSelectedCafeByUserAndCityUuid(userUuid, selectedCityUuid)
            .flatMapLatest { selectedCafe ->
                cafeDao.observeFirstCafeByCityUuid(selectedCityUuid).map { firstCafe ->
                    (selectedCafe ?: firstCafe)?.let { cafe ->
                        cafeMapper.toModel(cafe)
                    }
                }
            }
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