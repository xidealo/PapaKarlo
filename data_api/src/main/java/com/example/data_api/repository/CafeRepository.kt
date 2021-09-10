package com.example.data_api.repository

import com.bunbeauty.common.ApiResult
import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.example.data_api.dao.CafeDao
import com.example.data_api.mapper.CafeMapper
import com.example.domain_api.mapper.ICafeMapper
import com.example.domain_api.repo.ApiRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CafeRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val cafeDao: CafeDao,
    private val cafeMapper: ICafeMapper
) : CafeRepo {

    override suspend fun refreshCafeList() {
        when (val result =
            apiRepo.getCafeServerByCityList(dataStoreRepo.getSelectedCity() ?: "kimry")) {
            is ApiResult.Success -> {
                if (result.data != null)
                    cafeDao.insertAll(result.data!!.map { cafeMapper.toEntityModel(it)})
            }

            is ApiResult.Error -> {

            }
        }
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe {
        //TODO("Not yet implemented")

        return Any() as Cafe
    }

    override suspend fun getCafeByStreetUuid(streetUuid: String): Cafe {
        //TODO("Not yet implemented")

        return Any() as Cafe
    }

    override fun observeCafeList(): Flow<List<Cafe>> {
        //TODO("Not yet implemented")

        return Any() as Flow<List<Cafe>>
    }

    override fun observeCafeAddressList(): Flow<List<CafeAddress>> {
        //TODO("Not yet implemented")

        return Any() as Flow<List<CafeAddress>>
    }

    override fun observeCafeAddressByUuid(cafeUuid: String): Flow<CafeAddress> {
        //TODO("Not yet implemented")

        return Any() as Flow<CafeAddress>
    }
}