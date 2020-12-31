package com.bunbeauty.papakarlo.view_model

import android.content.Context
import androidx.lifecycle.asLiveData
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.api.firebase.ApiRepository
import com.bunbeauty.papakarlo.data.api.firebase.IApiRepository
import com.bunbeauty.papakarlo.data.local.datastore.IDataStoreHelper
import com.bunbeauty.papakarlo.ui.contacts.ContactsNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ContactsViewModel @Inject constructor(
    private val context: Context,
    private val dataStoreHelper: IDataStoreHelper,
    private val apiRepository: IApiRepository,
) : BaseViewModel() {

    var navigator: WeakReference<ContactsNavigator>? = null

    val contactInfoLiveData = dataStoreHelper.contactInfo.asLiveData()

    fun getContactsInfo() {
        apiRepository.getContactInfo()
    }

    fun addressClick() {
        navigator?.get()?.goToAddress(56.847314, 37.369407)
    }

    fun workTimeClick() {
        navigator?.get()?.goToTime()
    }

    fun phoneClick() {
        navigator?.get()?.goToPhone(context.getString(R.string.msg_contacts_phone))
    }
}