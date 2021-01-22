package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.data.model.ContactInfo
import com.bunbeauty.papakarlo.ui.contacts.ContactsNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    dataStoreHelper: IDataStoreHelper,
    private val apiRepository: IApiRepository,
) : BaseViewModel() {

    var navigator: WeakReference<ContactsNavigator>? = null
    val isClickable = ObservableField(false)

/*    val contactInfoLiveData = Transformations.map(dataStoreHelper.contactInfo.asLiveData()) { contactInfo ->
            isClickable.set(!isContactInfoEmpty(contactInfo))
            contactInfo
        }*/

    private fun isContactInfoEmpty(contactInfo: ContactInfo): Boolean {
        return contactInfo.label.isEmpty() || contactInfo.latitude == 0.0 || contactInfo.longitude == 0.0
    }

    fun getContactsInfo() {
        apiRepository.getContactInfo()
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