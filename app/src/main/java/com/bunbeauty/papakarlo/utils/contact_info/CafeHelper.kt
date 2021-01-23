package com.bunbeauty.papakarlo.utils.contact_info

import com.bunbeauty.papakarlo.R
import com.bunbeauty.papakarlo.data.model.Address
import com.bunbeauty.papakarlo.data.model.Address.Companion.ENTRANCE
import com.bunbeauty.papakarlo.data.model.Address.Companion.FLAT
import com.bunbeauty.papakarlo.data.model.Address.Companion.FLOOR
import com.bunbeauty.papakarlo.data.model.Address.Companion.HOUSE
import com.bunbeauty.papakarlo.data.model.Address.Companion.INTERCOM
import com.bunbeauty.papakarlo.data.model.Address.Companion.STREET
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity.Companion.END_TIME
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity.Companion.LATITUDE
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity.Companion.LONGITUDE
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity.Companion.NAME
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity.Companion.PHONE
import com.bunbeauty.papakarlo.data.model.cafe.CafeEntity.Companion.START_TIME
import com.bunbeauty.papakarlo.utils.resoures.ResourcesProvider
import com.google.firebase.database.DataSnapshot
import javax.inject.Inject

class CafeHelper @Inject constructor(private val resourcesProvider: ResourcesProvider) :
    ICafeHelper {

    /*override fun getWorkTimeString(cafeEntity: CafeEntity): String {
        if (cafeEntity.startTime.isEmpty() || cafeEntity.startTime.isEmpty()) {
            return ""
        }

        val workTime = resourcesProvider.getString(R.string.msg_contacts_work_time)
        val workTimeDivider = resourcesProvider.getString(R.string.msg_contacts_work_time_divider)
        return workTime + cafeEntity.startTime + workTimeDivider + cafeEntity.endTime
    }

    override fun getCafeFromSnapshot(dataSnapshot: DataSnapshot): CafeEntity {
        return CafeEntity(
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
    }*/


}