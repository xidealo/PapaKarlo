package com.example.data_api.repository

import com.bunbeauty.common.ApiResult
import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.example.data_api.dao.CafeDao
import com.example.data_api.mapFlow
import com.example.data_api.mapListFlow
import com.example.domain_api.mapper.ICafeMapper
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class CafeRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val cafeDao: CafeDao,
    private val cafeMapper: ICafeMapper
) : CafeRepo {

    override suspend fun refreshCafeList() {
        val selectedCity = dataStoreRepo.getSelectedCityUuid()
        if (selectedCity != null) {
            when (val result = apiRepo.getCafeListByCityUuid(selectedCity)) {
                is ApiResult.Success -> {
                    if (result.data != null)
                        cafeDao.insertAll(result.data!!.map { cafeMapper.toEntityModel(it) })
                }

                is ApiResult.Error -> {
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