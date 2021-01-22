package com.bunbeauty.papakarlo.utils.contact_info

import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.Address.Companion.ENTRANCE
import com.bunbeauty.papakarlo.data.model.Address.Companion.FLAT
import com.bunbeauty.papakarlo.data.model.Address.Companion.FLOOR
import com.bunbeauty.papakarlo.data.model.Address.Companion.HOUSE
import com.bunbeauty.papakarlo.data.model.Address.Companion.INTERCOM
import com.bunbeauty.papakarlo.data.model.Address.Companion.STREET
import com.bunbeauty.papakarlo.data.model.ContactInfo
import com.bunbeauty.papakarlo.data.model.ContactInfo.Companion.END_TIME
import com.bunbeauty.papakarlo.data.model.ContactInfo.Companion.LATITUDE
import com.bunbeauty.papakarlo.data.model.ContactInfo.Companion.LONGITUDE
import com.bunbeauty.papakarlo.data.model.ContactInfo.Companion.NAME
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
            Address(
                0,
                dataSnapshot.child(STREET).value.toString(),
                dataSnapshot.child(HOUSE).value.toString(),
                dataSnapshot.child(FLAT).value.toString(),
                dataSnapshot.child(ENTRANCE).value.toString(),
                dataSnapshot.child(INTERCOM).value.toString(),
                dataSnapshot.child(FLOOR).value.toString(),
            ),
            dataSnapshot.child(START_TIME).value.toString(),
            dataSnapshot.child(END_TIME).value.toString(),
            dataSnapshot.child(PHONE).value.toString(),
            dataSnapshot.child(NAME).value.toString(),
            dataSnapshot.child(LATITUDE).value.toString().toDouble(),
            dataSnapshot.child(LONGITUDE).value.toString().toDouble()
        )
    }


}