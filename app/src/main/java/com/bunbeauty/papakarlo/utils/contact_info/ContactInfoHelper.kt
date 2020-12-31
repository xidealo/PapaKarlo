package com.bunbeauty.papakarlo.utils.contact_info

import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.ContactInfo
import com.bunbeauty.papakarlo.data.model.ContactInfo.Companion.ADDRESS
import com.bunbeauty.papakarlo.data.model.ContactInfo.Companion.END_TIME
import com.bunbeauty.papakarlo.data.model.ContactInfo.Companion.PHONE
import com.bunbeauty.papakarlo.data.model.ContactInfo.Companion.START_TIME
import com.bunbeauty.papakarlo.utils.resoures.ResourcesProvider
import com.google.firebase.database.DataSnapshot
import javax.inject.Inject

class ContactInfoHelper @Inject constructor(private val resourcesProvider: ResourcesProvider) :
    IContactInfoHelper {

    override fun getWorkTimeString(contactInfo: ContactInfo): String {
        if (contactInfo.startTime.isEmpty() || contactInfo.startTime.isEmpty()) {
            return ""
        }

        val workTime = resourcesProvider.getString(R.string.msg_contacts_work_time)
        val workTimeDivider = resourcesProvider.getString(R.string.msg_contacts_work_time_divider)
        return workTime + contactInfo.startTime + workTimeDivider + contactInfo.endTime
    }

    override fun getContactInfoFromSnapshot(dataSnapshot: DataSnapshot): ContactInfo {
        return ContactInfo(
            dataSnapshot.child(ADDRESS).value.toString(),
            dataSnapshot.child(START_TIME).value.toString(),
            dataSnapshot.child(END_TIME).value.toString(),
            dataSnapshot.child(PHONE).value.toString()
        )
    }
}