package com.bunbeauty.domain.interactor.cafe

import com.bunbeauty.common.Constants.SECONDS_IN_HOUR
import com.bunbeauty.common.Constants.SECONDS_IN_MINUTE
import com.bunbeauty.common.Constants.TIME_DIVIDER
import com.bunbeauty.domain.model.cafe.Cafe
import com.bunbeauty.domain.model.cafe.CafeAddress
import com.bunbeauty.domain.model.cafe.CafePreview
import com.bunbeauty.domain.repo.CafeRepo
import com.bunbeauty.domain.repo.DataStoreRepo
import com.bunbeauty.domain.util.IDateTimeUtil
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

    override fun observeCafeList(): Flow<List<CafePreview>> {
        return cafeRepo.observeCafeList().flatMapLatest { cafeList ->
            observeMinutesOfDay().map { minutesOfDay ->
                cafeList.map { cafe ->
                    CafePreview(
                        uuid = cafe.uuid,
                        fromTime = getCafeTime(cafe.fromTime),
                        toTime = getCafeTime(cafe.toTime),
                        address = cafe.address,
                        isOpen = isOpen(cafe.fromTime, cafe.toTime, minutesOfDay),
                        closeIn = getCloseIn(cafe.toTime, minutesOfDay),
                    )
                }
            }
        }
    }

    override fun observeCafeAddressList(): Flow<List<CafeAddress>> {
        return cafeRepo.observeCafeList().map { cafeList ->
            cafeList.map { cafe ->
                CafeAddress(
                    cafeUuid = cafe.uuid,
                    address = cafe.address
                )
            }
        }
    }

    override fun observeSelectedCafeAddress(): Flow<CafeAddress> {
        return observeCafe().map { cafe ->
            CafeAddress(
                cafeUuid = cafe?.uuid ?: "",
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

    fun isOpen(fromTime: Int, toTime: Int, minutesOfDay: Int): Boolean {
        val beforeStart = getMinutesFromNowToTime(fromTime, minutesOfDay)
        val beforeEnd = getMinutesFromNowToTime(toTime, minutesOfDay)

        return beforeStart < 0 && beforeEnd > 0
    }

    fun getCloseIn(toTime: Int, minutesOfDay: Int): Int? {
        val beforeEnd = getMinutesFromNowToTime(toTime, minutesOfDay)

        return if (beforeEnd in 1 until 60) {
            beforeEnd % 60
        } else {
            null
        }
    }

    fun getMinutesFromNowToTime(daySeconds: Int, minutesOfDay: Int): Int {
        val minutes = daySeconds / SECONDS_IN_MINUTE
        return minutes - minutesOfDay
    }
}