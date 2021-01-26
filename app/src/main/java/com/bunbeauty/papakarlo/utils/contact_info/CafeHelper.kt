package com.bunbeauty.papakarlo.utils.contact_info

import com.bunbeauty.papakarlo.utils.resoures.ResourcesProvider
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