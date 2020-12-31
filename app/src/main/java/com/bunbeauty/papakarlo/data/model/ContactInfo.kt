package com.bunbeauty.papakarlo.data.model

data class ContactInfo(
    val address: String,
    val startTime: String,
    val endTime: String,
    val phone: String
) {

    companion object {
        const val CONTACT_INFO = "contact_info"
        const val ADDRESS = "address"
        const val START_TIME = "start_time"
        const val END_TIME = "end_time"
        const val PHONE = "phone"
    }
}