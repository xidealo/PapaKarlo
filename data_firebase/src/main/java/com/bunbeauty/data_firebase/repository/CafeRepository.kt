package com.bunbeauty.data_firebase.repository

import com.bunbeauty.data_firebase.dao.CafeDao
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.repo.CafeRepo
import com.example.domain_firebase.mapper.ICafeMapper
import com.example.domain_firebase.model.firebase.cafe.CafeFirebase
import com.example.domain_firebase.repo.FirebaseRepo
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CafeRepository @Inject constructor(
    private val cafeDao: CafeDao,
    private val firebaseRepo: FirebaseRepo,
    private val cafeMapper: ICafeMapper,
) : CafeRepo {

    override suspend fun refreshCafeList(selectedCityUuid: String) {
        val cafeWithDistrictsList = firebaseRepo.getCafeList()
            .flowOn(IO)
            .map { cafeFirebaseList: List<CafeFirebase> ->
                cafeFirebaseList.map(cafeMapper::toEntityModel)
            }.flowOn(Default)
            .first()
        cafeDao.refreshCafeList(cafeWithDistrictsList)
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe? {
        return null
    }

    override suspend fun getCafeList(): List<Cafe> {
        return emptyList()
    }

    override suspend fun saveSelectedCafeUuid(
        userUuid: String,
        selectedCityUuid: String,
        cafeUuid: String
    ) {

    }

    override suspend fun observeSelectedCafe(): Flow<Cafe?> {
        return flow {}
    }

    override fun observeCafeList(): Flow<List<Cafe>> {
        return cafeDao.observeCafeList()
            .flowOn(IO)
            .map { cafeEntityList ->
                cafeEntityList.map(cafeMapper::toUIModel)
            }.flowOn(Default)
    }

    override fun observeCafeAddressList(): Flow<List<CafeAddress>> {
        return flow {

        }
        /*cafeDao.observeCafeList()
            .flowOn(IO)
            .map { cafeEntityList ->
                cafeEntityList.map { cafeEntity ->
                    cafeMapper.toUIModel(cafeEntity).cafeAddress
                }
            }.flowOn(Default)*/
    }

    override fun observeCafeAddressByUuid(cafeUuid: String): Flow<CafeAddress?> {
        return flow { }
        /*cafeDao.observeCafeByUuid(cafeUuid)
                .flowOn(IO)
                .map { cafeEntity ->
                    cafeMapper.toUIModel(cafeEntity).cafeAddress
                }.flowOn(Default)*/
    }
}