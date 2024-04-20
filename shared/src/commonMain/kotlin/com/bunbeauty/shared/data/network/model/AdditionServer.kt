package com.bunbeauty.shared.data.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdditionServer(
    @SerialName("isSelected")
    val isSelected: Boolean,
    @SerialName("isVisible")
    val isVisible: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("fullName")
    val fullName: String?,
    @SerialName("photoLink")
    val photoLink: String,
    @SerialName("price")
    val price: Int?,
    @SerialName("uuid")
    val uuid: String,
    @SerialName("priority")
    val priority: Int,
)