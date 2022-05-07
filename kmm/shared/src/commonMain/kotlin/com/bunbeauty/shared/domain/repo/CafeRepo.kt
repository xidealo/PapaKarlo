package com.bunbeauty.shared.domain.repo

import com.bunbeauty.shared.domain.model.address.CafeAddress
import com.bunbeauty.shared.domain.model.cafe.Cafe
import kotlinx.coroutines.flow.Flow

interface CafeRepo {

    suspend fun getCafeList(selectedCityUuid: String): List<Cafe>
    suspend fun saveSelectedCafeUuid(
        userUuid: String,
        selectedCityUuid: String,
        cafeUuid: String
    )

    suspend fun getCafeList(): List<Cafe>
    suspend fun getCafeByUuid(cafeUuid: String): Cafe?

    fun observeSelectedCafeByUserAndCityUuid(
        userUuid: String,
        cityUuid: String
    ): Flow<Cafe?>

    fun observeFirstCafeCityUuid(cityUuid: String): Flow<Cafe?>
    fun observeCafeList(): Flow<List<Cafe>>
    fun observeCafeAddressList(): Flow<List<CafeAddress>>
    fun observeCafeAddressByUuid(cafeUuid: String): Flow<CafeAddress?>
}
