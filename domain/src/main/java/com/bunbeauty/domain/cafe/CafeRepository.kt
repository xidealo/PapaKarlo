package com.bunbeauty.domain.cafe

import com.bunbeauty.data.dao.CafeDao
import com.bunbeauty.data.model.cafe.Cafe
import com.bunbeauty.data.api.IApiRepository
import com.bunbeauty.domain.repository.address.CafeAddressRepo
import com.bunbeauty.domain.repository.district.DistrictRepo
import com.bunbeauty.domain.repository.street.StreetRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class CafeRepository @Inject constructor(
    private val apiRepository: IApiRepository,
    private val cafeDao: CafeDao,
    private val cafeAddressRepo: CafeAddressRepo,
    private val districtRepo: DistrictRepo,
    private val streetRepo: StreetRepo
) : CafeRepo {

    override val cafeEntityListFlow: Flow<List<Cafe>> = cafeDao.getCafeListFlow()

    override suspend fun refreshCafeList() {
        cafeDao.deleteAll()
        apiRepository.getCafeList().collect { cafeList ->
            for (cafe in cafeList) {
                saveCafe(cafe)
            }
        }
    }

    suspend fun saveCafe(cafe: Cafe) {
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