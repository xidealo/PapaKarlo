package com.bunbeauty.papakarlo.view_model

import androidx.databinding.ObservableField
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
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
    val contactInfoLiveData =
        Transformations.map(dataStoreHelper.contactInfo.asLiveData()) { contactInfo ->
            isClickable.set(!isContactInfoEmpty(contactInfo))
            contactInfo
        }

    private fun isContactInfoEmpty(contactInfo: ContactInfo): Boolean {
        return contactInfo.label.isEmpty() || contactInfo.latitude == 0.0 || contactInfo.longitude == 0.0
    }

    fun getContactsInfo() {
        apiRepository.getContactInfo()
    }
}