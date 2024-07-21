package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentServer(
    @SerialName("phoneNumber")
    val phoneNumber: String?,
    @SerialName("cardNumber")
    val cardNumber: String?
)
