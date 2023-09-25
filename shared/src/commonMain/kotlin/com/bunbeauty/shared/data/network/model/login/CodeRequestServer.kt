package com.bunbeauty.shared.data.network.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CodeRequestServer(
    @SerialName("phoneNumber")
    val phoneNumber: String,
)