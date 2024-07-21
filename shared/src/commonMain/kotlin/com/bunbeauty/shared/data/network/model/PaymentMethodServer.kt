package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PaymentMethodServer(

    @SerialName("uuid")
    val uuid: String,

    @SerialName("name")
    val name: String,

    @SerialName("value")
    val value: String?,

    @SerialName("valueToCopy")
    val valueToCopy: String?
)
