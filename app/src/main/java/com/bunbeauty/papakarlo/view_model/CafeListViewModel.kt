package com.bunbeauty.papakarlo.view_model

import android.os.CountDownTimer
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeRepo
import com.bunbeauty.papakarlo.ui.cafe_list.CafeListNavigator
import com.bunbeauty.papakarlo.utils.resoures.IResourcesProvider
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.lang.ref.WeakReference
import javax.inject.Inject

class CafeListViewModel @Inject constructor(
    private val cafeRepo: CafeRepo,
    private val resourcesProvider: IResourcesProvider,
) : BaseViewModel() {

    init {
        startTimer()
    }

    var navigator: WeakReference<CafeListNavigator>? = null
    val currentTime = ObservableField(DateTime.now())

    val cafeListLiveData by lazy {
        cafeRepo.cafeEntityListLiveData
    }

    fun refreshCafeList() {
        viewModelScope.launch {
            cafeRepo.refreshCafeList()
        }
    }

    private fun startTimer() {
        val toNewMinute = (SECONDS_IN_MINUTE - DateTime.now().secondOfMinute) * MILLIS_IN_SECOND
        object : CountDownTimer(toNewMinute, MILLIS_INTERVAL) {
            override fun onFinish() {
                currentTime.set(DateTime.now())
                startTimer()
            }

            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()
    }

    // Рассчитано на то, что кафе заканчивает работать до 24 ночи
    fun getIsClosedMessage(fromTime: String, toTime: String, now: DateTime): String {
        val beforeStart = getMinutesFromNowToTime(fromTime, now)
        val beforeEnd = getMinutesFromNowToTime(toTime, now)

        return when {
            (beforeStart >= 60) -> {
                "Закрыто. Откроется через ${beforeStart / 60} ч ${beforeStart % 60} мин"
            }
            (beforeStart in 1 until 60) -> {
                "Закрыто. Откроется через ${beforeStart % 60} мин"
            }
            (beforeEnd >= 60) -> {
                "Открыто. Закроется через ${beforeEnd / 60} ч ${beforeEnd % 60} мин"
            }
            (beforeEnd in 1 until 60) -> {
                "Открыто. Закроется через ${beforeEnd % 60} мин"
            }
            else -> {
                "Закрыто. Откроется завтра"
            }
        }
    }

    fun getIsClosedColor(fromTime: String, toTime: String, now: DateTime): Int {
        val beforeStart = getMinutesFromNowToTime(fromTime, now)
        val beforeEnd = getMinutesFromNowToTime(toTime, now)

        return if (beforeStart < 0 && beforeEnd > 0) {
            resourcesProvider.getColor(R.color.light_green)
        } else {
            resourcesProvider.getColor(R.color.light_red)
        }
    }

    private fun getMinutesFromNowToTime(time: String, now: DateTime): Int {
        val hoursMinutes = time
            .split(Regex("\\W"))
            .map { timePart -> timePart.toInt() }
        val minutes = hoursMinutes[0] * MINUTES_IN_HOUR + hoursMinutes[1]

        val nowMinutes = now.hourOfDay * MINUTES_IN_HOUR + now.minuteOfHour
        return minutes - nowMinutes
    }

    fun onCafeCardClick(cafeId: String) {
        navigator?.get()?.goToCafeOptions(cafeId)
    }

    companion object {
        private const val MINUTES_IN_HOUR = 60
        private const val SECONDS_IN_MINUTE = 60
        private const val MILLIS_IN_SECOND = 1000L
        private const val MILLIS_INTERVAL = 1000L
    }
}