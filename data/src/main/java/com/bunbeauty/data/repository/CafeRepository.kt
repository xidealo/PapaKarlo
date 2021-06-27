package com.bunbeauty.data.repository

import com.bunbeauty.data.dao.CafeDao
import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.repo.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CafeRepository @Inject constructor(
    private val apiRepo: ApiRepo,
    private val cafeDao: CafeDao,
    private val cafeAddressRepo: CafeAddressRepo,
    private val districtRepo: DistrictRepo,
    private val streetRepo: StreetRepo,
    private val dataStoreRepo: DataStoreRepo
) : CafeRepo {

    override val cafeEntityListFlow: Flow<List<Cafe>> = cafeDao.getCafeListFlow()

    override suspend fun refreshCafeList() {
        cafeDao.deleteAll()
        apiRepo.getCafeList().collect { cafeList ->
            for (cafe in cafeList.filter { it.cafeEntity.visible }) {
                saveCafe(cafe)
            }
            if (dataStoreRepo.cafeAddressId.first().isEmpty())
                dataStoreRepo.saveCafeAddressId(cafeList.first().address?.uuid ?: "")
        }
    }

    private suspend fun saveCafe(cafe: Cafe) {
        cafeDao.insert(cafe.cafeEntity)

        cafe.address?.let { address ->
            address.cafeId = cafe.cafeEntity.id
            address.uuid = cafe.cafeEntity.id
            cafeAddressRepo.insert(address)
        }

        for (district in cafe.districts) {
            district.districtEntity.cafeId = cafe.cafeEntity.id
            districtRepo.insert(district.districtEntity)

            for (street in district.streets) {
                street.districtId = district.districtEntity.id
                streetRepo.insert(street)
            }
        }
    }

    override fun getCafeById(cafeId: String) = cafeDao.getCafeById(cafeId)

    override suspend fun getCafeEntityByDistrict(districtId: String) =
        cafeDao.getCafeEntityByDistrict(districtId)
}