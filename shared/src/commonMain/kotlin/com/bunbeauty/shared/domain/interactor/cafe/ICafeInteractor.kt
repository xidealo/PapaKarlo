package com.bunbeauty.shared.domain.interactor.cafe

import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.CafeAddress
import com.bunbeauty.shared.domain.model.cafe.CafeStatus
import kotlinx.coroutines.flow.Flow

interface ICafeInteractor {

    fun observeCafeList(timeZone: String): Flow<List<Cafe>?>
    fun observeCafeAddressList(): CommonFlow<List<CafeAddress>>
    fun observeSelectedCafeAddress(): CommonFlow<CafeAddress>
    suspend fun getCafeList(): List<Cafe>?
    suspend fun getCafeStatus(cafe: Cafe, timeZone: String): CafeStatus
    suspend fun isClosed(cafe: Cafe, timeZone: String): Boolean
    suspend fun getCloseIn(cafe: Cafe, timeZone: String): Int?
    fun getCafeTime(daySeconds: Int): String
    suspend fun getCafeByUuid(cafeUuid: String): Cafe?
    suspend fun saveSelectedCafe(cafeUuid: String)
}