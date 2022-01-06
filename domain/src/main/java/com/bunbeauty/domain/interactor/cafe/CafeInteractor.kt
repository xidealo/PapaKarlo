package com.bunbeauty.domain.interactor.cafe

import com.bunbeauty.common.Constants.SECONDS_IN_HOUR
import com.bunbeauty.common.Constants.SECONDS_IN_MINUTE
import com.bunbeauty.common.Constants.TIME_DIVIDER
import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.model.cafe.CafeAddress
import com.bunbeauty.domain.model.cafe.CafePreview
import com.bunbeauty.domain.repo.Api
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime
import javax.inject.Inject

class CafeInteractor @Inject constructor(
    @Api private val cafeRepo: CafeRepo,
    private val dataStoreRepo: DataStoreRepo
) : ICafeInteractor {

    override fun observeCafeList(): Flow<List<CafePreview>> {
        return cafeRepo.observeCafeList().map { observedCafeList ->
            observedCafeList.map { cafe ->
                CafePreview(
                    uuid = cafe.uuid,
                    fromTime = getCafeTime(cafe.fromTime),
                    toTime = getCafeTime(cafe.toTime),
                    address = cafe.address,
                    isOpen = isOpen(cafe.fromTime, cafe.toTime),
                    closeIn = getCloseIn(cafe.toTime),
                )
            }
        }
    }

    override fun observeSelectedCafeAddress(): Flow<CafeAddress> {
        return observeCafe().map { cafe ->
            CafeAddress(
                cafeUuid = cafe?.uuid,
                address = cafe?.address ?: ""
            )
        }
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe? {
        return cafeRepo.getCafeByUuid(cafeUuid)
    }

    override suspend fun selectCafe(cafeUuid: String) {
        val userUuid = dataStoreRepo.getUserUuid()
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid()

        if (userUuid != null && selectedCityUuid != null) {
            cafeRepo.saveSelectedCafeUuid(userUuid, selectedCityUuid, cafeUuid)
        }
    }

    fun observeCafe(): Flow<Cafe?> {
        return dataStoreRepo.observeUserAndCityUuid().flatMapLatest { userCityUuid ->
            cafeRepo.observeSelectedCafeByUserAndCityUuid(
                userCityUuid.userUuid,
                userCityUuid.cityUuid
            ).flatMapLatest { cafe ->
                if (cafe == null) {
                    cafeRepo.observeFirstCafeCityUuid(userCityUuid.cityUuid)
                } else {
                    flow {
                        emit(cafe)
                    }
                }
            }
        }
    }

    fun getCafeTime(daySeconds: Int): String {
        val hours = daySeconds / SECONDS_IN_HOUR
        val minutes = (daySeconds % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE
        val minutesString = if (minutes < 10) {
            "0$minutes"
        } else {
            minutes.toString()
        }

        return "$hours$TIME_DIVIDER$minutesString"
    }

    fun isOpen(fromTime: Int, toTime: Int): Boolean {
        val beforeStart = getMinutesFromNowToTime(fromTime)
        val beforeEnd = getMinutesFromNowToTime(toTime)

        return beforeStart < 0 && beforeEnd > 0
    }

    fun getCloseIn(toTime: Int): Int? {
        val beforeEnd = getMinutesFromNowToTime(toTime)

        return if (beforeEnd in 1 until 60) {
            beforeEnd % 60
        } else {
            null
        }
    }

    fun getMinutesFromNowToTime(daySeconds: Int): Int {
        val minutes = daySeconds / SECONDS_IN_MINUTE
        val nowMinutes = DateTime.now().minuteOfDay
        return minutes - nowMinutes
    }
}