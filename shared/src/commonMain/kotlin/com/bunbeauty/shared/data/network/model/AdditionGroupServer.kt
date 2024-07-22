package com.bunbeauty.shared.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdditionGroupServer(
    @SerialName("additions")
    val additionServerList: List<AdditionServer>,
    @SerialName("isVisible")
    val isVisible: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("singleChoice")
    val singleChoice: Boolean,
    @SerialName("uuid")
    val uuid: String,
    @SerialName("priority")
    val priority: Int
)
