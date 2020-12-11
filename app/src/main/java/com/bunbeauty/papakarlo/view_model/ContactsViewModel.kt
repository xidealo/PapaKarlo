package com.bunbeauty.papakarlo.view_model

import android.content.Context
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.ui.contacts.ContactsNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ContactsViewModel @Inject constructor(private val context: Context) : BaseViewModel() {

    lateinit var contactsNavigator: WeakReference<ContactsNavigator>

    fun addressClick() {
        contactsNavigator.get()?.goToAddress(56.847314, 37.369407)
    }

    fun workTimeClick() {
        contactsNavigator.get()?.goToTime()
    }

    fun phoneClick() {
        contactsNavigator.get()?.goToPhone(context.getString(R.string.msg_contacts_phone))
    }
}