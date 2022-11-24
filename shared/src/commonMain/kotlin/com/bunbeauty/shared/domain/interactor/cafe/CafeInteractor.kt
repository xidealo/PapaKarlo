package com.bunbeauty.shared.domain.interactor.cafe

import com.bunbeauty.shared.domain.model.cafe.Cafe
import com.bunbeauty.shared.domain.model.cafe.CafeAddress
import com.bunbeauty.shared.domain.model.cafe.CafeStatus
import com.bunbeauty.shared.domain.repo.CafeRepo
import com.bunbeauty.shared.DataStoreRepo
import com.bunbeauty.shared.domain.util.IDateTimeUtil
import com.bunbeauty.shared.Constants.SECONDS_IN_HOUR
import com.bunbeauty.shared.Constants.SECONDS_IN_MINUTE
import com.bunbeauty.shared.Constants.TIME_DIVIDER
import com.bunbeauty.shared.domain.CommonFlow
import com.bunbeauty.shared.domain.asCommonFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CafeInteractor(
    private val cafeRepo: CafeRepo,
    private val dataStoreRepo: DataStoreRepo,
    private val dataTimeUtil: IDateTimeUtil
) : ICafeInteractor {

    override fun observeCafeList(): Flow<List<Cafe>?> {
        return observeMinutesOfDay().map {
            getCafeList()
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

    override suspend fun getCafeList(): List<Cafe>? {
        return dataStoreRepo.getSelectedCityUuid()?.let { selectedCityUuid ->
            cafeRepo.getCafeList(selectedCityUuid).ifEmpty { null }
        }
    }

    override suspend fun getCafeStatus(cafe: Cafe): CafeStatus {
        return getCurrentMinuteOfDay().let { minuteOfDay ->
            if (isClosed(cafe.fromTime, cafe.toTime, minuteOfDay)) {
                CafeStatus.CLOSED
            } else {
                if (isCloseSoon(cafe.toTime, minuteOfDay)) {
                    CafeStatus.CLOSE_SOON
                } else {
                    CafeStatus.OPEN
                }
            }
        }
    }

    override suspend fun isClosed(cafe: Cafe): Boolean {
        return isClosed(cafe.fromTime, cafe.toTime, getCurrentMinuteOfDay())
    }

    override suspend fun getCloseIn(cafe: Cafe): Int? {
        val beforeClose = (cafe.toTime / SECONDS_IN_MINUTE) - getCurrentMinuteOfDay()

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

    suspend fun getCurrentMinuteOfDay(): Int {
        val timeZone = dataStoreRepo.getSelectedCityTimeZone()
        return dataTimeUtil.getCurrentMinuteSecond(timeZone).minuteOfDay
    }

    fun observeMinutesOfDay(): Flow<Int> =
        dataStoreRepo.selectedCityTimeZone.flatMapLatest { timeZone ->
            flow {
                while (true) {
                    val currentMinuteSecond = dataTimeUtil.getCurrentMinuteSecond(timeZone)
                    emit(currentMinuteSecond.minuteOfDay)
                    delay((60 - currentMinuteSecond.secondOfMinute) * 1_000L)
                }
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