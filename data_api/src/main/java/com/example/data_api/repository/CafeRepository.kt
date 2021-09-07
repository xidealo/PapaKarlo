package com.example.data_api.repository

import com.bunbeauty.domain.model.Cafe
import com.bunbeauty.domain.model.address.CafeAddress
import com.bunbeauty.domain.repo.CafeRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CafeRepository @Inject constructor(): CafeRepo {

    override suspend fun refreshCafeList() {
        //TODO("Not yet implemented")
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