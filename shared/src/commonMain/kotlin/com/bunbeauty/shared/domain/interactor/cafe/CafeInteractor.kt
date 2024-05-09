package com.bunbeauty.shared.domain.interactor.cafe

import com.bunbeauty.shared.Constants.SECONDS_IN_HOUR
import com.bunbeauty.shared.Constants.SECONDS_IN_MINUTE
import com.bunbeauty.shared.Constants.TIME_DIVIDER
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.CafeAddress
import com.bunbeauty.shared.domain.repo.CafeRepo
import com.bunbeauty.shared.domain.util.IDateTimeUtil
import com.bunbeauty.shared.presentation.cafe_list.CafeItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CafeInteractor(
    private val cafeRepo: CafeRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val dataTimeUtil: IDateTimeUtil,
) : ICafeInteractor {

    override fun observeCafeList(timeZone: String): Flow<List<Cafe>> {
        return observeMinutesOfDay(timeZone).map {
            dataStoreRepo.getSelectedCityUuid()?.let { selectedCityUuid ->
                cafeRepo.getCafeList(selectedCityUuid)
            } ?: emptyList()
        }
    }

    override fun observeCafeAddressList(): CommonFlow<List<CafeAddress>> {
        return cafeRepo.observeCafeList().map { cafeList ->
            cafeList.map { cafe ->
                CafeAddress(
                    cafeUuid = cafe.uuid,
                    address = cafe.address
                )
            }
        }.asCommonFlow()
    }

    override fun observeSelectedCafeAddress(): CommonFlow<CafeAddress> {
        return observeCafe().map { cafe ->
            CafeAddress(
                cafeUuid = cafe?.uuid ?: "",
                address = cafe?.address ?: ""
            )
        }.asCommonFlow()
    }

    override suspend fun getCafeStatus(cafe: Cafe, timeZone: String): CafeItem.CafeOpenState {
        return getCurrentMinuteOfDay(timeZone).let { minuteOfDay ->
            if (isClosed(cafe.fromTime, cafe.toTime, minuteOfDay)) {
                CafeItem.CafeOpenState.Closed
            } else {
                if (isCloseSoon(cafe.toTime, minuteOfDay)) {
                    CafeItem.CafeOpenState.CloseSoon(
                        time = getCloseIn(cafe, timeZone) ?: 0
                    )
                } else {
                    CafeItem.CafeOpenState.Opened
                }
            }
        }
    }

    override suspend fun isClosed(cafe: Cafe, timeZone: String): Boolean {
        return isClosed(cafe.fromTime, cafe.toTime, getCurrentMinuteOfDay(timeZone))
    }

    override suspend fun getCloseIn(cafe: Cafe, timeZone: String): Int? {
        val beforeClose = (cafe.toTime / SECONDS_IN_MINUTE) - getCurrentMinuteOfDay(timeZone)

        return if (beforeClose in 1 until 60) {
            beforeClose % 60
        } else {
            null
        }
    }

    override suspend fun getCafeByUuid(cafeUuid: String): Cafe? {
        return cafeRepo.getCafeByUuid(cafeUuid)
    }

    override suspend fun saveSelectedCafe(cafeUuid: String) {
        val userUuid = dataStoreRepo.getUserUuid()
        val selectedCityUuid = dataStoreRepo.getSelectedCityUuid()

        if (userUuid != null && selectedCityUuid != null) {
            cafeRepo.saveSelectedCafeUuid(userUuid, selectedCityUuid, cafeUuid)
        }
    }

    private fun getCurrentMinuteOfDay(timeZone: String): Int {
        return dataTimeUtil.getCurrentMinuteSecond(timeZone).minuteOfDay
    }

    private fun observeMinutesOfDay(timeZone: String): Flow<Int> = flow {
        while (true) {
            val currentMinuteSecond = dataTimeUtil.getCurrentMinuteSecond(timeZone)
            emit(currentMinuteSecond.minuteOfDay)
            delay((60 - currentMinuteSecond.secondOfMinute) * 1_000L)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeCafe(): Flow<Cafe?> {
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

    override fun getCafeTime(daySeconds: Int): String {
        val hours = daySeconds / SECONDS_IN_HOUR
        val minutes = (daySeconds % SECONDS_IN_HOUR) / SECONDS_IN_MINUTE
        val minutesString = if (minutes < 10) {
            "0$minutes"
        } else {
            minutes.toString()
        }

        return "$hours$TIME_DIVIDER$minutesString"
    }

    fun isClosed(fromTime: Int, toTime: Int, minutesOfDay: Int): Boolean {
        return (minutesOfDay < fromTime / SECONDS_IN_MINUTE)
                || (minutesOfDay > toTime / SECONDS_IN_MINUTE)
    }

    fun isCloseSoon(toTime: Int, minutesOfDay: Int): Boolean {
        val beforeClose = (toTime / SECONDS_IN_MINUTE) - minutesOfDay

        return beforeClose in 1 until 60
    }
}