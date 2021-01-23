package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.bunbeauty.papakarlo.data.local.db.cafe.CafeRepo
import com.bunbeauty.papakarlo.ui.contacts.ContactsNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val cafeRepo: CafeRepo
) : BaseViewModel() {

    var navigator: WeakReference<ContactsNavigator>? = null
    val isClickable = ObservableField(false)

    val cafeListLiveData by lazy {
        cafeRepo.cafeEntityListLiveData
    }

    fun refreshCafeList() {
        viewModelScope.launch {
            cafeRepo.refreshCafeList()
        }
    }

    // Рассчитано на то, что кафе заканчивает работать до 24 ночи
    fun getIsClosedMessage(): String {
        return ""
        /*val startHoursMinutes = contactInfoLiveData.value!!
            .startTime
            .split(Regex("\\W"))
            .map { it.toInt() }
        val startMinutes = startHoursMinutes[0] * 60 + startHoursMinutes[1]

        val endHoursMinutes = contactInfoLiveData.value!!
            .endTime
            .split(Regex("\\W"))
            .map { timePart -> timePart.toInt() }
        val endMinutes = endHoursMinutes[0] * 60 + endHoursMinutes[1]

        val now = DateTime.now()
        val nowMinutes = now.hourOfDay * 60 + now.minuteOfHour
        val beforeStart = startMinutes - nowMinutes
        val beforeEnd = endMinutes - nowMinutes

        return when {
            (beforeStart >= 60) -> {
                "Закрыто. Откроется через ${beforeStart / 60} часов ${beforeStart % 60} минут"
            }
            (beforeStart in 1 until 60) -> {
                "Закрыто. Откроется через ${beforeStart % 60} минут"
            }
            (beforeEnd >= 60) -> {
                "Открыто. Закроется через ${beforeEnd / 60} часов ${beforeEnd % 60} минут"
            }
            (beforeEnd in 1 until 60) -> {
                "Открыто. Закроется через ${beforeEnd % 60} минут"
            }
            else -> {
                "Закрыто. Откроется завтра"
            }
        }*/
    }
}