package com.bunbeauty.papakarlo.utils.contact_info

import com.bunbeauty.papakarlo.data.model.ContactInfo
import com.google.firebase.database.DataSnapshot

interface IContactInfoHelper {

    fun getWorkTimeString(contactInfo: ContactInfo): String
    fun getContactInfoFromSnapshot(dataSnapshot: DataSnapshot): ContactInfo
}