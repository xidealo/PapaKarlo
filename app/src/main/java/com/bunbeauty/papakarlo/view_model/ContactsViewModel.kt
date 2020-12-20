package com.bunbeauty.papakarlo.view_model

import android.content.Context
import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.ui.contacts.ContactsNavigator
import com.bunbeauty.papakarlo.view_model.base.BaseViewModel
import java.lang.ref.WeakReference
import javax.inject.Inject

class ContactsViewModel @Inject constructor(private val context: Context) : BaseViewModel<ContactsNavigator>() {

    override var navigator: WeakReference<ContactsNavigator>? = null

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