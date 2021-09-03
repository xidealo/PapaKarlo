package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.CafeDao
import com.bunbeauty.domain.mapper.ICafeMapper
import com.bunbeauty.domain.model.entity.cafe.CafeEntity
import com.bunbeauty.domain.model.ui.Cafe
import com.bunbeauty.domain.model.ui.address.CafeAddress
import com.bunbeauty.domain.repo.ApiRepo
import com.bunbeauty.domain.repo.CafeRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CafeRepository @Inject constructor(
    private val cafeDao: CafeDao,
    private val apiRepo: ApiRepo,
    private val cafeMapper: ICafeMapper,
) : CafeRepo {

    override suspend fun refreshCafeList() {
        val t = apiRepo.getCafeServerList().first()
        print(t)
 /*       val cafeWithDistrictsList = apiRepo.getCafeList()
            .flowOn(IO)
            .map { cafeFirebaseList ->
                cafeFirebaseList.map(cafeMapper::toEntityModel)
            }.flowOn(Default)
            .first()
        cafeDao.refreshCafeList(cafeWithDistrictsList)*/
    }

    override suspend fun getCafeEntityByUuid(cafeUuid: String): CafeEntity {
        return cafeDao.getCafeByUuid(cafeUuid)
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe {
        return cafeMapper.toUIModel(cafeDao.getCafeByUuid(cafeUuid))
    }

    override suspend fun getCafeByStreetUuid(streetUuid: String): Cafe {
        return withContext(IO) {
            cafeMapper.toUIModel(cafeDao.getCafeByStreetUuid(streetUuid))
        }
    }

    override fun observeCafeList(): Flow<List<Cafe>> {
        return cafeDao.observeCafeList()
            .flowOn(IO)
            .map { cafeEntityList ->
                cafeEntityList.map(cafeMapper::toUIModel)
            }.flowOn(Default)
    }

    override fun observeCafeAddressList(): Flow<List<CafeAddress>> {
        return cafeDao.observeCafeList()
            .flowOn(IO)
            .map { cafeEntityList ->
                cafeEntityList.map { cafeEntity ->
                    cafeMapper.toUIModel(cafeEntity).cafeAddress
                }
            }.flowOn(Default)
    }

    override fun observeCafeAddressByUuid(cafeUuid: String): Flow<CafeAddress> {
        return cafeDao.observeCafeByUuid(cafeUuid)
            .flowOn(IO)
            .map { cafeEntity ->
                cafeMapper.toUIModel(cafeEntity).cafeAddress
            }.flowOn(Default)
    }
}