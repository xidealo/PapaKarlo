package com.bunbeauty.shared.data.network.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AuthSessionServer(
    @SerialName("uuid")
    val uuid: String
)
